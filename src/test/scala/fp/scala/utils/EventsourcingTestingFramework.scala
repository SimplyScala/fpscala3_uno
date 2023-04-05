package fp.scala.utils

import fp.scala.utils.typeclass.domain.{ApplyTo, DomainEventTypeclass}
import fp.scala.utils.typeclass.domain.DomainEventTypeclass.*
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

trait EventsourcingTestingFramework[Event, Command, State, Error] extends AnyFunSpec
	with Matchers
	with ApplyTo
	with EventsourcingTestingFrameworkHelper {
	def Given(events: List[Event]): List[Event] = events
	def Given(events: Event*): List[Event] = events.toList

	def When(command: Command)(events: List[Event]) = (command, events)

	def Then(expected: List[Event])(when: (Command, List[Event]))(implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) =
		specify(when._1,when._2, expected)

	def Then(expected: Event*)(when: (Command, List[Event]))(implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) =
		specify(when._1,when._2, expected.toList)

	def Then(expected: Error)(when: (Command, List[Event]))(implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) =
		specifyError(when._1,when._2, expected)
	
	private def specify(command: Command, givenEvents: List[Event], expectedEvents: List[Event])
	                   (implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) = {
		de.decide(applyTo(de.emptyState)(givenEvents), command).fold(
			error   => fail(s"expected Event but have error: ${error.toString}"),
			events  => events shouldBe /*matchTo*/ expectedEvents
		)
	}

	private def specifyError(command: Command, givenEvents: Seq[Event], expectedError: Error)
	                        (implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) = {
		de.decide(applyTo(de.emptyState)(givenEvents), command).fold(
			error   => error shouldBe expectedError,
			events  => fail(s"should be an error, not Events : $events")
		)
	}
}

trait EventsourcingTestingFrameworkHelper {
	import scala.language.implicitConversions
	import zio.prelude.AnySyntax
	
	extension[A] (x: A)
		def |>[B](f: A => B): B = f(x) 
}

/*
import pi.app.domain.{ApplyTo, DomainEventTypeclass}
import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite
//import com.softwaremill.diffx.scalatest.DiffMatcher._

trait EventsourcingTestingFramework[Event, Command, State, Error] extends AnyFunSuite
	with Matchers
	with ApplyTo
	with EventsourcingTestingFrameworkHelper {

	def Given(events: List[Event]): List[Event] = events
	def Given(events: Event*): List[Event] = events.toList

	def When(command: Command)(events: List[Event]) = (command, events)

	def Then(expected: List[Event])(when: (Command, List[Event]))(implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) =
		specify(when._1,when._2, expected)

	def Then(expected: Event*)(when: (Command, List[Event]))(implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) =
		specify(when._1,when._2, expected.toList)

	def Then(expected: Error)(when: (Command, List[Event]))(implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) =
		specifyError(when._1,when._2, expected)

	private def specify(command: Command, givenEvents: List[Event], expectedEvents: List[Event])
	                   (implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) = {
		de.decide(applyTo(de.emptyState)(givenEvents), command).fold(
			error   => fail(s"expected Event but have error: ${error.toString}"),
			events  => events shouldBe /*matchTo*/ expectedEvents
		)
	}

	private def specifyError(command: Command, givenEvents: Seq[Event], expectedError: Error)
	                        (implicit de: DomainEventTypeclass.Aux[Event, State, Command, Error]) = {
		de.decide(applyTo(de.emptyState)(givenEvents), command).fold(
			error   => error shouldBe expectedError,
			events  => fail(s"should be an error, not Events : $events")
		)
	}
}

trait EventsourcingTestingFrameworkHelper {
	import scala.language.implicitConversions
	import zio.prelude.AnySyntax

	implicit def idops[A](a: A) = AnySyntax(a)
}
*/