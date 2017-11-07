package com.github.akzero53.orico.oricounfixedstatementcalculator.shared

/**
  * Oricoのご利用明細（未確定分）1行にあたるモデル。
  */
case class Item(date: String,
                detail: String,
                user: String,
                paymentMethod: String,
                numberOfPayments: Int,
                invoiceMonth: String,
                charge: Int,
                others: String,
               )
