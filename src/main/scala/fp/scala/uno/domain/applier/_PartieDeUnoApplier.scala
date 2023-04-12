package fp.scala.uno.domain.applier

import fp.scala.uno.domain.UnoErreur
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.events.UnoEvent.*
import fp.scala.uno.domain.models.PartieDeUno.*
import fp.scala.uno.domain.models.{CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.utils.base.dsl.*
import fp.scala.utils.models.nel.nel
import fp.scala.utils.models.safeuuid.Typeclass.*
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.utils.models.safeuuid.Typeclass.given_Eq_SafeUUID

trait _PartieDeUnoApplier {
	def apply(event: UnoEvent, state: PartieDeUno): PartieDeUno = event match {

		case LaPartieEstPreteAJouer(joueurs, pioche) =>
			PartiePrete(joueurs, SensDeLaPartie.SensHoraire, pioche)

		case LaPartieADemarree(nbDeCarteADistribuer) => state match {
			case PartieAPreparer => ???
			/** la 1ere carte de la pioche est retournée dans le talon */
			case PartiePrete(joueurs, sensDeLaPartie, pioche) =>
				PartieEnCours(joueurs, sensDeLaPartie, None, pioche.tail, nel(pioche.head))

			case PartieEnCours(_, _, _, _, _) => ???
		}

		case UneCarteAEteDistribuee(joueurId, carte) => partieEnCours(state) { partie =>
			partie.copy(
				pioche = partie.pioche.foldLeft((false, List[CarteDeUno]())) { case ((firstOut, cartes), carte) =>
					if(carte === carte && !firstOut) (true, cartes)
					else (firstOut, cartes :+ carte)
				}
				._2,
				joueurs = partie.joueurs.foreach { joueur =>
					if(joueur.uid === joueurId) joueur.copy(main = joueur.main :+ carte)
					else joueur
				}
			)
		}

		case UnJoueurAJoueUneCarte(joueur, carteJouee) => partieEnCours(state) { partie =>
		    partie.copy(dernierJoueur = joueur.some)
		}

		case UnJoueurAPiocheUneCarte(joueur, cartePiochee) => ???

		case UnJoueurAJoueUneMauvaiseCarte(joueur, carteJouee) => ???


		case UnJoueurNaPasJoueASonTour(joueur) => state

		/*
		joueurs.updateIf(_.id === joueur) { j => j.copy(main = ) }
				PartiePrete(joueurs, sensDeLaPartie, pioche, talon :+ carteJouee)
		*/
		//partie.copy(pioche = partie.pioche.tail, talon = partie.pioche.head :: Nil)
	}

	private def partieEnCours(currentState: PartieDeUno)
	                         (ifPartieEnCoursAction: PartieEnCours => PartieEnCours):PartieEnCours = currentState match {
		case PartieAPreparer => ???
		case _: PartiePrete => ???
		case p: PartieEnCours => ifPartieEnCoursAction(p)
	}
}