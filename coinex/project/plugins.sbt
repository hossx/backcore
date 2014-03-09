resolvers += "spray repo" at "http://repo.spray.io"

resolvers += Classpaths.typesafeResolver

resolvers += "scct-github-repository" at "http://mtkopone.github.com/scct/maven-repo"

libraryDependencies ++= Seq(
  "com.github.siasia" %% "xsbt-web-plugin" % "0.12.0-0.2.11.1",
  "com.decodified" % "scala-ssh" % "0.6.2",
  "org.bouncycastle" % "bcprov-jdk16" % "1.46",
  "com.jcraft" % "jzlib" % "1.1.1"
)

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.0")

addSbtPlugin("io.spray" % "sbt-boilerplate" % "0.5.1")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.9.2")

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.3")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.0.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-osgi" % "0.5.0")

addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.3.1")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.2.0")

addSbtPlugin("reaktor" % "sbt-scct" % "0.2-SNAPSHOT")

// addSbtPlugin("com.atlassian.labs" % "sbt-git-stamp" % "0.1.0-SNAPSHOT")

addSbtPlugin("net.leifwarner" % "sbt-git-info" % "0.1-SNAPSHOT")
