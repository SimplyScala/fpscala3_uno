package fp.scala.app.api

import fp.scala.uno.application.api.UnoAPI
import fp.scala.app.AppLayer
import fp.scala.uno.application.api.UnoAPI.UnoAPIDeps
import sttp.capabilities.zio.ZioStreams
import sttp.tapir.ztapir.ZServerEndpoint
import sttp.tapir.ztapir.*

object APIRoutes:
	val APIs: List[ZServerEndpoint[AppLayer, Any]] = UnoAPI.endpoints.map { x => x.widen[AppLayer] }
	val STREAM_APIs: List[ZServerEndpoint[AppLayer, ZioStreams]] = UnoAPI.endpointsStreams.map { x => x.widen[AppLayer] }