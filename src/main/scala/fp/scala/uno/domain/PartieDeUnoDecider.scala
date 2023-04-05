package fp.scala.uno.domain

import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.commands.UnoCommand.*
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.events.UnoEvent.*
import fp.scala.uno.domain.UnoErreur
import fp.scala.uno.domain.UnoErreur.*
import fp.scala.uno.domain.models.PartieDeUno
import fp.scala.uno.domain.models.PartieDeUno.*
import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.uno.domain.models.joueurs.Joueurs.*
import fp.scala.utils.base.dsl.*

trait PartieDeUnoDecider {
	def decide(commande: UnoCommand, state: PartieDeUno): Either[UnoErreur, Seq[UnoEvent]] = commande match {
		case PreparerUnePartie(joueurs, pioche) =>
			Joueurs(joueurs.toSet)
				.map { js => LaPartieEstPreteAJouer(js, pioche) :: Nil }
				.mapLeft { _ => IlFaut3JoueursMinimum }

		case DemarrerLaPartie(nbCartes) =>
			state match {
				case PartieAPreparer => ???
				case PartiePrete(joueurs, sensDeLaPartie, pioche, talon) =>
					(LaPartieADemarree(nbCartes) :: Nil).right
			}

		case JouerUneCarte(joueur, carteJouee) => state match {
			case PartieAPreparer => ???
			// TODO joue une carte qu'il n'a pas ANTI-TRICHE
			case PartiePrete(joueurs, sensDeLaPartie, pioche, talon) =>
				(UnJoueurAJoueUneCarte(joueur, carteJouee) :: Nil).right
		}
	}
}


/**
 * On vérifie qu'il y a le bon nombre de joueurs
 * On distribue n cartes à chaque joueur
 * */
/*def PréparerUnePartie(joueurs: Seq[Joueur],
					  pioche: Seq[CarteDeUno],
					  nbDeCarteADistribuer: Int): Either[UnoErreur, PartieDeUno] = {
	val nbJoueurs = joueurs.size

	val cardsToPick = (1 :: (1 to nbDeCarteADistribuer * nbJoueurs)
		.toList
		.filter { i => i % nbJoueurs == 0 }
		.map { _ +1 })
		.take(nbDeCarteADistribuer)

	val result = joueurs.foldLeft(MainDesJoueurs()) { case (acc, joueur) =>
		val pick = cardsToPick.map { _ + acc.counter }

		val (carteDistribuées, restePioche) = pioche.zipWithIndex.partition { case (carte, i) => pick.exists { _ == i +1 } }
		val joueurAvecMain = joueur.copy(main = carteDistribuées.map { _._1 })

		acc.copy(
			counter = acc.counter +1,
			cartesARetirer = acc.cartesARetirer ++ pick,
			joueursAvecMains = acc.joueursAvecMains :+ joueurAvecMain
		)
	}

	val piocheRestante = pioche.zipWithIndex.filterNot { case (_, i) => result.cartesARetirer.exists { _ == i +1 } }.map { _._1 }

	Joueurs(result.joueursAvecMains.toSet)
		.map { js => PartieDeUno(js, SensDeLaPartie.SensHoraire, piocheRestante, Nil) }
		.mapLeft { _ => IlFaut3JoueursMinimum }

	trait PreparerUnePartie {
	protected case class MainDesJoueurs(counter: Int = 0,
	                                    cartesARetirer: Seq[Int] = Nil,
	                                    joueursAvecMains: Seq[Joueur] = Nil)
}

}*/

/**
 * Quand le 1er joueur est prêt il lance la partie
 * On retourne la première carte de la pioche et on la met dans le talon
 */
/*def CommencerLaPartie(partie: PartieDeUno): PartieDeUno =
	partie.copy(pioche = partie.pioche.tail, talon = partie.pioche.head :: Nil)*/