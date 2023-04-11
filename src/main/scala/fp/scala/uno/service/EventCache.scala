package fp.scala.uno.service

import fp.scala.uno.repository.models.events.{AggregateUid, RepositoryEvent}
import fp.scala.uno.domain.events.UnoEvent
import zio.{Ref, ZLayer}
import zio.prelude.AnySyntax
import zio.UIO

object EventCache:
	enum CacheUsing:
		case NoCache extends CacheUsing
		case CacheEvts(events: Seq[RepositoryEvent[UnoEvent]]) extends CacheUsing

	type EventsMemoryCache = Ref[Map[AggregateUid, Seq[RepositoryEvent[UnoEvent]]]]

	object EventsMemoryCache:
		extension (x: EventsMemoryCache)
			def get_(aggregateUid: AggregateUid): UIO[CacheUsing] =
				x.get.map { _.get(aggregateUid).map(CacheUsing.CacheEvts(_)).getOrElse(CacheUsing.NoCache) }

	val live = Ref.make(Map[AggregateUid, Seq[RepositoryEvent[UnoEvent]]]()) |> ZLayer.fromZIO