import sbt._

object RootBuild extends Build {
  lazy val root = Project(id = "root", base = file("."))
    .aggregate(config, simplelib)

  lazy val config = Project(id = "config", base = file("config"))

  lazy val simplelib = Project(id = "examples-simplelib", base = file("examples/simplelib"))
    .dependsOn(config)
}
