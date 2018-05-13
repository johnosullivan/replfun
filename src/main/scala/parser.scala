package replfun

import org.parboiled2._
import abstracts.Expr
import abstracts.factory._
import CharPredicate._

class parser(val input: ParserInput) extends Parser {

  def Input = rule { zeroOrMore(' ') ~ zeroOrMore(Statement) ~ EOI ~> (block((_: Seq[Expr]): _*)) }

  def Statement = rule {
    Expression ~ ws(';') | Assignment | Conditional | Loop | Block
  }

  def Assignment = rule {
    capture(Ident) ~ zeroOrMore(WhiteSpace ~ ws(".") ~ capture(Ident)) ~ WhiteSpace ~ ws('=') ~ Expression ~ ws(';') ~> ((i: String, is: Seq[String], e: Expr) => assignment(i +: is.toList, e))
  }

  def Conditional: Rule1[Expr] = rule {
    ws("if") ~ ws('(') ~ Expression ~ ws(')') ~
      Block ~
      zeroOrMore(ElseIf) ~
      optional(ws("else") ~ Block) ~>
      ((c0: Expr, b0: Expr, es: Seq[(Expr, Expr)], e: Option[Expr]) => {
        val bn1 = e getOrElse (block())
        val e0 = es.lastOption map {
          case (cn, bn) =>
            es.init.foldRight(cond(cn, bn, bn1))((e, r) => cond(e._1, e._2, r))
        } getOrElse bn1
        cond(c0, b0, e0)
      })
  }

  def ElseIf: Rule1[(Expr, Expr)] = rule {
    ws("else") ~ ws("if") ~ ws('(') ~ Expression ~ ws(')') ~ Block ~>
      ((_: Expr, _: Expr))
  }

  def Loop = rule {
    ws("while") ~ ws('(') ~ Expression ~ ws(')') ~ Block ~>
      (loop(_: Expr, _: Expr))
  }

  def Block: Rule1[Expr] = rule {
    ws('{') ~ zeroOrMore(Statement) ~ ws('}') ~> (block((_: Seq[Expr]): _*))
  }

  def Expression = rule {
    Term ~ zeroOrMore(
      ws('+') ~ Term ~> (plus(_: Expr, _))
        | ws('-') ~ Term ~> (minus(_: Expr, _)))
  }

  def Term = rule {
    Factor ~ zeroOrMore(
      ws('*') ~ Factor ~> (times(_: Expr, _))
        | ws('/') ~ Factor ~> (div(_: Expr, _))
        | ws('%') ~ Factor ~> (mod(_: Expr, _)))
  }

  def Factor: Rule1[Expr] = rule {
    Selection | Number | UnaryPlus | UnaryMinus | Parens | Struct
  }

  def Selection: Rule1[Expr] = rule {
    Variable ~ zeroOrMore(ws('.') ~ capture(Ident)) ~ WhiteSpace ~>
      ((e: Expr, fs: Seq[String]) => fs.foldLeft(e)(select))
  }

  def Struct = rule {
    ws('{') ~ ws('}') ~> (() => struct()) |
      ws('{') ~ Field ~ zeroOrMore(ws(',') ~ Field) ~ ws('}') ~>
      ((f: (String, Expr), fs: Seq[(String, Expr)]) => struct((f +: fs): _*))
  }

  def Field = rule {
    capture(Ident) ~ WhiteSpace ~ ws(':') ~ Expression ~> ((_: String, _: Expr))
  }

  def Number = rule { capture(Digits) ~ WhiteSpace ~> ((s: String) => constant(s.toInt)) }

  def UnaryPlus = rule { ws('+') ~ Factor }

  def UnaryMinus = rule { ws('-') ~ Factor ~> (uminus(_: Expr)) }

  def Parens = rule { ws('(') ~ Expression ~ ws(')') ~ WhiteSpace }

  def Digits = rule { oneOrMore(Digit) }

  def Ident = rule { Alpha ~ zeroOrMore(AlphaNum) }

  def Variable = rule { capture(Ident) ~ WhiteSpace ~> (variable(_)) }

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def ws(c: Char) = rule { c ~ WhiteSpace }

  def ws(s: String) = rule { s ~ WhiteSpace }

}
