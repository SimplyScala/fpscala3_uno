package fp.scala.uno.domain.models

import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.utils.models.nel.NEL
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.typeclass.eq.Eq
import fp.scala.utils.typeclass.ord.Ord
import fp.scala.utils.typeclass.show.Show

import scala.deriving.*

enum PartieDeUno:
	case PartieAPreparer extends PartieDeUno
	
	/**
	 * Une partie de Uno:
	 */
	case PartiePrete(//uid: SafeUUID,
	                 joueurs: Joueurs,
	                 sensDeLaPartie: SensDeLaPartie,
	                 pioche: NEL[CarteDeUno]) extends PartieDeUno
	
	case PartieEnCours(joueurs: Joueurs,
	                   sensDeLaPartie: SensDeLaPartie,
	                   pioche: Seq[CarteDeUno], 
	                   talon: NEL[CarteDeUno]) extends PartieDeUno

enum SensDeLaPartie:
	case SensHoraire
	case SensAntiHoraire