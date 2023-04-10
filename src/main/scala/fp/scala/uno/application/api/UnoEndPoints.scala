package fp.scala.uno.application.api

import fp.scala.app.api.EndpointsError
import fp.scala.app.bindings.tapir.TapirCodec.*
import fp.scala.uno.application.api.models.PreparerUnePartie
import fp.scala.uno.application.api.models.PreparerUnePartieJsonBindings.PreparerUnePartieJsonCodec
import fp.scala.app.bindings.json.ApiResultsJsonCodec.CRUDResultJsonCodec
import fp.scala.app.models.ApiResults.CRUDResult
import sttp.capabilities.zio.ZioStreams
import sttp.model.sse.ServerSentEvent
import sttp.monad.syntax.MonadErrorOps
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import sttp.tapir.ztapir.*
import sttp.tapir.Endpoint
import zio.*

import java.nio.charset.StandardCharsets

object UnoEndPoints {

	val preparerUnePartieEP: Endpoint[Unit, PreparerUnePartie, EndpointsError, CRUDResult, Any] =
		endpoint.post
			.in("unogame")
			.in(jsonBody[PreparerUnePartie])
			.errorOut(EndpointsError.handleOutError)
			.out(jsonBody[CRUDResult])
			

		/*
		val referentielModuleCommands
        : AppSecuredEndpoint[RequestReferentielModuleCommand, List[ReferentielModuleDomainEvent]] =
        securedEndpoint.patch
            .in(Aggregate.ModuleEnseignement.toString)
            .in(jsonBody[ReferentielModulesJsonCommand])
            .mapInTo[RequestReferentielModuleCommand]
            .out(jsonBody[List[ReferentielModuleDomainEvent]])*/

	/*val streamEvents =
		endpoint.get
			.in("")
			.out(streamTextBody(ZioStreams)(CodecFormat.TextEventStream(), Some(StandardCharsets.UTF_8)))
			.mapOut(ZioServerSentEvents.parseBytesToSSE)(ZioServerSentEvents.serialiseSSEToBytes)*/

}