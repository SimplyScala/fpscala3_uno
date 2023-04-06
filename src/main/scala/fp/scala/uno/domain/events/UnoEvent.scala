package fp.scala.uno.domain.events

import fp.scala.uno.domain.models.CarteDeUno
import fp.scala.uno.domain.models.joueurs.Joueurs
import fp.scala.utils.models.nel.NEL
import fp.scala.utils.models.safeuuid.SafeUUID

enum UnoEvent:
	case LaPartieEstPreteAJouer(joueurs: Joueurs, pioche: NEL[CarteDeUno]) extends UnoEvent
	case LaPartieADemarree(nbDeCarteADistribuer: Int) extends UnoEvent
	case UnJoueurAJoueUneCarte(joueur: SafeUUID, carteJouee: CarteDeUno) extends UnoEvent
	case UnJoueurAPiocheUneCarte(joueur: SafeUUID) extends UnoEvent
	case UnJoueurAJoueUnMauvaiseCarte(joueur: SafeUUID, carteJouee: CarteDeUno) extends UnoEvent
/*
	case LeJoueurSuivantDevraPiocherDesCartes(nbCartes)     // ack ou contre mesure
	case LeJoueurSuivantDevraPasserSonTour()                // ack ou contre mesure

	case LeJoueurNaPasUtiliseDeContreMesure
	case UnJoueurAPiocheDesCartes(nbCartes)
	case UnJoueurAPasseSonTour()
	case UnJoueurDitUno()
	case UnJoueurAccuseUnAutreDeNePasAvoirDitUno(joueur, joueurQuiNaPasDit)
	
	*/
