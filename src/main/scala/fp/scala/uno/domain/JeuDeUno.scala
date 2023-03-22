package fp.scala.uno.domain

import fp.scala.uno.domain.models.{ActionDeJeu, CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.uno.domain.PartieDeUnoErreur.*
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.base.dsl.*
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.utils.models.safeuuid.Typeclass.given_Eq_SafeUUID

object JeuDeUno {
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
							.filter { i => i % nbJoueurs == 0 })
							.take(nbDeCarteADistribuer)

		val (joueursAvecMain, piocheRestante) = joueurs.foldLeft((joueurs, pioche)) { case ((js, p), joueur) =>
			val (carteDistribuées, restePioche) = p.zipWithIndex
					.partition { case (carte, i) => cardsToPick.exists { _ == i + 1 } }
			val joueurAvecMain = joueur.copy(main = carteDistribuées.map { _._1 })

			val updateJoueur = js.map { j => if(j.uid === joueurAvecMain.uid) joueurAvecMain else j }

			(updateJoueur, restePioche.map { case (c, _) => c })
		}

		Joueurs(joueursAvecMain.toSet)
			.map { js => PartieDeUno(js, SensDeLaPartie.SensHoraire, piocheRestante, Nil) }
			.mapLeft { _ => IlFaut3JoueursMinimum }
	}

	/**
	 * Quand le 1er joueur est prêt il lance la partie
	 * On retourne la première carte de la pioche et on la met dans le talon
	 */
	def CommencerLaPartie(partie: PartieDeUno): PartieDeUno = ???

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

	def UnJouerDitUno(joueur: SafeUUID): PartieDeUno = ???

	def UnJouerDitTaPasDisUno(joueurQuiParle: SafeUUID, joueurQuiNaPasDitUno: SafeUUID): PartieDeUno = ???


	/** TODO
	 * Le joueur a-t-il joué dans les temps ?
	 * On rajoute des évènements (pour informer les joueurs, tel joueur prend 2 cartes, la couleur a changé, ...)
	 */
}

enum PartieDeUnoErreur:
	case IlFaut3JoueursMinimum