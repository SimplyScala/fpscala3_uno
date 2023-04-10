package fp.scala.uno.application.api

import fp.scala.app.api.EndpointsError
import fp.scala.app.bindings.tapir.TapirCodec.*
import fp.scala.uno.application.api.models.{JouerUnePartieAPICmd, PreparerUnePartieAPICmd}
import fp.scala.uno.application.api.models.PreparerUnePartieJsonBindings.{JouerUnePartieAPICommandJsonCodec, PreparerUnePartieJsonCodec}
import fp.scala.app.bindings.json.ApiResultsJsonCodec.CRUDResultJsonCodec
import fp.scala.app.models.ApiResults.CRUDResult
import fp.scala.utils.models.safeuuid.SafeUUID
import sttp.capabilities.zio.ZioStreams
import sttp.model.StatusCode
import sttp.model.sse.ServerSentEvent
import sttp.monad.syntax.MonadErrorOps
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import sttp.tapir.ztapir.*
import sttp.tapir.Endpoint
import zio.*

import java.nio.charset.StandardCharsets

object UnoEndPoints {

	val preparerUnePartieEP: Endpoint[Unit, PreparerUnePartieAPICmd, EndpointsError, CRUDResult, Any] =
		endpoint.post
			.in("unogame")
			.in(jsonBody[PreparerUnePartieAPICmd])
			.errorOut(EndpointsError.handleOutError)
			.out(jsonBody[CRUDResult].and(statusCode(StatusCode.Created)))

	val jouerUnePartieEP: Endpoint[Unit, (SafeUUID, JouerUnePartieAPICmd), EndpointsError, Unit, Any] =
		endpoint.patch
			.in("unogame" / path[SafeUUID]("uid"))
			.in(jsonBody[JouerUnePartieAPICmd])
			.errorOut(EndpointsError.handleOutError)
			.out(emptyOutput.and(statusCode(StatusCode.Ok)))

	/*val streamEvents =
		endpoint.get
			.in("")
			.out(streamTextBody(ZioStreams)(CodecFormat.TextEventStream(), Some(StandardCharsets.UTF_8)))
			.mapOut(ZioServerSentEvents.parseBytesToSSE)(ZioServerSentEvents.serialiseSSEToBytes)*/

}