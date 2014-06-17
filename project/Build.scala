import sbt._
import sbt.Keys._

object ConfigBuild extends Build {

  val root = Project(
    id = "sisioh-config",
    base = file("."),
    settings = Seq(
      publishMavenStyle := true,
      publishArtifact in Test := false,
      pomIncludeRepository := {
        _ => false
      },
      publishTo <<= version {
        (v: String) =>
          val nexus = "https://oss.sonatype.org/"
          if (v.trim.endsWith("SNAPSHOT"))
            Some("snapshots" at nexus + "content/repositories/snapshots")
          else
            Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
credentials ++= {
  val sonatype = ("Sonatype Nexus Repository Manager", "oss.sonatype.org")
  def loadMavenCredentials(file: java.io.File) : Seq[Credentials] = {
    xml.XML.loadFile(file) \ "servers" \ "server" map (s => {
      val host = (s \ "id").text
      val realm = if (host == sonatype._2) sonatype._1 else "Unknown"
      Credentials(realm, host, (s \ "username").text, (s \ "password").text)
    })
  }
  val ivyCredentials   = Path.userHome / ".ivy2" / ".credentials"
  val mavenCredentials = Path.userHome / ".m2"   / "settings.xml"
  (ivyCredentials.asFile, mavenCredentials.asFile) match {
    case (ivy, _) if ivy.canRead => Credentials(ivy) :: Nil
    case (_, mvn) if mvn.canRead => loadMavenCredentials(mvn)
    case _ => Nil
  }
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
      organization := "org.sisioh",
      version := "0.0.4",
      scalaVersion := "2.11.0",
      crossScalaVersions := Seq("2.11.0", "2.10.4"),
      scalacOptions ++= Seq("-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked"),
      javacOptions ++= Seq("-encoding", "UTF-8", "-deprecation"),
      libraryDependencies ++= Seq(
        "junit" % "junit" % "4.8.1" % "test",
        "org.hamcrest" % "hamcrest-all" % "1.3" % "test",
        "org.mockito" % "mockito-core" % "1.9.5" % "test",
        "com.typesafe" % "config" % "1.2.1",
        "org.specs2" %% "specs2" % "2.3.12" % "test"
      ),
      libraryDependencies := {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((2, scalaMajor)) if scalaMajor >= 11 =>
            libraryDependencies.value ++ Seq( 
              "org.scala-lang" % "scala-reflect" % "2.11.0"
            ) 
          case _ =>
            libraryDependencies.value ++ Seq(
              "org.scala-lang" % "scala-reflect" % "2.10.4"
            )
        }
      }
    )
  )
}
