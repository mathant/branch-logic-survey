package com.mysurvey.flow

import com.mysurvey.compose.AssignmentSurveyComposer
import com.mysurvey.domain._
import com.mysurvey.rules.Comparator.lessThan_18
import org.scalatest.{BeforeAndAfter, FreeSpec, MustMatchers}

import scala.util.{Failure, Success}


class InMemoryFlowManagerTest extends FreeSpec with MustMatchers with BeforeAndAfter {

  val qa1 = QuestionAnswer(QuestionId(1), Question("What's your name?"), FreeTextAnswer())
  val qa2 = QuestionAnswer(QuestionId(2), Question("How old are you?"), FreeTextAnswer())
  val qa3 = QuestionAnswer(QuestionId(3), Question("Enter your pet name please"), FreeTextAnswer())
  val qa4 = QuestionAnswer(QuestionId(4), Question("Do you have a driving license?"), BooleanAnswer())

  val startBlock = QuestionAnswerBlock(qa1,
    QuestionAnswerBlock(qa2,
      DecisionBlock(qa2.questionId, lessThan_18,
        yesBlock = QuestionAnswerBlock(qa3, EndBlock),
        noBlock = QuestionAnswerBlock(qa4, EndBlock))
    )
  )

  val surverStartBlock = startBlock
  val inMemoryFlowManager = new InMemoryFlowManager(surverStartBlock)

  before {
    inMemoryFlowManager.printerListBuffer.clear()
    inMemoryFlowManager.scannerListBuffer.clear()
  }

  "Give a survey is presented" - {

    " when the user selects the 'YES' flow " - {

      " then 'YES' flow should be followed" in {
        val inputList = List("Mathan", "17", "PetName-Tigger")
        inMemoryFlowManager.scannerListBuffer.appendAll(inputList)

        val results = inMemoryFlowManager.startSurvey().get
        val expectedQuestionOrder = List(qa1, qa2, qa3)

        assert(inMemoryFlowManager.printerListBuffer.toList == expectedQuestionOrder.map(inMemoryFlowManager.format(_)))

        val expectedAnswerOrder = inputList
        assert(results.map(_.answer.value) == expectedAnswerOrder)
        assert(results.map(r => (r.questionId, r.question)) == expectedQuestionOrder.map(e => (e.questionId, e.question)))
      }
    }

    " when the user selects the 'NO' flow " - {

      " then 'NO' flow should be followed" in {
        val inputList = List("Mathan", "18", "DrivingLicense-number")
        inMemoryFlowManager.scannerListBuffer.appendAll(inputList)

        val results = inMemoryFlowManager.startSurvey().get
        val expectedQuestionOrder = List(qa1, qa2, qa4)

        assert(inMemoryFlowManager.printerListBuffer.toList == expectedQuestionOrder.map(inMemoryFlowManager.format(_)))

        val expectedAnswerOrder = inputList
        assert(results.map(_.answer.value) == expectedAnswerOrder)
        assert(results.map(r => (r.questionId, r.question)) == expectedQuestionOrder.map(e => (e.questionId, e.question)))
      }
    }

    " when the user gives INVALID input " - {

      " then exception is thrown" in {

        val inputList = List("Mathan", "1WWW8", "DrivingLicense-number")
        inMemoryFlowManager.scannerListBuffer.appendAll(inputList)

        val results = inMemoryFlowManager.startSurvey()
        results match {
          case Success(s) => fail("Must throw an exception")
          case Failure(ex: Exception) => succeed
        }
      }
    }
  }

  " Test the Assignment survey for correctness" in {

    val flowManager = new InMemoryFlowManager(AssignmentSurveyComposer.startBlock)

    val inputList = List("Mathan", "17", "PetName-Tigger")
    inMemoryFlowManager.scannerListBuffer.appendAll(inputList)

    val results = inMemoryFlowManager.startSurvey().get
    val expectedQuestionOrder = List(AssignmentSurveyComposer.qa1, AssignmentSurveyComposer.qa2,
      AssignmentSurveyComposer.qa3)

    assert(inMemoryFlowManager.printerListBuffer.toList == expectedQuestionOrder.map(inMemoryFlowManager.format(_)))

    val expectedAnswerOrder = inputList
    assert(results.map(_.answer.value) == expectedAnswerOrder)
    assert(results.map(r => (r.questionId, r.question)) == expectedQuestionOrder.map(e => (e.questionId, e.question)))
  }
}
