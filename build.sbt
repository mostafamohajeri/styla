

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/mostafamohajeri/styla"),
    "scm:git@github.com:mostafamohajeri/styla.git"
  )
)


ThisBuild / developers := List(
  Developer(
    id    = "mostafamohajeri",
    name  = "Mostafa",
    email = "m.mohajeriparizi@uva.nl",
    url   = url("https://github.com/mostafamohajeri/")
  )
)

ThisBuild / description := "Prolog engine in Scala"
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/mostafamohajeri/styla"))


lazy val stylaport = (project in file(".")).settings(
  name := "styla",
//  credentials += Credentials(Path.userHome / ".sbt" / ".sonatype_credentials"),
//  publishTo := {
//    val nexus = "http://145.100.135.102:8081/repository/agent-script/"
//    if (isSnapshot.value)
//      Some(("snapshots" at nexus).withAllowInsecureProtocol(true))
//    else
//      Some(("releases"  at nexus).withAllowInsecureProtocol(true))
//  },
)


// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true

//ThisBuild / resolvers += Resolver.mavenLocal
//githubTokenSource := TokenSource.GitConfig("github.token")
// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-parser-combinators
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
// https://mvnrepository.com/artifact/org.jline/jline-console
//libraryDependencies += "org.jline" % "jline-console" % "3.16.0"

//ThisBuild / name := "styla"
ThisBuild / organization := "io.github.mostafamohajeri"
ThisBuild / organizationName := "CCI Group"
ThisBuild / organizationHomepage := Some(url("https://cci-research.nl/"))
ThisBuild / version      := "0.2.3"
ThisBuild / scalaVersion := "2.13.7"