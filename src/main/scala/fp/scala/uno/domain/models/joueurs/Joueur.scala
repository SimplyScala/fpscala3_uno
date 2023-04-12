package fp.scala.uno.domain.models.joueurs

import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.utils.base.dsl.*
import fp.scala.utils.models.safeuuid.SafeUUID

case class Joueur(uid: SafeUUID,
                  nom: String,
                  placement: Int,
                  main: Seq[CarteDeUno] = Nil)

/**
 *  Invariants
 *      - Un Set (de N joueurs) d'au moins 3 joueurs
 *      - les placements vont de 1 à N
 * */

/*
type Joueurs = Joueurs.JoueursImpl

object Joueurs:
	private[joueurs] case class JoueursImpl(private[Joueurs] joueurs: Set[Joueur])

	def apply(rawJoueurs: Set[Joueur]): Either[String, Joueurs] =
		if(rawJoueurs.size >= 3) JoueursImpl(rawJoueurs).right
		else "Il faut 3 joueurs minimum".left
		
	extension (js: Joueurs)
		def toSet: Set[Joueur] = js.joueurs
		def foreach(f: Joueur => Joueur): Joueurs = JoueursImpl(js.joueurs.map { f })
		def updateIf(predicate: Joueur => Boolean)(f: Joueur => Joueur): Joueurs =
			val updatedJoueurs = js.joueurs.map { j => if(predicate(j)) f(j) else j }
			JoueursImpl(updatedJoueurs)
		def size: Int = js.joueurs.size
		def find(predicate: Joueur => Boolean): Option[Joueur] = js.joueurs.find(predicate)*/
