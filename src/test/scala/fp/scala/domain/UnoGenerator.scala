package fp.scala.domain

import fp.scala.uno.domain.models.ListeDesCartes.{CarteJaune, CarteBleu, CarteRouge, CarteVerte}
import fp.scala.uno.domain.models.{CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent

object UnoGenerator:
	val joueur1_uid = SafeUUID.generate
	val joueur2_uid = SafeUUID.generate
	val joueur3_uid = SafeUUID.generate

	val joueur1 = Joueur(joueur1_uid, "Joueur 1", 1)
	val joueur2 = Joueur(joueur2_uid, "Joueur 2", 2)
	val joueur3 = Joueur(joueur3_uid, "Joueur 3", 3)

	/** Commandes */
	val PreparerUnePartie_KO = UnoCommand.PreparerUnePartie(joueur1 :: Nil, Nil)
	val PreparerUnePartie = UnoCommand.PreparerUnePartie(joueur1 :: joueur2 :: joueur3 :: Nil, pioche_1)
	val DemarrerLaPartie = UnoCommand.DemarrerLaPartie(1)
	val JouerUneCarte = UnoCommand.JouerUneCarte(joueur1_uid, CarteJaune._5)


	/** Évènements */
	val LaPartieEstPreteAJouer = UnoEvent.LaPartieEstPreteAJouer(joueurs, pioche_1)
	val LaPartieADemare = UnoEvent.LaPartieADemarree(1)
	val UneCarteAEteJouee = UnoEvent.UnJoueurAJoueUneCarte(joueur1_uid, CarteJaune._5)

	def joueurs: Joueurs = Joueurs(Set(joueur1, joueur2, joueur3)).toOption.getOrElse(null)

	def pioche_1 = 
		CarteJaune._1 :: CarteJaune._2 :: CarteJaune._3 :: CarteJaune._4 ::
		CarteJaune._5 :: CarteJaune._6 :: CarteJaune._7 :: CarteJaune._8 ::
		CarteBleu._1 :: CarteBleu._2 :: CarteBleu._3 ::
		CarteRouge._1 :: CarteRouge._2 :: CarteRouge._3 ::
		CarteVerte._1 :: CarteVerte._2 :: CarteVerte._3 ::Nil