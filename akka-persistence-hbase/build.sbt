organization := "com.coinport"

name := "akka-persistence-hadoop"

version := "0.3"

scalaVersion := "2.10.2"

val akkaVersion = "2.3-SNAPSHOT"

resolvers += "akka snapshots" at "http://repo.akka.io/snapshots"

resolvers += "maven2" at "http://repo1.maven.org/maven2"

libraryDependencies += "org.apache.hadoop" % "hadoop-core"   % "1.1.2"

libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "1.1.2"

libraryDependencies += "org.apache.hbase"  % "hbase"         % "0.94.6.1" % "compile"

libraryDependencies += ("org.hbase"        % "asynchbase"    % "1.4.1")
  .exclude("org.slf4j", "log4j-over-slf4j")
  .exclude("org.slf4j", "jcl-over-slf4j")

libraryDependencies += "org.slf4j"         % "slf4j-log4j12" % "1.6.0"

libraryDependencies += "com.typesafe.akka" %% "akka-persistence-experimental" % akkaVersion % "compile"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit"       % akkaVersion % "test"

libraryDependencies += "org.scalatest"     %% "scalatest"          % "2.0"       % "test"

parallelExecution in Test := false

// publishing settings

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(Path.userHome / ".sbt" / "sonatype.properties")
