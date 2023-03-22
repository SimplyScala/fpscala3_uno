package fp.scala.domain

import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.uno.domain.models.joueurs.Joueur
import fp.scala.utils.models.safeuuid.SafeUUID

object UnoGenerator:
	def generateJoueurs(nbJoueurs: Int): Seq[Joueur] =
		(1 to nbJoueurs).toSeq.map { i => Joueur(SafeUUID.generate, s"Joueur $i", i) }