package fp.scala.utils.base

package object dsl {
	extension [A](x: A)
		def right: Either[Nothing, A] = Right(x)
		def left: Either[A, Nothing] = Left(x)
		
	extension [E, A](x: Either[E, A])
		def mapLeft[EE](f: E => EE): Either[EE, A] = x match {
			case Left(e) => Left(f(e))
			case Right(x) => Right(x)
		}	
}