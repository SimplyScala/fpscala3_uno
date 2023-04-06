package fp.scala.utils.models

import zio.prelude.NonEmptyList

package object nel {
	type NEL[+A] = NonEmptyList[A]

	def nel[A](x: A, xs: A*): NonEmptyList[A] = NonEmptyList(x, xs:_*)

	extension [A](xs: Seq[A]) {
		def toNEL: Option[NEL[A]] = xs.headOption.map { h => nel[A](h, xs.tail: _*) }
	}
}