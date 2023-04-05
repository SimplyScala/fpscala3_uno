package fp.scala.utils.typeclass.domain

trait ApplyTo {
	protected def applyTo[E, S, C, Err](startingState: S)(events: Seq[E])(implicit de: DomainEventTypeclass.Aux[E, S, C, Err]): S = {
		events.foldLeft(startingState) { (currentState, event) => de.apply(currentState, event) }
	}
}