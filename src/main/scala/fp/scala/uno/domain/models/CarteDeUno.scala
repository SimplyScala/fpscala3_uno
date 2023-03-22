package fp.scala.uno.domain.models

import fp.scala.utils.typeclass.eq.Eq
import fp.scala.utils.typeclass.ord.Ord
import fp.scala.utils.typeclass.show.Show

enum CarteDeUno:
	case CarteNumeric(valeur: ValeurNumeriqueDeCarte, couleur: CouleurDeCarte)
	case Joker(jokerType: JokerType, couleur: CouleurDeCarte)
	case Plus4Cartes
	case ChangementDeCouleur

enum ValeurNumeriqueDeCarte derives Eq, Show, Ord:
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
