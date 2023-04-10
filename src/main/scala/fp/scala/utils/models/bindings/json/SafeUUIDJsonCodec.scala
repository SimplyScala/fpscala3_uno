package fp.scala.utils.models.bindings.json

import fp.scala.utils.models.safeuuid.*
import zio.json.{DeriveJsonCodec, JsonCodec}

object SafeUUIDJsonCodec:
	implicit val SafeUUIDJsonCodec: JsonCodec[SafeUUID] = JsonCodec.string.transformOrFail(SafeUUID.apply, _.safeValue)