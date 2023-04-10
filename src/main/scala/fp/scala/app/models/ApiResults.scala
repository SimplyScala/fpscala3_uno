package fp.scala.app.models

import fp.scala.utils.models.safeuuid.SafeUUID

object ApiResults:
	case class CRUDResult(uid: SafeUUID)