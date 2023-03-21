package fp.scala.utils.base

package object dsl {
	extension [A](x: A)
		def right: Either[Nothing, A] = Right(x)
		def left: Either[A, Nothing] = Left(x)
}