package fp.scala.utils.typeclass

import scala.deriving.Mirror
import scala.compiletime.{erasedValue, summonInline}

package object show {
	trait Show[T]:
		def show(x: T): String

	object Show:
		inline given derived[T](using m: Mirror.Of[T]): Show[T] =
			lazy val shows = summonAll[m.MirroredElemTypes]
			inline m match
				case s: Mirror.SumOf[T] => showSum(s, shows)
				case _: Mirror.ProductOf[T] => showProduct(shows)

	def showProduct[T](shows: => List[Show[_]]): Show[T] =                    // (1)
		new Show[T]:                                                            // (2)
			def show(t: T): String = {
				(t.asInstanceOf[Product].productIterator).zip(shows.iterator).map { // (3)
					case (p, s) => s.asInstanceOf[Show[Any]].show(p)                  // (4)
				}.mkString
			}

	def showSum[T](s: Mirror.SumOf[T], shows: => List[Show[_]]): Show[T] = // (1)
		new Show[T]:
			def show(t: T): String = {
				val index = s.ordinal(t)                                         // (2)
				shows(index).asInstanceOf[Show[Any]].show(t)                     // (3)
			}

	inline def summonAll[T <: Tuple]: List[Show[_]] =
		inline erasedValue[T] match
			case _: EmptyTuple => Nil
			case _: (t *: ts) => summonInline[Show[t]] :: summonAll[ts]
}