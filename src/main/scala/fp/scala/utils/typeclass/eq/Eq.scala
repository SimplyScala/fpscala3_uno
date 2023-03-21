package fp.scala.utils.typeclass.eq

import scala.deriving.*
import scala.compiletime.{erasedValue, summonInline}

trait Eq[T]:
	def equal(x: T, y: T): Boolean

object Eq:
	def apply[T](using eq: Eq[T]) = eq

	extension[T] (x:T)(using eq: Eq[T])
		def isEqualTo(y: T): Boolean = eq.equal(x, y)
		def ===(y: T): Boolean = eq.equal(x, y)
		def /==(y: T): Boolean = !eq.equal(x, y)

	inline given derived[T](using m: Mirror.Of[T]): Eq[T] =
		lazy val elemInstances = DerivedEq.summonAll[m.MirroredElemTypes]
		inline m match
			case s: Mirror.SumOf[T]     => DerivedEq.eqSum(s, elemInstances)
			case p: Mirror.ProductOf[T] => DerivedEq.eqProduct(p, elemInstances)

private[eq] object DerivedEq:
	inline def summonAll[T <: Tuple]: List[Eq[_]] =
		inline erasedValue[T] match
			case _: EmptyTuple => Nil
			case _: (t *: ts) => summonInline[Eq[t]] :: summonAll[ts]

	given Eq[Int] with
		def equal(x: Int, y: Int) = x == y

	def check(elem: Eq[_])(x: Any, y: Any): Boolean =
		elem.asInstanceOf[Eq[Any]].equal(x, y)

	def iterator[T](p: T) = p.asInstanceOf[Product].productIterator

	def eqSum[T](s: Mirror.SumOf[T], elems: => List[Eq[_]]): Eq[T] =
		new Eq[T]:
			def equal(x: T, y: T): Boolean =
				val ordx = s.ordinal(x)
				(s.ordinal(y) == ordx) && check(elems(ordx))(x, y)

	def eqProduct[T](p: Mirror.ProductOf[T], elems: => List[Eq[_]]): Eq[T] =
		new Eq[T]:
			def equal(x: T, y: T): Boolean =
				iterator(x).zip(iterator(y)).zip(elems.iterator).forall {
					case ((x, y), elem) => check(elem)(x, y)
				}