/**
 * Copyright (C) 2014 Coinport Inc. <http://www.coinport.com>
 *
 */

package com.coinport.coinex.mail

import com.coinport.coinex.data._

import akka.actor._
import akka.event.LoggingReceive

class Mailer(handler: MailHandler) extends Actor with ActorLogging {

  def receive = LoggingReceive {
    case request @ DoSendEmail(email, emailType, params, v, lang, tplName) =>
      log.info("{}", request)
      emailType match {
        case EmailType.RegisterVerify =>
          handler.sendRegistrationEmailConfirmation(email, params.toSeq, v, lang)

        case EmailType.LoginToken =>
          handler.sendLoginToken(email, params.toSeq)

        case EmailType.PasswordResetToken =>
          handler.sendPasswordReset(email, params.toSeq, v, lang)

        case EmailType.Monitor =>
          handler.sendMonitor(email, params.toSeq)

        case EmailType.VerificationCode =>
          handler.sendVerificationCodeEmail(email, params.toSeq, v, lang)

        case EmailType.WithdrawalNotification =>
          handler.sendWithdrawalNotification(email, params.toSeq, v, lang)

        case EmailType.Others if tplName.isDefined =>
          handler.sendEmailWithTemplate(email, tplName.get, params.toSeq)
          sender ! "complete"
      }
  }
}
