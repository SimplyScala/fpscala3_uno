package fp.scala.uno.service

import fp.scala.uno.domain.UnoErreur
import fp.scala.uno.domain.commands.UnoCommand
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.domain.models.PartieDeUno
import fp.scala.uno.repository.EventRepository
import fp.scala.uno.repository.models.events.{AggregateName, AggregateUid, EventStreamId, ProcessUid, RepositoryEvent}
import fp.scala.utils.models.safeuuid.SafeUUID
import fp.scala.utils.typeclass.domain.ApplyTo
import fp.scala.utils.typeclass.domain.DomainEventTypeclass.UnoEventTypeclass
import fp.scala.uno.domain.decide
import zio.{IO, ZIO, ZLayer}
import CommandHandlerError.*
import zio.prelude.AnySyntax

import java.time.OffsetDateTime


trait UnoCommandHandler:
	def processCommand(processUid: ProcessUid,
	                   aggregateUid: AggregateUid,
	                   commande: UnoCommand): IO[CommandHandlerError, Seq[RepositoryEvent[UnoEvent]]]

object UnoCommandHandler extends ApplyTo:
	val live: ZLayer[EventRepository[UnoEvent], Nothing, UnoCommandHandler] =
		ZIO.service[EventRepository[UnoEvent]].map { (evtRepo: EventRepository[UnoEvent]) =>
			new UnoCommandHandler:
				import UnoEventJsonCodec.UnoEventJsonCodec

				override def processCommand(processUid: ProcessUid,
				                            aggregateUid: AggregateUid,
				                            commande: UnoCommand): IO[CommandHandlerError, Seq[RepositoryEvent[UnoEvent]]] = {
					val evtStreamId = EventStreamId(aggregateUid, AggregateName("uno"))
					for
						previousEvts <- evtRepo.getEventStream(evtStreamId).mapError { FromDbError(_) }
						currentState = applyTo(PartieDeUno.PartieAPreparer)(previousEvts.map { _.event })
						newEvents <- ZIO.fromEither(decide(commande, currentState)).mapError { DomainError(_) }
						repoEvts = newEvents.map { repoEvtBuilder(processUid, aggregateUid, AggregateName("uno")) }
						_ <- evtRepo.saveEvents(evtStreamId, repoEvts).mapError { FromDbError(_) }

					yield repoEvts
				}
		} |> ZLayer.fromZIO

	private def repoEvtBuilder(processUid: ProcessUid,
	                           aggregateUid: AggregateUid,
	                           aggName: AggregateName)(evt: UnoEvent) =
		RepositoryEvent(processUid, aggregateUid, aggName, OffsetDateTime.now(), evt)