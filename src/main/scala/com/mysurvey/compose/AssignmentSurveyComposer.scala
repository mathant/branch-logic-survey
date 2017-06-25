package com.mysurvey.compose

import com.mysurvey.domain._
import com.mysurvey.rules.Comparator._

object AssignmentSurveyComposer {

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
}

