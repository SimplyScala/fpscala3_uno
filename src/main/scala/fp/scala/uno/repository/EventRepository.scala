package fp.scala.uno.repository

import fp.scala.app.infrastructure.JdbcConnection.ZTransactor
import fp.scala.app.infrastructure.models.DbError
import fp.scala.uno.repository.models.events.{AggregateName, AggregateUid, EventStreamId, ProcessUid, RepositoryEvent}
import fp.scala.utils.models.safeuuid.*
import AggregateUid.*
import doobie.implicits.*
import doobie.util.update.*
import zio.*
import zio.interop.catz.*
import zio.json.*
import zio.json.JsonCodec
import zio.{IO, ZIO, ZLayer}
import zio.prelude.AnySyntax
import zio.test.FailureRenderer.FailureMessage.Fragment

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

abstract class EventRepository[E: JsonCodec]:
	def getEventStream(eventStreamId: EventStreamId): IO[DbError, Seq[RepositoryEvent[E]]]

	def saveEvents(eventStreamId: EventStreamId, events: Seq[RepositoryEvent[E]]): IO[DbError, Int]

object EventRepository:
	def live[E: Tag: JsonCodec]: ZLayer[ZTransactor, Nothing, EventRepository[E]] =
		ZIO.service[ZTransactor].map { cnx =>
			new EventRepository[E]:
				override def getEventStream(eventStreamId: EventStreamId): IO[DbError, Seq[RepositoryEvent[E]]] =
					Req.list(eventStreamId)
						.query[(String, String, String, String, String)]
						.to[Seq]
						.transact(cnx)
						.tapError(e => ZIO.logErrorCause("getEventStream", Cause.fail(e)))
						.mapError(DbError.DbException(_))
						.flatMap { ms =>
							ms.map { case (pUid, aUid, aName, sentDate, json_evt) =>
								val rEvt = for
									evt <- ZIO.fromEither(json_evt.fromJson[E])
											.tapError(e => ZIO.logErrorCause("getEventStream: decode", Cause.fail(e)))
									processUid <- ZIO.fromEither(SafeUUID(pUid).map { ProcessUid(_) })
									aggregateUid <- ZIO.fromEither(SafeUUID(pUid).map { AggregateUid(_) })
								yield RepositoryEvent
									( processUid
									, aggregateUid
									, AggregateName(aName)
									, OffsetDateTime.parse(sentDate, DateTimeFormatter.ISO_DATE_TIME)
									, evt
									)

								rEvt.mapError(DbError.DecodingError(_))
							} |> ZIO.collectAll
						}

				override def saveEvents(eventStreamId: EventStreamId, events: Seq[RepositoryEvent[E]]): IO[DbError, Int] =
					Update[(String, String, String, String, String)](Req.insert)
						.updateMany(events.map { e =>
							( e.processUid.safeUUID.safeValue
							, e.aggregateUid.safeUUID.safeValue
							, e.aggregateName.value
							, e.sentDate.format(DateTimeFormatter.ISO_DATE_TIME), e.event.toJson
							)
						})
						.transact(cnx)
						.flatMapError(e => ZIO.logErrorCause(Cause.fail(e)).map(_ => DbError.DbException(e)))

		} |> ZLayer.fromZIO

private object Req:
	import doobie.Fragment
	import doobie.implicits.*
	import doobie.util.update.*
	import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec.SafeUUIDJsonCodec

	def list(eventStreamId: EventStreamId): Fragment =
		val aggUid = eventStreamId.id.safeUUID.safeValue
		val aggName = eventStreamId.aggregateName.value
		sql"""select processuid, aggregateuid, aggregatename, sentdate, valuez
		     from events
			 where aggregatename=$aggName
			 and aggregateuid=$aggUid
			 order by insertorder"""

	def insert: String =
		"""insert into events (processuid, aggregateuid, aggregatename, sentdate, valuez) values (?, ?, ?, ?, ?::jsonb)"""