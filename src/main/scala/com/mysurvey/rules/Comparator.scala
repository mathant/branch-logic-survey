package com.mysurvey.rules

import com.mysurvey.domain.Answer

import scala.util.Try


object Comparator {

  def lessThan(baseValue: Int)(answerToCompare: Answer): Try[Boolean] = Try {
    val valueToCompare = answerToCompare.value
    valueToCompare.toInt < baseValue
  }

  val lessThan_18 = lessThan(18) _
}
