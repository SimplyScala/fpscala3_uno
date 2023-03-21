package fp.scala.uno.domain.models

import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.typeclass.eq.Eq
import fp.scala.utils.typeclass.show.Show

import scala.deriving.*

/**
 * Une partie de Uno:
 *     - se joue avec au minimum des Joueurs
 *
 */
case class PartieDeUno(uid: SafeUUID,
                       joueurs: Joueurs,
                       sensDeLaPartie: SensDeLaPartie,
                       pioche: Seq[CarteDeUno],
                       talon: Seq[CarteDeUno])

enum SensDeLaPartie:
	case SensHoraire
	case SensAntiHoraire

enum CarteDeUno:
	case CarteNumeric(valeur: ValeurNumeriqueDeCarte, couleur: CouleurDeCarte)
	case Joker(jokerType: JokerType, couleur: CouleurDeCarte)
	case Plus4Cartes
	case ChangementDeCouleur

enum ValeurNumeriqueDeCarte derives Eq, Show/*, Ordering*/:
	case ZERO
	case UN
	case DEUX
	case TROIS
	case QUATRE
	case CINQ
	case SIX
	case SEPT
	case HUIT
	case NEUF

enum CouleurDeCarte derives Eq, Show:
	case Jaune
	case Bleu
	case Rouge
	case Vert

enum JokerType derives Eq, Show:
	case Plus2Cartes
	case Passer
	case ChangementDeSens

trait Ordering[T]:
	def compare(x: T, y: T): Order
	extension (x: T)
		def > (y: T): Boolean = compare(x, y) == Order.GT
		def < (y: T): Boolean = compare(x, y) == Order.LT

enum Order:
	case EQ
	case LT
	case GT