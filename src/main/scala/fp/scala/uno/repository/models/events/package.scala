package fp.scala.uno.repository.models

import fp.scala.utils.models.safeuuid.*
import zio.json.JsonCodec
import zio.prelude.*
import fp.scala.utils.models.bindings.json.SafeUUIDJsonCodec.SafeUUIDJsonCodec
import fp.scala.utils.typeclass.eq.Eq

package object events:
	opaque type ProcessUid <: SafeUUID = SafeUUID
	object ProcessUid:
		def apply(safeUUID: SafeUUID): ProcessUid = safeUUID
		extension (x: ProcessUid)
			def safeUUID: SafeUUID = x

	opaque type AggregateUid <: SafeUUID = SafeUUID
	object AggregateUid:
		def apply(safeUUID: SafeUUID): AggregateUid = safeUUID
		def generate: AggregateUid = SafeUUID.generate
		extension (x: AggregateUid)
			def safeUUID: SafeUUID = x
		given Eq[AggregateUid] with
			def equal(x: AggregateUid, y: AggregateUid) = x.safeUUID.safeValue == y.safeUUID.safeValue

	opaque type AggregateName <: String = String
	object AggregateName:
		def apply(name: String): AggregateName = name
		extension (x: AggregateName)
			def value: String = x

	case class EventStreamId(id: AggregateUid, aggregateName: AggregateName)

	object JsonCodecs:
		implicit val ProcessUidJsonCodec: JsonCodec[ProcessUid] = SafeUUIDJsonCodec.transform(ProcessUid(_), identity)
		implicit val AggregateUidJsonCodec: JsonCodec[AggregateUid] = SafeUUIDJsonCodec.transform(AggregateUid(_), identity)
		implicit val AggregateNameJsonCodec: JsonCodec[AggregateName] = JsonCodec.string.transform(AggregateName(_), identity)
