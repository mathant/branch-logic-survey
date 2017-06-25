package com.mysurvey.domain


sealed trait Answer {

  val typeDescription: String
  val value: String

  def withValue(value: String): Answer
}

case class BooleanAnswer(typeDescription: String = "Boolean", value: String = "") extends Answer {
  override def withValue(newValue: String): BooleanAnswer = copy(value = newValue)
}

case class FreeTextAnswer(typeDescription: String = "Free Text", value: String = "") extends Answer {
  override def withValue(newValue: String): FreeTextAnswer = copy(value = newValue)
}

