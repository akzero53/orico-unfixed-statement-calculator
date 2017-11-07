package com.github.akzero53.orico.oricounfixedstatementcalculator

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Parser {
  case class State()

  private val component = ScalaComponent.builder[Unit]("Parser")
    .initialState(State())
    .render_PS((p, s) => {
      <.div("とりあえず表示")
    })
    .build

  def apply() = component()
}
