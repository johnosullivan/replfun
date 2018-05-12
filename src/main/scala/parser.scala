package replfun

import org.parboiled2._
import abstracts.Expr
import abstracts.factory._

class parser(val input: ParserInput) extends Parser {

  def Input = rule { zeroOrMore(' ') ~ zeroOrMore(Statement) ~ EOI ~> (block((_: Seq[Expr]): _*)) }

  def Statement = rule {
    Number | Variable
  }

  def Number = rule { capture(Digits) ~ WhiteSpace ~> ((s: String) => constant(s.toInt)) }

  import CharPredicate._

  def Digits = rule { oneOrMore(Digit) }

  def Ident = rule { Alpha ~ zeroOrMore(AlphaNum) }

  def Variable = rule { capture(Ident) ~ WhiteSpace ~> (variable(_)) }

  val WhiteSpaceChar = CharPredicate(" \n\r\t\f")

  def WhiteSpace = rule { zeroOrMore(WhiteSpaceChar) }

  def ws(c: Char) = rule { c ~ WhiteSpace }

  def ws(s: String) = rule { s ~ WhiteSpace }

}
