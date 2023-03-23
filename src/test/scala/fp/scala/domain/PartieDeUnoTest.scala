package fp.scala.domain

import fp.scala.uno.domain.JeuDeUno.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import fp.scala.domain.UnoGenerator.*
import fp.scala.uno.domain.models.ListeDesCartes.*
import fp.scala.uno.domain.models.PartieDeUno
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.uno.domain.models.SensDeLaPartie
import fp.scala.utils.base.dsl.*
import fp.scala.uno.domain.PartieDeUnoErreur.*
import fp.scala.utils.models.safeuuid.SafeUUID

class PartieDeUnoTest extends AnyFunSpec with should.Matchers {
	describe("PréparerUnePartie") {
		it("Pas le bon nombre de joueurs") {
			val result = PréparerUnePartie(generateJoueurs(2), Nil, 0)

			result shouldBe IlFaut3JoueursMinimum.left
		}

		it("Dumb partie") {
			val joueur1 = Joueur(SafeUUID.generate, s"Joueur 1", 1)
			val joueur2 = Joueur(SafeUUID.generate, s"Joueur 2", 2)
			val joueur3 = Joueur(SafeUUID.generate, s"Joueur 3", 3)

			val joueurs = joueur1 :: joueur2 :: joueur3 :: Nil
			val pioche = CarteJaune._1 :: CarteJaune._2 :: CarteJaune._3 :: CarteJaune._4 :: Nil

			val result = PréparerUnePartie(joueurs, pioche, 1)

			val expectedJoueurs =
				joueur1.copy(main = CarteJaune._1 :: Nil) ::
				joueur2.copy(main = CarteJaune._2 :: Nil) ::
				joueur3.copy(main = CarteJaune._3 :: Nil) ::
				Nil

			val expected = PartieDeUno(
				Joueurs(expectedJoueurs.toSet).getOrElse(null),
				SensDeLaPartie.SensHoraire,
				CarteJaune._4 :: Nil,
				Nil
			)

			result shouldBe expected.right
		}

		it("Tester la distribution des cartes, l'une après l'autre, pour les joueurs") {
			val joueur1 = Joueur(SafeUUID.generate, s"Joueur 1", 1)
			val joueur2 = Joueur(SafeUUID.generate, s"Joueur 2", 2)
			val joueur3 = Joueur(SafeUUID.generate, s"Joueur 3", 3)

			val joueurs = joueur1 :: joueur2 :: joueur3 :: Nil

			val pioche = CarteJaune._1 :: CarteJaune._2 :: CarteJaune._3 ::
						CarteBleu._1 :: CarteBleu._2 :: CarteBleu._3 ::
						CarteRouge._1 :: CarteRouge._2 :: CarteRouge._3 ::
						CarteVerte._1 :: CarteVerte._2 :: CarteVerte._3 ::Nil

			val result = PréparerUnePartie(joueurs, pioche, 3)

			val expectedJoueurs =
				joueur1.copy(main = CarteJaune._1 :: CarteBleu._1 :: CarteRouge._1 :: Nil) ::
				joueur2.copy(main = CarteJaune._2 :: CarteBleu._2 :: CarteRouge._2 :: Nil) ::
				joueur3.copy(main = CarteJaune._3 :: CarteBleu._3 :: CarteRouge._3 :: Nil) ::
				Nil

			val expected = PartieDeUno(
				Joueurs(expectedJoueurs.toSet).getOrElse(null),
				SensDeLaPartie.SensHoraire,
				CarteVerte._1 :: CarteVerte._2 :: CarteVerte._3 ::Nil,
				Nil
			)

			result shouldBe expected.right
		}
	}

	describe("LancerUnePartie") {
		it("LancerUnePartie simple") {
			val partie = prepareUnePartie

			
		}
	}
}