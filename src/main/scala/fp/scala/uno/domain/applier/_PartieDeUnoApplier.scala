package fp.scala.uno.domain.applier

import fp.scala.uno.domain.UnoErreur
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.events.UnoEvent.*
import fp.scala.uno.domain.models.PartieDeUno.*
import fp.scala.uno.domain.models.{CarteDeUno, PartieDeUno, SensDeLaPartie}
import fp.scala.utils.base.dsl.*
import fp.scala.utils.models.nel.nel
import fp.scala.utils.models.safeuuid.Typeclass.*
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.utils.models.safeuuid.Typeclass.given_Eq_SafeUUID

trait _PartieDeUnoApplier {
	def apply(event: UnoEvent, state: PartieDeUno): PartieDeUno = ???

	/*private def partieEnCours(currentState: PartieDeUno)
	                         (ifPartieEnCoursAction: PartieEnCours => PartieEnCours):PartieEnCours = ???*/

	/*currentState match {
		case PartieAPreparer => ???
		case _: PartiePrete => ???
		case p: PartieEnCours => ifPartieEnCoursAction(p)
	}*/
}