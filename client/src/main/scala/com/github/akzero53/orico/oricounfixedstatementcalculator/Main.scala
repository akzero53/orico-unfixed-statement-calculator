package com.github.akzero53.orico.oricounfixedstatementcalculator

import org.scalajs.dom

object Main {

  def main(args: Array[String]): Unit = {
    val tReactRoot = dom.document.getElementById("react-root")
    Parser().renderIntoDOM(tReactRoot)
  }
}
