package com.mysurvey.domain


import scala.util.{Failure, Success, Try}

sealed trait SurveyBlock

case class QuestionAnswerBlock(questionAnswer: QuestionAnswer, next: SurveyBlock) extends SurveyBlock

case class DecisionBlock(decidingQuestionId: QuestionId, decidingFunction: (Answer) => Try[Boolean],
                         yesBlock: SurveyBlock, noBlock: SurveyBlock) extends SurveyBlock {

  def next(prevAnswer: Answer): Try[SurveyBlock] = {
    decidingFunction(prevAnswer) match {
      case Success(true) => Success(yesBlock)
      case Success(false) => Success(noBlock)
      case Failure(ex) => Failure(ex)
    }
  }
}

object EndBlock extends SurveyBlock