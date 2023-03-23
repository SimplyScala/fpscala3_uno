package fp.scala.uno.domain

import fp.scala.uno.domain.models.{ActionDeJeu, CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.uno.domain.PartieDeUnoErreur.*
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.base.dsl.*
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.utils.models.safeuuid.Typeclass.given_Eq_SafeUUID

/** https://www.regledujeu.fr/uno/ */
object JeuDeUno extends PreparerUnePartie {
	/**
	 * On vérifie qu'il y a le bon nombre de joueurs
	 * On distribue n cartes à chaque joueur
	 * */
	def PréparerUnePartie(joueurs: Seq[Joueur],
	                      pioche: Seq[CarteDeUno],
	                      nbDeCarteADistribuer: Int): Either[PartieDeUnoErreur, PartieDeUno] = {
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
	}

	/**
	 * Quand le 1er joueur est prêt il lance la partie
	 * On retourne la première carte de la pioche et on la met dans le talon
	 */
	def CommencerLaPartie(partie: PartieDeUno): PartieDeUno =
		partie.copy(pioche = partie.pioche.tail, talon = partie.pioche.head :: Nil)

	/**
	 * Un joueur
	 *  - joue une carte
	 *  - pioche une carte
	 *
	 * On vérifie
	 *  - qu'il joue une carte correcte
	 *  - qu'il joue à son tour
	 *
	 */
	def UnJoueurJoue(action: ActionDeJeu, partie: PartieDeUno): PartieDeUno = ???

	/**
	 * Un joueur dit Uno
	 *  - Il faut qu'il ne lui reste qu'une seule carte
	 *  - il ne faut pas qu'un autre joueur ai dit `ContreUno avant
	 */
	def Uno(joueur: SafeUUID): PartieDeUno = ???

	/**
	 * Un joueur dit contre Uno
	 *  - Il faut qu'un joueur n'est plus qu'une carte
	 *  - Il faut que ce joueur n'est pas encore dit `Uno
	 */
	def ContreUno(joueur: SafeUUID): PartieDeUno = ???


	/** TODO
	 * Le joueur a-t-il joué dans les temps ?
	 * On rajoute des évènements (pour informer les joueurs, tel joueur prend 2 cartes, la couleur a changé, ...)
	 */
}