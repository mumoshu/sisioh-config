val scala210Version = "2.10.5"

val scala211Version = "2.11.6"

name := "sisioh-config-simplelib"

sonatypeProfileName := "org.sisioh"

organization := "org.sisioh"

scalaVersion := scala211Version

crossScalaVersions := Seq(scala210Version, scala211Version)

scalacOptions ++= Seq("-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked")

javacOptions ++= Seq("-encoding", "UTF-8", "-deprecation")

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8.1" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.3" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "com.typesafe" % "config" % "1.3.0",
  "org.specs2" %% "specs2" % "2.3.12" % "test"
)

libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang" % "scala-reflect" % scala211Version
      )
    case _ =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang" % "scala-reflect" % scala210Version
      )
  }
}

publishArtifact := false
