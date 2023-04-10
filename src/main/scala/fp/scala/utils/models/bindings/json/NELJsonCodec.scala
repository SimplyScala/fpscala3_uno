package fp.scala.utils.models.bindings.json

import fp.scala.utils.models.nel.NEL
import zio.json.JsonCodec
import zio.prelude.NonEmptyList

object NELJsonCodec {
	implicit def NELJsonCodec[A: JsonCodec]: JsonCodec[NEL[A]] =
		JsonCodec[Seq[A]].transformOrFail(
			NonEmptyList.fromIterableOption(_).toRight("should be nonempty list"),
			_.toList
		)
}