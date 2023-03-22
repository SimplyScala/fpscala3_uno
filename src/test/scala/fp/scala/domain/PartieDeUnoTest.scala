package fp.scala.domain

import fp.scala.uno.domain.JeuDeUno.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should
import fp.scala.domain.UnoGenerator.*
import fp.scala.uno.domain.models.ListeDesCartes.*
import fp.scala.uno.domain.models.PartieDeUno
import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.uno.domain.models.SensDeLaPartie
import fp.scala.utils.base.dsl.*
import fp.scala.uno.domain.PartieDeUnoErreur.*

class PartieDeUnoTest extends AnyFunSpec with should.Matchers {
	describe("PréparerUnePartie") {
		it("Pas le bon nombre de joueurs") {
			val result = PréparerUnePartie(generateJoueurs(2), Nil, 0)

			result shouldBe IlFaut3JoueursMinimum.left
		}

		it("Dumb partie") {
			val joueurs = generateJoueurs(3)
			val pioche = CarteJaune._1 :: CarteJaune._2 :: CarteJaune._3 :: CarteJaune._4 :: Nil
			val result = PréparerUnePartie(joueurs, pioche, 1)

			val expected = PartieDeUno(
				Joueurs(joueurs.toSet).toOption.getOrElse(null)
					.foreach { j =>
						j.copy(main =
							(if(j.placement == 1) CarteJaune._1
							else if (j.placement == 2) CarteJaune._2
							else CarteJaune._3) :: Nil
						)
					},
				SensDeLaPartie.SensHoraire,
				CarteJaune._4 :: Nil,
				Nil
			)

			result shouldBe expected.right
		}
	}
}