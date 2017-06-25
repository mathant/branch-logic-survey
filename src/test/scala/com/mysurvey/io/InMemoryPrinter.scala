package com.mysurvey.io

import scala.collection.mutable.ListBuffer

trait InMemoryPrinter extends Printer {

  val printerListBuffer: ListBuffer[String]

  override def printItem(item: String): Unit = {
    printerListBuffer += item
  }
}
