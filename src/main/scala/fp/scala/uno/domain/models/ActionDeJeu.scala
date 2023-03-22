package fp.scala.uno.domain.models

import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.typeclass.show.Show
import fp.scala.utils.models.safeuuid.Typeclass.given_Show_SafeUUID


case class ActionDeJeu(joueur: SafeUUID, action: Action) derives Show

enum Action derives Show:
	case JouerUneCarte(carteJouee: CarteDeUno)