package fp.scala.uno.repository

import fp.scala.app.infrastructure.JdbcConnection.ZTransactor
import fp.scala.app.infrastructure.models.DbError
import fp.scala.uno.repository.models.events.{AggregateUid, EventStreamId, RepositoryEvent}
import doobie.implicits.*
import doobie.util.update.*
import zio.*
import zio.interop.catz.*
import zio.json.*
import zio.json.JsonCodec
import zio.{IO, ZIO, ZLayer}
import zio.prelude.AnySyntax

abstract class EventRepository[E: JsonCodec]:
	def getEventStream(eventStreamId: EventStreamId): IO[DbError, Seq[RepositoryEvent[E]]]

	def saveEvents(eventStreamId: EventStreamId, events: Seq[RepositoryEvent[E]]): IO[DbError, Int]

object EventRepository:
	def live[E: Tag: JsonCodec]: ZLayer[ZTransactor, Nothing, EventRepository[E]] =
		ZIO.service[ZTransactor].map { cnx =>
			new EventRepository[E]:
				override def getEventStream(eventStreamId: EventStreamId): IO[DbError, Seq[RepositoryEvent[E]]] = ???
					/*Req.list(eventStreamId, "uno")
						.query[String]
						.to[Seq]
						.transact(cnx)
						.tapError(e => ZIO.logErrorCause("getEventStream", Cause.fail(e)))
						.mapError(DbError.DbException(_))
						.flatMap { ms =>
							ms.map { raw =>
								ZIO.fromEither(raw.fromJson[DomainEventRead[E]])
									.tapError(e => ZIO.logErrorCause("getEventStream: decode", Cause.fail(e)))
									.mapError(DbError.DecodingError(_))
							} |> ZIO.collectAll
						}*/

				override def saveEvents(eventStreamId: EventStreamId, events: Seq[RepositoryEvent[E]]): IO[DbError, Int] = ???
					/*Update[String]("insert into events (valuez) values (?::jsonb)")
						.updateMany(events.toJson)
						.transact(cnx)
						.flatMapError(e => ZIO.logErrorCause(Cause.fail(e)).map(_ => DbError.DbException(e)))*/
		} |> ZLayer.fromZIO

private object Req:
	import doobie.Fragment
	import doobie.implicits.*
	import doobie.util.update.*
	import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec.SafeUUIDJsonCodec

	def list(eventStreamId: AggregateUid, aggName: String): Fragment = ???
		/*val aggUid = eventStreamId.safeValue
		sql"""select jsonb_set(valuez, '{insertorder}', to_jsonb(insertorder), true)
		     from events
			 where aggregatename=$aggName
			 and aggregateuid=$aggUid
			 order by insertorder"""*/