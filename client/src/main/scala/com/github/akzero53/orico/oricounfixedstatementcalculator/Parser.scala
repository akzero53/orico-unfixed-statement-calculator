package com.github.akzero53.orico.oricounfixedstatementcalculator

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.util.{Failure, Success}

object Parser {
  case class State(sourceHtmlOption: Option[String] = None,
                   resultOption: Option[Int] = None)

  class Backend($: BackendScope[Unit, State]) {
    def render(state: State) = {
      <.div(
        <.div(
          <.label("Oricoのご利用明細（未確定分）ページのHTMLを貼ってください。"),
          <.input(
            ^.value := state.sourceHtmlOption.getOrElse(""),
            ^.onChange ==> handleChangeSourceHtml
          )
        ),
        <.div(
          <.button(
            "計算する!!",
            ^.onClick --> handleClickCalculateButton
          )
        ),
        <.div(
          state.resultOption.map(total => {
            <.label(s"合計金額: $total")
          }).whenDefined
        )
      )
    }

    def handleChangeSourceHtml(event: ReactEventFromInput) = {
      val tNextValue = event.target.value
      val tNextValueOption = if (tNextValue.isEmpty) None else Some(tNextValue)
      $.modState(_.copy(sourceHtmlOption = tNextValueOption))
    }

    def handleClickCalculateButton() = $.modState((state: State) => {
      state.copy(resultOption = state.sourceHtmlOption.flatMap((sourceHtml: String) => {
        OricoHtmlParser.parse(sourceHtml) match {
          case Failure(e) =>
            None
          case Success(items) =>
            Some(items.map(_.charge).sum)
        }
      }))
    })
  }

  private val component = ScalaComponent.builder[Unit]("Parser")
    .initialState(State())
    .backend(new Backend(_))
    .renderBackend
    .build

  def apply() = component()
}
