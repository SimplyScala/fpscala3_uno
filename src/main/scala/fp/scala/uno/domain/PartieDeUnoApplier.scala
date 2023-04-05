package fp.scala.uno.domain

import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.events.UnoEvent.*
import fp.scala.uno.domain.models.PartieDeUno
import fp.scala.uno.domain.models.PartieDeUno.*
import fp.scala.uno.domain.models.SensDeLaPartie
import fp.scala.utils.base.dsl._

trait PartieDeUnoApplier {
	def apply(event: UnoEvent, state: PartieDeUno): PartieDeUno = event match {
		case LaPartieEstPreteAJouer(joueurs, pioche) =>
			PartiePrete(joueurs, SensDeLaPartie.SensHoraire, pioche, Nil)

		case LaPartieADemarree(nbDeCarteADistribuer) => state match {
			case PartieAPreparer => ???
			/** la 1ere carte de la pioche est retournée dans le talon */
			case PartiePrete(joueurs, sensDeLaPartie, pioche, talon) =>
				PartiePrete(joueurs, sensDeLaPartie, pioche.safeTail, pioche.headOption.toList)
		}

		case UnJoueurAJoueUneCarte(joueur, carteJouee) => ???

		/*
		joueurs.updateIf(_.id === joueur) { j => j.copy(main = ) }
				PartiePrete(joueurs, sensDeLaPartie, pioche, talon :+ carteJouee)
		*/
		//partie.copy(pioche = partie.pioche.tail, talon = partie.pioche.head :: Nil)
	}
}