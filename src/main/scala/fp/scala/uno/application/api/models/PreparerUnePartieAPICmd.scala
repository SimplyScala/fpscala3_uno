package fp.scala.uno.application.api.models

import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.uno.domain.models.joueurs.Joueur
import fp.scala.utils.models.safeuuid.SafeUUID
import zio.json.{DeriveJsonCodec, JsonCodec}
import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec
import fp.scala.utils.models.nel.NEL

case class PreparerUnePartieAPICmd(processUid: SafeUUID, joueurs: Seq[SafeUUID])

enum JouerUnePartieAPICmd:
	case DemarrerLaPartie(processUid: SafeUUID) extends JouerUnePartieAPICmd
	case JouerUneCarte(processUid: SafeUUID, joueur: SafeUUID, carteJouee: CarteDeUno) extends JouerUnePartieAPICmd
	case PiocherUneCarte(processUid: SafeUUID, joueur: SafeUUID) extends JouerUnePartieAPICmd
	
object JouerUnePartieAPICmd:
	extension (c: JouerUnePartieAPICmd)
		def toDomain: (SafeUUID, UnoCommand) = c match {
			case DemarrerLaPartie(p) => (p, UnoCommand.DemarrerLaPartie())
			case JouerUneCarte(p, j, c) => (p, UnoCommand.JouerUneCarte(j, c))
			case PiocherUneCarte(p, j) => (p, UnoCommand.PiocherUneCarte(j))
		} 

object PreparerUnePartieJsonBindings:
	import SafeUUIDJsonCodec.SafeUUIDJsonCodec
	import fp.scala.uno.service.UnoEventJsonCodec.CarteDeUnoJsonCodec

	implicit val PreparerUnePartieJsonCodec: JsonCodec[PreparerUnePartieAPICmd] = DeriveJsonCodec.gen[PreparerUnePartieAPICmd]
	implicit val JouerUnePartieAPICommandJsonCodec: JsonCodec[JouerUnePartieAPICmd] =
		DeriveJsonCodec.gen[JouerUnePartieAPICmd]
