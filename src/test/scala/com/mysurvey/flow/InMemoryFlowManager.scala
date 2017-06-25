package com.mysurvey.flow

import com.mysurvey.domain.SurveyBlock
import com.mysurvey.io.{InMemoryPrinter, InMemoryScanner}

import scala.collection.mutable.ListBuffer

/**
  * Created by mathan on 24/06/2017.
  */
class InMemoryFlowManager(override val startBlock: SurveyBlock) extends ConsoleFlowManager(startBlock)
  with InMemoryPrinter with InMemoryScanner {
  override val printerListBuffer: ListBuffer[String] = new ListBuffer[String]
  override val scannerListBuffer: ListBuffer[String] = new ListBuffer[String]
}
