package fp.scala.uno.domain.models

import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.typeclass.show.Show

import scala.deriving.*

/**
 * Une partie de Uno:
 *     - se joue avec au minimum des Joueurs
 *
 */
case class PartieDeUno(uid: SafeUUID,
                       joueurs: Joueurs,
                       placementDesJoueurs: Set[Int], // TODO mettre un type
                       sensDeLaPartie: SensDeLaPartie)

enum SensDeLaPartie:
	case SensHoraire
	case SensAntiHoraire

enum CarteDeUno:
	case CarteNumeric(valeur: ValeurNumeriqueDeCarte, couleur: CouleurDeCarte)

enum ValeurNumeriqueDeCarte /*derives Eq, Ordering, Show*/:
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

enum CouleurDeCarte /*derives Eq, Show*/:
	case Jaune
	case Bleu
	case Rouge
	case Vert

trait Eq[T]:
	def equal(x: T, y: T): Boolean
	extension (x: T)
		def isEqualTo(y: T): Boolean = equal(x, y)



trait Ordering[T]:
	def compare(x: T, y: T): Order
	extension (x: T)
		def > (y: T): Boolean = compare(x, y) == Order.GT
		def < (y: T): Boolean = compare(x, y) == Order.LT

enum Order:
	case EQ
	case LT
	case GT

