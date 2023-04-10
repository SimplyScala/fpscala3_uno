package fp.scala.app.api

import fp.scala.uno.application.api.UnoAPI
import fp.scala.app.AppLayer
import sttp.tapir.ztapir.ZServerEndpoint

object DomainAPIs {
	val APIs: List[ZServerEndpoint[AppLayer, Any]] = UnoAPI.endpoints
}