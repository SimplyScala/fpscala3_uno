package fp.scala.app.bindings.tapir

import fp.scala.utils.models.safeuuid.*
import sttp.tapir.Schema
import sttp.tapir.Codec.PlainCodec
import sttp.tapir.Schema.SName
import sttp.tapir.SchemaType.{SCoproduct, SDiscriminator, SRef}
import sttp.tapir.{Codec, DecodeResult, FieldName, Schema, SchemaType, Validator}
import zio.prelude.NonEmptyList

object TapirCodec:
	implicit val safeUUIDTapirCodec: PlainCodec[SafeUUID] =
		def decode(s: String): DecodeResult[SafeUUID] = SafeUUID(s).fold(
			e => DecodeResult.Error(s, new IllegalArgumentException(e)),
			uid => DecodeResult.Value(uid)
		)

		Codec.string.mapDecode(decode)(_.safeValue)

	implicit val safeUUIDTapirSchema: Schema[SafeUUID] = Schema.string
	implicit val validatorForSafeUUID: Validator[SafeUUID] = Validator.pass

	implicit def schemaForNEL[A](implicit x: Schema[A]): Schema[NonEmptyList[A]] =
		Schema[NonEmptyList[A]](SchemaType.SArray(implicitly[Schema[A]])(_.toList))
	implicit def validatorForNEL[A]: Validator[NonEmptyList[A]] = Validator.pass