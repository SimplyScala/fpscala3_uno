package fp.scala.uno.domain.decider

import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.uno.domain.models.joueurs.Joueur
//import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.utils.models.nel.NEL
import fp.scala.utils.models.safeuuid.SafeUUID

private[decider] trait DemarrerUnePartie {
	protected def distribuerLesCartesAuxJoueurs(/*joueurs: Joueurs,
	                                            */pioche: NEL[CarteDeUno],
	                                            nbDeCarteADistribuer: Int): List[UnoEvent] = ???
		/*val nbJoueurs = joueurs.size

		val cardsToPick = (1 :: (1 to nbDeCarteADistribuer * nbJoueurs)
			.toList
			.filter { i => i % nbJoueurs == 0 }
			.map { _ + 1 })
			.take(nbDeCarteADistribuer)

		val result = joueurs.toSet.foldLeft(MainDesJoueurs()) { case (acc, joueur) =>
			val pick = cardsToPick.map { _ + acc.counter }

			val (carteDistribuées, restePioche) = pioche.zipWithIndex.partition { case (carte, i) => pick.exists { _ == i +1 } }
			val joueurAvecMain = joueur.copy(main = carteDistribuées.map { _._1 })

			acc.copy(
				counter = acc.counter +1,
				//cartesARetirer = acc.cartesARetirer ++ pick,
				joueursAvecMains = acc.joueursAvecMains ++ Map(joueur.uid -> carteDistribuées.map { _._1 })
			)
		}

		//val piocheRestante = pioche.zipWithIndex.filterNot { case (_, i) => result.cartesARetirer.exists { _ == i +1 } }.map { _._1 }

		result.joueursAvecMains.flatMap { case (jUid, cartes) =>
			cartes.map { carte => UnoEvent.UneCarteAEteDistribuee(jUid, carte) }
		}
		.toList*/

	private case class MainDesJoueurs(counter: Int = 0,
	                                  //cartesARetirer: Seq[Int] = Nil,
	                                  joueursAvecMains: Map[SafeUUID, List[CarteDeUno]] = Map())
}