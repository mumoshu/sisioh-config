package org.sisioh.config

object ConfigurationMode extends Enumeration {
  val Dev, Prod = Value

  private val nameToMode = Map(
    "development" -> Dev,
    "production" -> Prod
  )

  def unapply(modeName: String): Option[Value] = nameToMode.get(modeName)
}
