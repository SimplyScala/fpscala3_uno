package fp.scala.uno.application.api.models

import fp.scala.utils.models.safeuuid.SafeUUID
import zio.json.{DeriveJsonCodec, JsonCodec}
import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec

case class PreparerUnePartie(processUid: SafeUUID, joueurs: Seq[SafeUUID])

object PreparerUnePartieJsonBindings:
	import SafeUUIDJsonCodec.SafeUUIDJsonCodec

	implicit val PreparerUnePartieJsonCodec: JsonCodec[PreparerUnePartie] = DeriveJsonCodec.gen[PreparerUnePartie]
