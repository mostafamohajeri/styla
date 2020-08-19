

lazy val stylaport = (project in file(".")).settings(
)


ThisBuild / resolvers += Resolver.mavenLocal
// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-parser-combinators
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
// https://mvnrepository.com/artifact/org.jline/jline-console
//libraryDependencies += "org.jline" % "jline-console" % "3.16.0"


ThisBuild / organization := "nl.uva.sne.cci"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.3"