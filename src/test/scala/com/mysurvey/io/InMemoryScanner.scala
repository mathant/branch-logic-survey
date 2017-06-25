package com.mysurvey.io

import scala.collection.mutable.ListBuffer


trait InMemoryScanner extends Scanner {

  val scannerListBuffer: ListBuffer[String]

  override def scanItem(): String = {
    val head = scannerListBuffer.head
    scannerListBuffer.remove(0)
  }
}
