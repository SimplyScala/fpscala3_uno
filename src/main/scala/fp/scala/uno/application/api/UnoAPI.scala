package fp.scala.uno.application.api

import fp.scala.uno.application.api.models.PreparerUnePartie
import fp.scala.uno.repository.models.events.{AggregateUid, ProcessUid}
import fp.scala.uno.service.UnoCommandHandler
import sttp.tapir.ztapir.ZServerEndpoint
import sttp.tapir.ztapir.RichZEndpoint
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.models.ListeDesCartes
import fp.scala.uno.domain.models.joueurs.Joueur
import fp.scala.app.models.ApiResults.CRUDResult
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.app.AppLayer
import zio.{IO, ZIO}
import zio.prelude.AnySyntax


object UnoAPI {
	type UnoAPIDeps = UnoCommandHandler

	lazy val endpoints: List[ZServerEndpoint[UnoAPIDeps, Any]] = preparerUnePartie :: Nil

	/*
	{ "processUid": "98064f99-fc82-4634-86e3-28d048be3754"
	, "joueurs": ["98064f99-fc82-4634-86e3-28d048be3753", "98064f99-fc82-4634-86e3-28d048be3752", "98064f99-fc82-4634-86e3-28d048be3759"]
	}
	*/
	val preparerUnePartie: ZServerEndpoint[UnoAPIDeps, Any]/*: Endpoint[Unit, PreparerUnePartie, Unit, CRUDResult, Any] =*/ =
		UnoEndPoints.preparerUnePartieEP.zServerLogic { (req: PreparerUnePartie) =>
			val aggregateUid = AggregateUid.generate
			val processUid = ProcessUid(req.processUid)

			for
				joueurs <- getJoueurs(req.joueurs)
				unoCommand = UnoCommand.PreparerUnePartie(joueurs, ListeDesCartes.pioche)
				ch <- ZIO.service[UnoCommandHandler]
				events <- ch.processCommand(processUid, aggregateUid, unoCommand)
							.mapError { UnoAPIError.toEndpointsError(_) }
			yield CRUDResult(aggregateUid.safeUUID)

			// TODO pousser les events dans une queue pour transmettre aux joueurs
		}

	private def getJoueurs(js: Seq[SafeUUID]): IO[Nothing, Seq[Joueur]] =
		val joueurs = js
			.zipWithIndex
			.map { case (j, i) => Joueur(j, s"Joueur ${i +1}", i +1, Nil) }

		ZIO.succeed(joueurs)
}