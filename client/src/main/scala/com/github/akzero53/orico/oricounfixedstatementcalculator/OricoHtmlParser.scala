package com.github.akzero53.orico.oricounfixedstatementcalculator

import com.github.akzero53.orico.oricounfixedstatementcalculator.shared.Item
import org.scalajs.dom.{Node, NodeList}
import org.scalajs.dom.experimental.domparser.{DOMParser, SupportedType}

import scala.util.Try

object OricoHtmlParser {

  implicit class NodeListUtil(nodeList: NodeList) {
    def getNodes: Seq[Node] = (0 until nodeList.length).map(nodeList(_))
  }

  def parse(html: String): Try[Seq[Item]] = Try {
    val parser: DOMParser = new DOMParser
    parser
      .parseFromString(html, SupportedType.`text/html`)
      .getElementsByClassName("standard").getNodes
      .filter(node => {
        node.childNodes.getNodes.exists(_.nodeName == "THEAD")
      })
      .head.childNodes.getNodes
      .find(_.nodeName == "TBODY")
      .getOrElse(throw new IllegalStateException("ボディがねぇ！！"))
      .childNodes.getNodes
      .filter(_.nodeName == "TR")
      .tail
      .map(_.childNodes.getNodes.filter(_.nodeName == "TD").map(_.textContent.trim))
      .map(details => Item(
        date = details.head,
        detail = details(1),
        user = details(2),
        paymentMethod = details(3),
        numberOfPayments = details(4).toInt,
        invoiceMonth = details(5),
        charge = details(6).replace("円", "").replace(",", "").toInt,
        others = details(7)
      ))
  }
}
