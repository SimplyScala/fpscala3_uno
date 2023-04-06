package fp.scala.uno.domain.commands

import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.uno.domain.models.joueurs.Joueur
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.models.nel.NEL

enum UnoCommand:
	case PreparerUnePartie(joueurs: Seq[Joueur], pioche: NEL[CarteDeUno]) extends UnoCommand
	case DemarrerLaPartie(nbDeCarteADistribuer: Int = 7) extends UnoCommand // TODO entre 1 et 7
	case JouerUneCarte(joueur: SafeUUID, carteJouee: CarteDeUno) extends UnoCommand
	case PiocherUneCarte(joueur: SafeUUID) extends UnoCommand
	/*
	
	case PiocherUneCarte() extends UnoCommand
	case DireUno() extends UnoCommand
	case AccuserUnJoueurDeNePasAvoirDitUno() extends UnoCommand
	case NePasUtiliserDeContreMesure() extends UnoCommand        // TODO via un schedule ???*/