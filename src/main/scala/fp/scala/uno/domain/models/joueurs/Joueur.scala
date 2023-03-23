package fp.scala.uno.domain.models.joueurs

import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.utils.base.dsl.*
import fp.scala.utils.models.safeuuid.SafeUUID

case class Joueur(uid: SafeUUID,
                  nom: String,
                  // TODO représenter le placement pour qu'il ne puisse pas y avoir d'incohérence, peut être dans Joueurs
                  placement: Int,
                  main: Seq[CarteDeUno] = Nil)

/**
 *  Invariants
 *      - Un Set (de N joueurs) d'au moins 3 joueurs
 *      - les placements vont de 1 à N
 * */
type Joueurs = Joueurs.JoueursImpl

object Joueurs:
	private[joueurs] case class JoueursImpl(joueurs: Set[Joueur])

	def apply(rawJoueurs: Set[Joueur]): Either[String, Joueurs] =
		if(rawJoueurs.size >= 3) JoueursImpl(rawJoueurs).right
		else "Il faut 3 joueurs minimum".left
		
	extension (js: Joueurs)
		def toSet: Set[Joueur] = js.joueurs
		def foreach[B](f: Joueur => Joueur): Joueurs = JoueursImpl(js.joueurs.map { f })