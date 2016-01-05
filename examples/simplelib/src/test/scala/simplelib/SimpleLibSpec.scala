package simplelib

import org.sisioh.config._

import org.specs2.mutable.Specification

class SimpleLibSpec extends Specification {

  case class SimpleApp(mode: ConfigurationMode.Value) {
    lazy val config = Configuration.loadByMode(new java.io.File("."), mode)

    def hi = str("simplelib.hi")
    def hello = str("simpleapp.hello")
    def whatever = str("simplelib.whatever")

    private def str(key: String): String = config.getStringValue(key).get
  }

  "SimpleLib" should {

    "get simplelib.hi from reference.conf" in {
      SimpleApp(ConfigurationMode.Prod).hi must_== "hi"
    }

    "get simpleapp.hello from application.conf" in {
      SimpleApp(ConfigurationMode.Prod).hello must_== "hello"
    }

    "get simplelib.whatever overriden by application.conf" in {
      SimpleApp(ConfigurationMode.Prod).whatever must_== "whatever overriden in application.conf"
    }

  }

}
