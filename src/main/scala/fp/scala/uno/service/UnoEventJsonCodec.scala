package fp.scala.uno.service

import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.models.{CarteDeUno, CouleurDeCarte, JokerType, ValeurNumeriqueDeCarte}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import zio.json.{DeriveJsonCodec, JsonCodec}
import Joueurs.*

object UnoEventJsonCodec:
	import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec.SafeUUIDJsonCodec
	import fp.scala.utils.models.bindings.json.NELJsonCodec.NELJsonCodec

			implicit val JokerTypeJsonCodec: JsonCodec[JokerType] = DeriveJsonCodec.gen[JokerType]
			implicit val CouleurDeCarteJsonCodec: JsonCodec[CouleurDeCarte] = DeriveJsonCodec.gen[CouleurDeCarte]
			implicit val ValeurNumeriqueDeCarteJsonCodec: JsonCodec[ValeurNumeriqueDeCarte] = DeriveJsonCodec.gen[ValeurNumeriqueDeCarte]
		implicit val CarteDeUnoJsonCodec: JsonCodec[CarteDeUno] = DeriveJsonCodec.gen[CarteDeUno]
		implicit val JoueurJsonCodec: JsonCodec[Joueur] = DeriveJsonCodec.gen[Joueur]
		implicit val JoueursJsonCodec: JsonCodec[Joueurs] = JsonCodec[Set[Joueur]].transformOrFail(Joueurs(_), _.toSet)
	implicit val UnoEventJsonCodec: JsonCodec[UnoEvent] = DeriveJsonCodec.gen[UnoEvent]