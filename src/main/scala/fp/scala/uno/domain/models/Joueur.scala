package fp.scala.uno.domain.models

import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.base.dsl._

case class Joueur(uid: SafeUUID, nom: String)

package object joueurs {
	/** Un Set d'au moins 3 joueurs */
	type Joueurs = JoueursImpl
	
	private case class JoueursImpl(val joueurs: Set[Joueur])
	
	object Joueurs {
		def apply(rawJoueurs: Set[Joueur]): Either[String, Joueurs] =
			if(rawJoueurs.size >= 3) JoueursImpl(rawJoueurs).right 
			else "Il faut 3 joueurs minimum".left 
	}
	
	extension (js: Joueurs)
		def toSet: Set[Joueur] = js.joueurs
}
