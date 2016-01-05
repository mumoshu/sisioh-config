package org.sisioh.config

import org.specs2.mutable.Specification

class ConfigurationModeSpec extends Specification {

  "ConfigurationMode" should {

    "extract the Dev mode out of a string" in {
      val ConfigurationMode(mode) = "development"
      mode must_== ConfigurationMode.Dev
    }

    "extract the Prod mode out of a string" in {
      val mode = "production" match {
        case ConfigurationMode(m) => m
      }
      mode must_== ConfigurationMode.Prod
    }

  }

}
