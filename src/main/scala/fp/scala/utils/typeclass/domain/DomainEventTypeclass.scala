package fp.scala.utils.typeclass.domain

import fp.scala.uno.{domain => D}
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.models.PartieDeUno
import fp.scala.uno.domain.UnoErreur

trait DomainEventTypeclass[Event] {
	type State
	type Command
	type Error

	def apply(currentState: State, event: Event): State
	def decide(currentState: State, command: Command): Either[Error, Seq[Event]]
	//def processUidFromEvent(e: Event): ProcessUid
	//def processUid(c: Command): ProcessUid
	def emptyState: State
}

object DomainEventTypeclass {
	type Aux[A, S, C, E] = DomainEventTypeclass[A] {
		type State = S
		type Command = C
		type Error = E
	}

	implicit object UnoEventTypeclass extends DomainEventTypeclass[UnoEvent] {
		type State = PartieDeUno
		type Command = UnoCommand
		type Error = UnoErreur

		override def apply(currentState: State, event: UnoEvent): State = D.apply(event, currentState)
		//override def processUidFromEvent(e: ProductionEvent): ProcessUid = e.processUid
		//override def processUid(c: ProductionCommand): ProcessUid = c.processUid
		override def emptyState: State = PartieDeUno.PartieAPreparer
		override def decide(currentState: State, command: UnoEventTypeclass.Command): Either[UnoErreur, Seq[UnoEvent]] =
			D.decide(command, currentState)
	}
}