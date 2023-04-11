package fp.scala.uno.application.api

import fp.scala.uno.application.api.models.{JouerUnePartieAPICmd, PreparerUnePartieAPICmd}
import JouerUnePartieAPICmd.*
import fp.scala.uno.repository.models.events.{AggregateUid, ProcessUid}
import fp.scala.uno.service.{EventStreamer, UnoCommandHandler}
import sttp.tapir.ztapir.ZServerEndpoint
import sttp.tapir.ztapir.RichZEndpoint
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.models.ListeDesCartes
import fp.scala.uno.domain.models.joueurs.Joueur
import fp.scala.app.models.ApiResults.CRUDResult
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.app.AppLayer
import fp.scala.uno.service.EventStreamer.EventRecever
import sttp.capabilities.zio.ZioStreams
import zio.{Duration, Hub, IO, Scope, ZIO}
import sttp.model.sse.ServerSentEvent
import zio.json.*
import zio.stream.*
import zio.prelude.AnySyntax
import fp.scala.utils.base.dsl.*

import java.util.concurrent.TimeUnit


object UnoAPI {
	type UnoAPIDeps = UnoCommandHandler & EventStreamer.EventRecever[UnoEvent]/* & Scope*/

	lazy val endpoints: List[ZServerEndpoint[UnoAPIDeps, Any]] = preparerUnePartie :: jouerUnePartie :: Nil
	lazy val endpointsStreams: List[ZServerEndpoint[UnoAPIDeps, ZioStreams]] = streamEvents :: Nil

	private val preparerUnePartie: ZServerEndpoint[UnoAPIDeps, Any]/*: Endpoint[Unit, PreparerUnePartie, Unit, CRUDResult, Any] =*/ =
		UnoEndPoints.preparerUnePartieEP.zServerLogic { (req: PreparerUnePartieAPICmd) =>
			val aggregateUid = AggregateUid.generate
			val processUid = ProcessUid(req.processUid)

			/*
			import zio.direct.*
			defer {
				val get: IO[Nothing, Seq[Joueur]] = getJoueurs(req.joueurs)
				val joueurs: Seq[Joueur] =  get.run
				//création de la commande de préparation de partie
				val unoCommand: UnoCommand.PreparerUnePartie = UnoCommand.PreparerUnePartie(joueurs, ListeDesCartes.pioche)

				//récupération du service
				val ch: UnoAPIDeps = ZIO.service[UnoCommandHandler].run

				//traitement de la commande
				val process: ZIO[Any, EndpointsError, Seq[RepositoryEvent[UnoEvent]]] = ch.processCommand(processUid, aggregateUid, unoCommand).mapError(UnoAPIError.toEndpointsError)
				val events: Seq[RepositoryEvent[UnoEvent]] = process.run

				CRUDResult(aggregateUid.safeUUID)
			}*/
			for
				joueurs <- getJoueurs(req.joueurs)
				unoCommand = UnoCommand.PreparerUnePartie(joueurs, ListeDesCartes.pioche)

				ch <- ZIO.service[UnoCommandHandler]
				q <- ZIO.service[EventStreamer.EventRecever[UnoEvent]]

				events <- ch.processCommand(processUid, aggregateUid, unoCommand).mapError { UnoAPIError.toEndpointsError(_) }

				_ <- q.publishAll(events.map { _.event })
			yield CRUDResult(aggregateUid.safeUUID)
		}

	private val jouerUnePartie: ZServerEndpoint[UnoAPIDeps, Any] =
		UnoEndPoints.jouerUnePartieEP.zServerLogic { (aUid: SafeUUID, req: JouerUnePartieAPICmd) =>
			val (processUid, command) = req.toDomain//.map { case (p, c) => (ProcessUid(p), c) }
			val aggregateUid = AggregateUid(aUid)

			for
				ch <- ZIO.service[UnoCommandHandler]
				q <- ZIO.service[EventStreamer.EventRecever[UnoEvent]]

				events <- ch.processCommand(ProcessUid(processUid), aggregateUid, command).mapError { UnoAPIError.toEndpointsError(_) }

				_ <- q.publishAll(events.map { _.event })
			yield ()
		}

	private val streamEvents: ZServerEndpoint[UnoAPIDeps, ZioStreams] = UnoEndPoints.streamEventsEP.zServerLogic { (aggregateUid: SafeUUID) =>
		import fp.scala.uno.service.UnoEventJsonCodec.UnoEventJsonCodec

		for
			q <- ZIO.service[EventStreamer.EventRecever[UnoEvent]]
			stream = ZStream.fromHub(q)
			//_ <- ZIO.logInfo("someone listen")
			newStream = stream.map { event => ServerSentEvent(Some(event.toJson), Some("UnoEvent")) }
			/** nécessaire pour démarrer la connection avec le client qui subscribe le SSE */
			ackStream = ZStream(ServerSentEvent("{message: Start listenning UnoEvent}".some))
			//tStream = ZStream.tick(Duration(500, TimeUnit.MILLISECONDS)).map { _ => ServerSentEvent(Some("tick")) }
		yield ackStream ++ newStream
	}

	private def getJoueurs(js: Seq[SafeUUID]): IO[Nothing, Seq[Joueur]] =
		val joueurs = js
			.zipWithIndex
			.map { case (j, i) => Joueur(j, s"Joueur ${i +1}", i +1, Nil) }

		ZIO.succeed(joueurs)
}