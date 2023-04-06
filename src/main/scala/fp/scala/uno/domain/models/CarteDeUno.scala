package fp.scala.uno.domain.models

import fp.scala.utils.typeclass.eq.Eq
import fp.scala.utils.typeclass.ord.Ord
import fp.scala.utils.typeclass.show.Show

enum CarteDeUno:
	case CarteNumerique(valeur: ValeurNumeriqueDeCarte, couleur: CouleurDeCarte) extends CarteDeUno
	case Joker(jokerType: JokerType, couleur: CouleurDeCarte) extends CarteDeUno
	case Plus4Cartes extends CarteDeUno
	case ChangementDeCouleur extends CarteDeUno

enum ValeurNumeriqueDeCarte derives Eq, Show, Ord:
	case ZERO extends ValeurNumeriqueDeCarte
	case UN extends ValeurNumeriqueDeCarte
	case DEUX extends ValeurNumeriqueDeCarte
	case TROIS extends ValeurNumeriqueDeCarte
	case QUATRE extends ValeurNumeriqueDeCarte
	case CINQ extends ValeurNumeriqueDeCarte
	case SIX extends ValeurNumeriqueDeCarte
	case SEPT extends ValeurNumeriqueDeCarte
	case HUIT extends ValeurNumeriqueDeCarte
	case NEUF extends ValeurNumeriqueDeCarte

enum CouleurDeCarte derives Eq, Show:
	case Jaune extends CouleurDeCarte
	case Bleu extends CouleurDeCarte
	case Rouge extends CouleurDeCarte
	case Vert extends CouleurDeCarte

enum JokerType derives Eq, Show:
	case Plus2Cartes extends JokerType
	case Passer extends JokerType
	case ChangementDeSens extends JokerType
