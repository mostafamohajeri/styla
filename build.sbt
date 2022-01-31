

lazy val stylaport = (project in file(".")).settings(
  name := "styla",
  credentials += Credentials(Path.userHome / ".sbt" / ".sonatype_credentials"),
  publishTo := {
    val nexus = "http://145.100.135.102:8081/repository/agent-script/"
    if (isSnapshot.value)
      Some(("snapshots" at nexus).withAllowInsecureProtocol(true))
    else
      Some(("releases"  at nexus).withAllowInsecureProtocol(true))
  },
)


//ThisBuild / resolvers += Resolver.mavenLocal
//githubTokenSource := TokenSource.GitConfig("github.token")
// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-parser-combinators
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
// https://mvnrepository.com/artifact/org.jline/jline-console
//libraryDependencies += "org.jline" % "jline-console" % "3.16.0"


ThisBuild / organization := "nl.uva.sne.cci"
ThisBuild / version      := "0.2.3"
ThisBuild / scalaVersion := "2.13.7"