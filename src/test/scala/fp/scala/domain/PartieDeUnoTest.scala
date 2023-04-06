package fp.scala.domain

import fp.scala.domain.UnoGenerator.*
import fp.scala.uno.domain.models.ListeDesCartes.*
import fp.scala.uno.domain.models.{ActionDeJeu, PartieDeUno, SensDeLaPartie}
import fp.scala.uno.domain.models.joueurs.{Joueur, Joueurs}
import fp.scala.utils.base.dsl.*
import fp.scala.uno.domain.UnoErreur.*
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.uno.domain.models.Action
import fp.scala.uno.domain.models.CarteDeUno
import Joueurs.*
import fp.scala.uno.domain.UnoErreur
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.utils.EventsourcingTestingFramework

class PartieDeUnoTest extends EventsourcingTestingFramework[UnoEvent, UnoCommand, PartieDeUno, UnoErreur] {

	describe("Préparer une partie") {
		it("Il n'y a pas assez de joueur pour préparer une partie") {
			Given(Nil) |> When(PreparerUnePartie_KO) |> Then(UnoErreur.IlFaut3JoueursMinimum)
		}

		it("Préparer une partie") {
			Given(Nil) |> When(PreparerUnePartie) |> Then(LaPartieEstPreteAJouer)
		}
	}

	describe("Démarrer une partie") {
		it("Démarrer la partie OK") {
			Given(LaPartieEstPreteAJouer) |> When(DemarrerLaPartie) |> Then(LaPartieADemare)
		}
	}

	describe("Jouer une partie - coups normaux") {
		it("Un Joueur joue une carte numérique") {
			Given(LaPartieEstPreteAJouer, LaPartieADemare) |> When(JouerUneCarte) |> Then(UneCarteAEteJouee)
		}

		it("Un Joueur joue une autre carte numérique") {
			Given(LaPartieEstPreteAJouer, LaPartieADemare, UneCarteAEteJouee) |>
			When(JouerUneAutreCarte) |> 
			Then()
		}

		it("Un Joueur pioche une carte") {
			Given(LaPartieEstPreteAJouer, LaPartieADemare) |> When(PiocherUneCarte) |> Then(UneCarteAEtePiochee)
		}
	}

	describe("Jouer une partie - jouer de manière erronée") {
		it("Un Joueur joue la mauvaise carte") {
			Given(LaPartieEstPreteAJouer, LaPartieADemare) |> When(JouerUneMauvaiseCarte) |> Then(UneMauvaiseCarteAEteJouee)
		}

		/*it("Un Joueur ne joue pas à son tour") {

		}*/
	}
}