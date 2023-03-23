package fp.scala.domain

import fp.scala.uno.domain.JeuDeUno.PréparerUnePartie
import fp.scala.uno.domain.models.ListeDesCartes.CarteJaune
import fp.scala.uno.domain.models.{CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.utils.models.safeuuid.SafeUUID

object UnoGenerator:
	def generateJoueurs(nbJoueurs: Int): Seq[Joueur] =
		(1 to nbJoueurs).toSeq.map { i => Joueur(SafeUUID.generate, s"Joueur $i", i) }
		
	def prepareUnePartie: PartieDeUno =
		val joueur1 = Joueur(SafeUUID.generate, s"Joueur 1", 1, CarteJaune._1 :: Nil)
		val joueur2 = Joueur(SafeUUID.generate, s"Joueur 2", 2, CarteJaune._2 :: Nil)
		val joueur3 = Joueur(SafeUUID.generate, s"Joueur 3", 3, CarteJaune._3 :: Nil)

		val joueurs = joueur1 :: joueur2 :: joueur3 :: Nil
		val pioche = CarteJaune._4 :: Nil

		val result = PréparerUnePartie(joueurs, pioche, 1)

		PartieDeUno(
			Joueurs(joueurs.toSet).getOrElse(null),
			SensDeLaPartie.SensHoraire,
			pioche,
			Nil
		)		