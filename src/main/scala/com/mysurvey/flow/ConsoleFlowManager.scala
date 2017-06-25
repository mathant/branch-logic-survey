package com.mysurvey.flow

import java.io.PrintWriter

import com.mysurvey.domain.{QuestionAnswer, SurveyBlock}
import com.mysurvey.io.{ConsolePrinter, ConsoleScanner}

import scala.util.{Failure, Success}

class ConsoleFlowManager(val startBlock: SurveyBlock)
  extends FlowManager with ConsolePrinter with ConsoleScanner {

  def format(qa: QuestionAnswer): String = {
    s"ID: ${qa.questionId.id} \nQuestions: '${qa.question.description}'\nAnswer Type:<${qa.answer.typeDescription}>\n"
  }

  def formatWithAnswerValue(qa: QuestionAnswer): String = {
    s"(${qa.questionId.id}) ${qa.question.description} <${qa.answer.typeDescription}> Answer: '${qa.answer.value}'\n"
  }

  def printResults() = {
    val results = startSurvey()
    results match {
      case Success(res) => {
        printItem("Results:\n")
        res.foreach(item => printItem(formatWithAnswerValue(item)))
      }
      case Failure(ex) => {
        val writer = new PrintWriter(out)
        ex.printStackTrace(writer)
        writer.flush()
        writer.close()
      }
    }

  }
}
