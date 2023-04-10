package fp.scala.uno.repository.models.events

import fp.scala.utils.models.safeuuid.SafeUUID
import zio.json.{DeriveJsonCodec, JsonCodec}

import java.time.OffsetDateTime

case class RepositoryEvent[E](processUid: ProcessUid,
                              aggregateUid: AggregateUid,
                              aggregateName: AggregateName,
                              sentDate: OffsetDateTime,
                              //doneBy: DoneBy,
                              event: E)

enum DoneBy:
	case Player(uid: SafeUUID) extends DoneBy

/*
object RepositoryEvent:
	import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec.SafeUUIDJsonCodec
	implicit val DoneByJsonCodec: JsonCodec[DoneBy] = DeriveJsonCodec.gen[DoneBy]
	implicit def RepositoryEventJsonCodec[E](implicit codec: JsonCodec[E]): JsonCodec[RepositoryEvent[E]] = DeriveJsonCodec.gen[RepositoryEvent[E]]*/
