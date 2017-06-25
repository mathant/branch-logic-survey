package com.mysurvey.io

import java.io.OutputStream


trait Printer {
  def printItem(item: String): Unit
}

trait ConsolePrinter extends Printer {
  val out: OutputStream = Console.out

  override def printItem(item: String): Unit = out.write(item.toString.getBytes)
}