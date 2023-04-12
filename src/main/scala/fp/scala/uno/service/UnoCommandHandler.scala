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
import AggregateUid.given_Eq_AggregateUid
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.uno.domain.decide
import zio.{IO, ZIO, ZLayer}
import CommandHandlerError.*
import fp.scala.uno.service.EventCache.CacheUsing
import fp.scala.uno.service.EventCache.StaticCache
import fp.scala.uno.service.EventCache.EventsMemoryCache.*
import fp.scala.uno.service.EventCache.*
import CacheUsing.*
import fp.scala.uno.application.api.UnoAPI.updateCacheEvents
import zio.prelude.AnySyntax

import java.time.OffsetDateTime


trait UnoCommandHandler:
	def processCommand(processUid: ProcessUid,
	                   aggregateUid: AggregateUid,
	                   commande: UnoCommand): IO[CommandHandlerError, Seq[RepositoryEvent[UnoEvent]]]

object UnoCommandHandler extends ApplyTo:
	type UnoCommandHandlerDeps = EventRepository[UnoEvent] & EventCache.EventsMemoryCache

	val live: ZLayer[UnoCommandHandlerDeps, Nothing, UnoCommandHandler] =
		(for
			evtRepo: EventRepository[UnoEvent] <- ZIO.service[EventRepository[UnoEvent]]
			cache: EventCache.EventsMemoryCache <- ZIO.service[EventCache.EventsMemoryCache]
		yield {
			new UnoCommandHandler:
				import UnoEventJsonCodec.UnoEventJsonCodec

				override def processCommand(processUid: ProcessUid,
				                            aggregateUid: AggregateUid,
				                            commande: UnoCommand): IO[CommandHandlerError, Seq[RepositoryEvent[UnoEvent]]] = {
					val evtStreamId = EventStreamId(aggregateUid, AggregateName("uno"))
					for
						cacheUsing <- cache.get_(aggregateUid)
						previousEvts <-	getPreviousEvents(cacheUsing, evtStreamId)

						currentState = applyTo(PartieDeUno.PartieAPreparer)(previousEvts.map { _.event })

						newEvents <- ZIO.fromEither(decide(commande, currentState)).mapError { DomainError(_) }

						repoEvts = newEvents.map { repoEvtBuilder(processUid, aggregateUid, AggregateName("uno")) }
						_ <- evtRepo.saveEvents(evtStreamId, repoEvts).mapError { FromDbError(_) }
						_ <- cache.update(updateCacheEvents(aggregateUid, previousEvts ++ repoEvts))
					yield repoEvts
				}

				private def getPreviousEvents(cacheUsing: CacheUsing,
				                              evtStreamId: EventStreamId) = cacheUsing match {
					case NoCache => ZIO.logInfo("[TEST] CACHE NOT USED") *>
						evtRepo.getEventStream(evtStreamId).mapError { FromDbError(_) }
					case CacheEvts(repoEvents) => ZIO.logInfo("[TEST] CACHE USED") *> ZIO.succeed(repoEvents)
				}

				private def updateCacheEvents(aggregateUid: AggregateUid, events: Seq[RepositoryEvent[UnoEvent]])
				                             (cache: StaticCache): StaticCache =
					cache.properUpsert(aggregateUid, events)

		}) |> ZLayer.fromZIO

	private def repoEvtBuilder(processUid: ProcessUid,
	                           aggregateUid: AggregateUid,
	                           aggName: AggregateName)(evt: UnoEvent) =
		RepositoryEvent(processUid, aggregateUid, aggName, OffsetDateTime.now(), evt)