/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 */

package com.coinport.coinex.users

import akka.persistence.SnapshotOffer
import com.coinport.coinex.data._
import akka.actor._
import akka.persistence._
import ErrorCode._
import akka.event.LoggingReceive

class UserProcessor(mailer: ActorRef, userManagerSecret: String) extends EventsourcedProcessor with ActorLogging {
  override val processorId = "coinex_up"

  val manager = new UserManager(userManagerSecret)

  def receiveRecover = PartialFunction.empty[Any, Unit]

  def receiveCommand = LoggingReceive {
    case m @ DoRegisterUser(userProfile, password) =>
      if (manager.isEmailRegistered(userProfile.email)) {
        sender ! RegisterUserFailed(EmailAlreadyRegistered)
      } else {
        val profile = manager.regulateProfile(userProfile, password, lastSequenceNr)
        sender ! RegisterUserSucceeded(profile)
        persist(DoRegisterUser(profile, password))(updateState)
        sendEmailVerificationEmail(profile)
      }

    case m @ DoRequestPasswordReset(email) =>
      manager.getUser(email) match {
        case None => sender ! RequestPasswordResetFailed(UserNotExist)
        case Some(profile) if profile.email != email =>
          sender ! RequestPasswordResetFailed(TokenNotUnique)
        case Some(profile) if profile.passwordResetToken.isDefined =>
          sender ! RequestPasswordResetSucceeded(profile.id, profile.email, profile.passwordResetToken.get)
          sendRequestPasswordResetEmail(profile)
        case Some(profile) if profile.passwordResetToken.isEmpty => persist(m)(updateState)
      }

    case m @ DoResetPassword(email, password, passwordResetToken) =>
      manager.getUser(email) match {
        case Some(user) if user.passwordResetToken == passwordResetToken =>
          sender ! ResetPasswordSucceeded(user.id, user.email)
          persist(m)(updateState)
        case Some(user) =>
          sender ! ResetPasswordFailed(TokenNotMatch)
        case None =>
          sender ! ResetPasswordFailed(UserNotExist)
      }

    case Login(email, password) =>
      manager.checkLogin(email, password) match {
        case Left(error) => sender ! LoginFailed(error)
        case Right(profile) => sender ! LoginSucceeded(profile.id, profile.email)
      }

    // This command may not be necessary
    /*
    case ValidatePasswordResetToken(token) =>
      manager().passwordResetTokenMap.get(token) match {
        case Some(id) => sender ! PasswordResetTokenValidationResult(manager().profileMap.get(id))
        case None => sender ! PasswordResetTokenValidationResult(None)
      }
      */
  }

  def updateState(event: Any): Unit = event match {
    case DoRegisterUser(profile, _) =>
      manager.registerUser(profile)

    case DoRequestPasswordReset(email) =>
      val profile = manager.requestPasswordReset(email, lastSequenceNr)
      sender ! RequestPasswordResetSucceeded(profile.id, profile.email, profile.passwordResetToken.get)
      if (!recoveryRunning) { sendRequestPasswordResetEmail(profile) }

    case DoResetPassword(email, password, token) =>
      manager.resetPassword(email, password, token)
  }

  def sendEmailVerificationEmail(profile: UserProfile) {
    mailer ! DoSendEmail(profile.email, EmailType.RegisterVerify, Map(
      "NAME" -> profile.realName.getOrElse(profile.email),
      "LANG" -> "CHINESE",
      "TOKEN" -> profile.verificationToken.get))
  }
  def sendRequestPasswordResetEmail(profile: UserProfile) {
    mailer ! DoSendEmail(profile.email, EmailType.PasswordResetToken, Map(
      "NAME" -> profile.realName.getOrElse(profile.email),
      "LANG" -> "CHINESE",
      "TOKEN" -> profile.passwordResetToken.get))
  }
}
