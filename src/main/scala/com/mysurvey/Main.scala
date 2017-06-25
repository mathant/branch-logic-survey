package com.mysurvey

import com.mysurvey.compose.AssignmentSurveyComposer
import com.mysurvey.flow.ConsoleFlowManager


object Main {

  val flowManager = new ConsoleFlowManager(AssignmentSurveyComposer.startBlock)

  def main(args: Array[String]): Unit = {
    flowManager.printResults()
  }
}
