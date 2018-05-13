package replfun

import scalaz.{ Equal, Functor, Show }
import matryoshka.Delay
import matryoshka.data.Fix

//abstract syntax tree
object abstracts {

  sealed trait ExprF[+A]

  case class Function() extends ExprF[Nothing]

  case class Constant(value: Int) extends ExprF[Nothing]
  case class Variable(name: String) extends ExprF[Nothing]

  case class UMinus[A](expr: A) extends ExprF[A]
  case class Plus[A](left: A, right: A) extends ExprF[A]
  case class Minus[A](left: A, right: A) extends ExprF[A]
  case class Times[A](left: A, right: A) extends ExprF[A]
  case class Div[A](left: A, right: A) extends ExprF[A]
  case class Mod[A](left: A, right: A) extends ExprF[A]

  case class Assignment[A](left: List[String], right: A) extends ExprF[A]
  case class Cond[A](guard: A, thenBranch: A, elseBranch: A) extends ExprF[A]
  case class Loop[A](guard: A, body: A) extends ExprF[A]
  case class Block[A](expressions: List[A]) extends ExprF[A]

  case class Func[A](args: List[String], body: A) extends ExprF[A]
  case class Apply[A](func: A, args: List[A]) extends ExprF[A]
  case class Struct[A](fields: Map[String, A]) extends ExprF[A]
  case class Select[A](receiver: A, field: String) extends ExprF[A]

  type Expr = Fix[ExprF]

  object factory {
    def constant(c: Int) = Fix[ExprF](Constant(c))
    def function() = Fix[ExprF](Function())
    def variable(n: String) = Fix[ExprF](Variable(n))
    def uminus(r: Expr) = Fix[ExprF](UMinus(r))
    def plus(l: Expr, r: Expr) = Fix[ExprF](Plus(l, r))
    def minus(l: Expr, r: Expr) = Fix[ExprF](Minus(l, r))
    def times(l: Expr, r: Expr) = Fix[ExprF](Times(l, r))
    def div(l: Expr, r: Expr) = Fix[ExprF](Div(l, r))
    def mod(l: Expr, r: Expr) = Fix[ExprF](Mod(l, r))
    def assignment(l: List[String], r: Expr) = Fix[ExprF](Assignment(l, r))
    def cond(g: Expr, t: Expr, e: Expr) = Fix[ExprF](Cond(g, t, e))
    def loop(g: Expr, b: Expr) = Fix[ExprF](Loop(g, b))
    def block(es: Expr*) = Fix[ExprF](Block(es.toList))
    def func(as: List[String], b: Expr) = Fix[ExprF](Func(as, b))
    def apply(f: Expr, xs: Expr*) = Fix[ExprF](Apply(f, xs.toList))
    def struct(fs: (String, Expr)*) = Fix[ExprF](Struct(fs.toMap))
    def select(r: Expr, n: String) = Fix[ExprF](Select(r, n))
  }
  
}
