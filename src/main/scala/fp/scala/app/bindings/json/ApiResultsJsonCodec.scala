package fp.scala.app.bindings.json

import zio.json.{DeriveJsonCodec, JsonCodec}
import fp.scala.app.models.ApiResults.*
import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec

object ApiResultsJsonCodec:
	import SafeUUIDJsonCodec.SafeUUIDJsonCodec
	
	implicit val CRUDResultJsonCodec: JsonCodec[CRUDResult] = DeriveJsonCodec.gen[CRUDResult]
	