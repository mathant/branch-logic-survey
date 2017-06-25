package com.mysurvey.flow

import com.mysurvey.domain._
import com.mysurvey.io.{Printer, Scanner}

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}


trait FlowManager extends Printer with Scanner {

  val startBlock: SurveyBlock

  def format(qa: QuestionAnswer): String

  def startSurvey() = runSurvey(startBlock, Map[QuestionId, Answer](), List[QuestionAnswer]())



  /**
    *
    * @param surveyBlock - the block from which the survey need to begin for this iteration
    * @param prevAnswersMap - a map to accumulate previous questionsId -> answer
    * @param prevResults - the previous answers, in the order it is answered
    * @return List of Questions with user supplied answers, or Exception if any
    */
  //add the latest survey block (QuestionAnswerBlock) with answer to the head of the list (for efficiency),
  // reverse the list before sending back.
  @tailrec
  final def runSurvey(surveyBlock: SurveyBlock, prevAnswersMap: Map[QuestionId, Answer],
                prevResults: List[QuestionAnswer]): Try[List[QuestionAnswer]] = {

    surveyBlock match {

      case EndBlock => Success(prevResults.reverse)

      case decisionBlock: DecisionBlock => {
        val prevAnswer = prevAnswersMap(decisionBlock.decidingQuestionId)
        val nextBlock = decisionBlock.next(prevAnswer)
        nextBlock match {
          case Success(block) => runSurvey(block, prevAnswersMap, prevResults)
          case Failure(ex) => Failure(ex)
        }
      }

      case qaBlock: QuestionAnswerBlock => {
        val qa = qaBlock.questionAnswer
        printItem(format(qa))
        val scannedItem = scanItem()
        val updatedAnswer = qa.answer.withValue(scannedItem)
        val updatedQuestionAnswer = qa.withAnswer(updatedAnswer)
        runSurvey(qaBlock.next, prevAnswersMap + (qa.questionId -> updatedAnswer), updatedQuestionAnswer :: prevResults)
      }
    }
  }
}