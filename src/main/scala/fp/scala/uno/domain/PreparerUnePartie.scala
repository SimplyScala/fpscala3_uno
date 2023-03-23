package fp.scala.uno.domain

import fp.scala.uno.domain.PartieDeUnoErreur.IlFaut3JoueursMinimum
import fp.scala.uno.domain.models.{CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.utils.base.dsl.*

trait PreparerUnePartie {
	protected case class MainDesJoueurs(counter: Int = 0,
	                                    cartesARetirer: Seq[Int] = Nil,
	                                    joueursAvecMains: Seq[Joueur] = Nil)
}