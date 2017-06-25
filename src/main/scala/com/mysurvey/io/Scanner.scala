package com.mysurvey.io

import java.io.BufferedReader

trait Scanner {
  def scanItem(): String
}

trait ConsoleScanner extends Scanner {
  val in: BufferedReader = Console.in

  override def scanItem(): String = Console.in.readLine()
}
