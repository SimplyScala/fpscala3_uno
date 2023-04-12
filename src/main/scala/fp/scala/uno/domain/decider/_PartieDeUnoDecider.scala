package fp.scala.uno.domain.decider

import fp.scala.uno.domain.UnoErreur
import fp.scala.uno.domain.UnoErreur.*
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.commands.UnoCommand.*
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.events.UnoEvent.*
import fp.scala.uno.domain.models.CarteDeUno.*
import fp.scala.uno.domain.models.PartieDeUno.*
//import fp.scala.uno.domain.models.joueurs.Joueurs
//import fp.scala.uno.domain.models.joueurs.Joueurs.*
import fp.scala.uno.domain.models.{CarteDeUno, PartieDeUno}
import fp.scala.utils.base.dsl.*
import fp.scala.utils.models.nel.NEL
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.utils.models.safeuuid.Typeclass.given_Eq_SafeUUID

trait _PartieDeUnoDecider extends DemarrerUnePartie {
	def decide(commande: UnoCommand, state: PartieDeUno): Either[UnoErreur, Seq[UnoEvent]] = ???
		/*commande match {
		case PreparerUnePartie(joueurs, pioche) =>
			Joueurs(joueurs.toSet)
				.map { js => LaPartieEstPreteAJouer(js, pioche) :: Nil }
				.mapLeft { _ => IlFaut3JoueursMinimum }

		case DemarrerLaPartie(nbCartes) =>
			state match {
				case PartieAPreparer => ???
				case PartiePrete(joueurs, sensDeLaPartie, pioche) =>
					(LaPartieADemarree(nbCartes) :: distribuerLesCartesAuxJoueurs(joueurs, pioche, nbCartes)).right
				case _: PartieEnCours => ???
			}

		case JouerUneCarte(joueur, carteJouee) => partieEnCours(state, commande) { partieEncours =>
			val result = for {
				_ <- estCeAMonTourDeJouer(joueur, partieEncours)
				_ <- estCeLaBonneCarteAjouer(joueur, partieEncours.talon, carteJouee)
			} yield ()
			
			result.inverse.getOrElse(UnJoueurAJoueUneCarte(joueur, carteJouee) :: Nil).right
		}

		case PiocherUneCarte(joueur) => partieEnCours(state, commande) { partieEncours =>
			(UnJoueurAPiocheUneCarte(joueur, partieEncours.pioche.head) :: Nil).right
		}
	}*/

	/*private def estCeAMonTourDeJouer(joueur: SafeUUID, partie: PartieEnCours): Either[Seq[UnoEvent], Unit] =
		(for {
			dernierJoueur <- partie.joueurs.find { _.uid.some === partie.dernierJoueur }
			joueurTesté <- partie.joueurs.find { _.uid === joueur }
		} yield { // TODO check sens de la partie
			if(joueurTesté.placement == dernierJoueur.placement +1) ().right
			else (UnJoueurNaPasJoueASonTour(joueur) :: Nil).left
		})
		.getOrElse {
			val bonplacement = partie.joueurs
				.find { _.uid === joueur }
				.exists { _.placement == 1 }

			if(bonplacement) ().right else (UnJoueurNaPasJoueASonTour(joueur) :: Nil).left
		}*/

	/*private def estCeLaBonneCarteAjouer(joueur: SafeUUID,
	                                    pioche: NEL[CarteDeUno],
	                                    carteJouee: CarteDeUno): Either[Seq[UnoEvent], Unit] =
		pioche.head match {
			case CarteNumerique(valeur, couleur) => carteJouee match {
				case CarteNumerique(v2, c2) =>
					if(valeur === v2 || couleur == c2) ().right
					else (UnJoueurAJoueUneMauvaiseCarte(joueur, carteJouee) :: Nil).left
				case Joker(_, couleur) => ???
				case Plus4Cartes => ???
				case ChangementDeCouleur => ???
			}
			case Joker(type_, couleur) => ???
			case Plus4Cartes => ???
			case ChangementDeCouleur => ???
		}*/

	/*private def partiePreteAjouer(currentState: PartieDeUno, command: UnoCommand)
	                             (ifPartiePreteAction: PartiePrete => Either[UnoErreur, Seq[UnoEvent]]): Either[UnoErreur, Seq[UnoEvent]] = currentState match {
		case PartieAPreparer => ???
		case p: PartiePrete   => ifPartiePreteAction(p)
		case _: PartieEnCours => ???
	}*/

	/*private def partieEnCours(currentState: PartieDeUno, command: UnoCommand)
	                         (ifPartieEnCoursAction: PartieEnCours => Either[UnoErreur, Seq[UnoEvent]]): Either[UnoErreur, Seq[UnoEvent]] = currentState match {
		case PartieAPreparer => ???
		case _: PartiePrete => ???
		case p: PartieEnCours => ifPartieEnCoursAction(p)
	}*/
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