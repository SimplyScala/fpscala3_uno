package fp.scala.utils.models

import fp.scala.utils.typeclass.show.Show
import fp.scala.utils.typeclass.eq.Eq

import java.util.UUID
import zio.prelude.*

import scala.util.{Failure, Success, Try}

package object safeuuid:
	private [safeuuid] object SafeUUIDT extends Newtype[String]
	type SafeUUID = SafeUUIDT.Type

	object SafeUUID:
		def apply(rawValue: String): Either[String, SafeUUID] = Try(UUID.fromString(rawValue)) match {
			case Failure(_)     => Left(s"[$rawValue] has not the good format (need SHA-256 String format)")
			case Success(value) => Right(SafeUUIDT(value.toString))
		}

		def apply(javaUUID: UUID): SafeUUID = SafeUUIDT(javaUUID.toString)

		def generate: SafeUUID = SafeUUIDT(java.util.UUID.randomUUID().toString)

	extension (uid: SafeUUID)
		def safeValue: String = SafeUUIDT.unwrap(uid)

	object Typeclass:
		/*inline */given Show[SafeUUID] with
			def apply(x: SafeUUID): String = x.safeValue
			
		given Eq[SafeUUID] with
			def equal(x: SafeUUID, y: SafeUUID) = x.safeValue == y.safeValue	
				