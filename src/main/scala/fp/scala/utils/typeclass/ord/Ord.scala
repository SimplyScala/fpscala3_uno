package fp.scala.utils.typeclass.ord

import fp.scala.utils.typeclass.show.Show
import scala.deriving.*
import scala.compiletime.{erasedValue, summonInline}

enum Order derives Show:
	case EQ
	case LT
	case GT

/**
Comparability
    x <= y || y <= x = True
Transitivity
    if x <= y && y <= z = True, then x <= z = True
Reflexivity
    x <= x = True
Antisymmetry
    if x <= y && y <= x = True, then x == y = True

The following operator interactions are expected to hold:
    x >= y = y <= x
    x < y = x <= y && x /= y
    x > y = y < x
    x < y = compare x y == LT
    x > y = compare x y == GT
    x == y = compare x y == EQ
    min x y == if x <= y then x else y = True
    max x y == if x >= y then x else y = True
*/
trait Ord[T]:
	def compare(x: T, y: T): Order

object Ord:
	def apply[T](using o: Ord[T]) = o

	extension [T](x: T)(using o: Ord[T])
		def > (y: T): Boolean = o.compare(x, y) == Order.GT
		def < (y: T): Boolean = o.compare(x, y) == Order.LT
		def >=(y: T): Boolean = o.compare(x, y) == Order.GT || o.compare(x, y) == Order.EQ
		def <=(y: T): Boolean = o.compare(x, y) == Order.LT || o.compare(x, y) == Order.EQ

	inline given derived[T](using m: Mirror.Of[T]): Ord[T] =
		lazy val elemInstances = DerivedOrd.summonAll[m.MirroredElemTypes]
		inline m match
			case s: Mirror.SumOf[T]     => DerivedOrd.ordSum(s, elemInstances)
			// TODO je ne veux pas gérer les Product
			case p: Mirror.ProductOf[T] => ??? //DerivedOrd.ordProduct(p, elemInstances)

private[ord] object DerivedOrd:
	inline def summonAll[T <: Tuple]: List[Ord[_]] =
		inline erasedValue[T] match
			case _: EmptyTuple => Nil
			case _: (t *: ts) => summonInline[Ord[t]] :: summonAll[ts]

	given Ord[Int] with
		def compare(x: Int, y: Int) = if(x == y) Order.EQ else if(x < y) Order.LT else Order.GT

	def iterator[T](p: T) = p.asInstanceOf[Product].productIterator

	def ordSum[T](s: Mirror.SumOf[T], elems: => List[Ord[_]]): Ord[T] =
		new Ord[T]:
			def compare(x: T, y: T): Order =
				val ordx = s.ordinal(x)
				val ordy = s.ordinal(y)
				if(ordx == ordy) Order.EQ
				else if(ordx < ordy) Order.LT
				else Order.GT

	/*def ordProduct[T](p: Mirror.ProductOf[T], elems: => List[Ord[_]]): Ord[T] =
		new Ord[T]:
			def equal(x: T, y: T): Boolean =
				iterator(x).zip(iterator(y)).zip(elems.iterator).forall {
					case ((x, y), elem) => check(elem)(x, y)
				}*/