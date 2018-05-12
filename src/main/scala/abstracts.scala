package replfun

import scalaz.{ Equal, Functor, Show }
import matryoshka.Delay
import matryoshka.data.Fix

//abstract syntax tree
object abstracts {

    sealed trait ExprF[+A]

    case class Constant(value: Int) extends ExprF[Nothing]
    case class Variable(name: String) extends ExprF[Nothing]
    case class Block[A](expressions: List[A]) extends ExprF[A]

    implicit object exprFFunctor extends Functor[ExprF] {
      def map[A, B](fa: ExprF[A])(f: A => B): ExprF[B] = fa match {
        case e @ Constant(v) => e
        case e @ Variable(n) => e
        case Block(es) => Block(es map f)
      }
    }

    type Expr = Fix[ExprF]

    object factory {
      def constant(c: Int) = Fix[ExprF](Constant(c))
      def variable(n: String) = Fix[ExprF](Variable(n))
      def block(es: Expr*) = Fix[ExprF](Block(es.toList))
    }
}
