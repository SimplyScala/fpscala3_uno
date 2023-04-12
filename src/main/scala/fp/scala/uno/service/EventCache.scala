package fp.scala.uno.service

import fp.scala.uno.repository.models.events.{AggregateUid, RepositoryEvent}
import AggregateUid.*
import fp.scala.uno.domain.events.UnoEvent
import zio.{Ref, ZLayer}
import fp.scala.utils.typeclass.eq.Eq
import Eq.*
import zio.prelude.AnySyntax
import zio.UIO

object EventCache:
	enum CacheUsing:
		case NoCache extends CacheUsing
		case CacheEvts(events: Seq[RepositoryEvent[UnoEvent]]) extends CacheUsing

	type StaticCache = Map[AggregateUid, Seq[RepositoryEvent[UnoEvent]]]
	type EventsMemoryCache = Ref[StaticCache]

	object EventsMemoryCache:
		extension (x: EventsMemoryCache)
			def get_(aggregateUid: AggregateUid): UIO[CacheUsing] =
				x.get.map { _.properGet(aggregateUid).map(CacheUsing.CacheEvts(_)).getOrElse(CacheUsing.NoCache) }

	extension [A: Eq, B](m: Map[A, B])
		def properGet(k: A): Option[B] = m.iterator.collectFirst { case (k1, v) if k1 === k => v }
		def properUpsert(k: A, v: B): Map[A, B] =
			m.properGet(k)
				.map { _ => m.map { case (k1, v1) => if(k1 === k) (k, v) else (k1, v1) } }
				.getOrElse { m + (k -> v) }

	val live = Ref.make(Map[AggregateUid, Seq[RepositoryEvent[UnoEvent]]]()) |> ZLayer.fromZIO