package simplelib

import org.sisioh.config._

class SimpleLib(config: Configuration) {
  def hi = config.getStringValue("simplelib.hi")
  def hello = config.getStringValue("simplelib.hello")
}
