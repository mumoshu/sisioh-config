import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import sbt.Keys._
import sbt._
import xerial.sbt.Sonatype.SonatypeKeys._

import scalariform.formatter.preferences._

object ConfigBuild extends Build {

  val scala211Version = "2.11.6"
  val scala210Version = "2.10.5"

  lazy val scalariformSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences :=
      ScalariformKeys.preferences.value
        .setPreference(AlignParameters, true)
        .setPreference(AlignSingleLineCaseStatements, true)
        .setPreference(DoubleIndentClassDeclaration, true)
        .setPreference(PreserveDanglingCloseParenthesis, true)
        .setPreference(MultilineScaladocCommentsStartOnFirstLine, false)
  )

  val root = Project(
    id = "sisioh-config",
    base = file("."),
    settings = scalariformSettings ++ Seq(
      sonatypeProfileName := "org.sisioh",
      organization := "org.sisioh",
      scalaVersion := scala211Version,
      crossScalaVersions := Seq(scala210Version, scala211Version),
      scalacOptions ++= Seq("-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked"),
      javacOptions ++= Seq("-encoding", "UTF-8", "-deprecation"),
      libraryDependencies ++= Seq(
        "junit" % "junit" % "4.8.1" % "test",
        "org.hamcrest" % "hamcrest-all" % "1.3" % "test",
        "org.mockito" % "mockito-core" % "1.9.5" % "test",
        "com.typesafe" % "config" % "1.3.0",
        "org.specs2" %% "specs2" % "2.3.12" % "test"
      ),
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
      },
      publishMavenStyle := true,
      publishArtifact in Test := false,
      pomIncludeRepository := {
        _ => false
      },
      pomExtra := (
        <url>https://github.com/sisioh/sisioh-config</url>
          <licenses>
            <license>
              <name>Apache License Version 2.0</name>
              <url>http://www.apache.org/licenses/</url>
              <distribution>repo</distribution>
            </license>
          </licenses>
          <scm>
            <url>git@github.com:sisioh/sisioh-config.git</url>
            <connection>scm:git:git@github.com:sisioh/sisioh-conifig.git</connection>
          </scm>
          <developers>
            <developer>
              <id>j5ik2o</id>
              <name>Junichi Kato</name>
              <url>http://j5ik2o.me</url>
            </developer>
          </developers>
        ),
      credentials := {
        val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
        Credentials(ivyCredentials) :: Nil
      }
    )
  )
}
