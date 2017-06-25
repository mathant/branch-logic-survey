package com.mysurvey.domain

import org.scalatest.{FreeSpec, MustMatchers}

import scala.util.{Failure, Success, Try}


class DecisionBlockTest extends FreeSpec with MustMatchers {


  "A Decision block with a deciding function when a passed with previous answer " - {

    val qa2 = QuestionAnswer(QuestionId(2), Question("How many coffee do you drink per day?"), FreeTextAnswer())
    val qa3 = QuestionAnswer(QuestionId(3), Question("Do you like coffee delivered to you?"), FreeTextAnswer())
    val qa4 = QuestionAnswer(QuestionId(4), Question("Do you like fruit juice?"), BooleanAnswer())

    val decidingFunction = (answer: Answer) => Try(4 < answer.value.toInt)

    val yesBlock = QuestionAnswerBlock(qa3, EndBlock)
    val noBlock = QuestionAnswerBlock(qa4, EndBlock)
    val decidingBlock = DecisionBlock(QuestionId(2), decidingFunction, yesBlock, noBlock)

    " which return true should return yesBlock" in {

      val answer = FreeTextAnswer().withValue("12")

      decidingBlock.next(answer).get mustBe yesBlock

    }
    " which return false should return noBlock" in {

      val answer = FreeTextAnswer().withValue("3")

      decidingBlock.next(answer).get mustBe noBlock

    }
    " which return Exception should return Exception" in {

      val answer = FreeTextAnswer().withValue("3dadf")

      decidingBlock.next(answer) match {
        case Success(_) => fail()
        case Failure(e: Exception) => e.isInstanceOf[NumberFormatException]
      }

    }
  }
}
