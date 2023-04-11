package fp.scala.uno.service

import zio.{Hub, Tag, ZLayer}
import zio.prelude.*

object EventStreamer:
	type EventRecever[E] = Hub[E]
	
	def live[E: Tag] = Hub.unbounded[E] |> ZLayer.fromZIO

	/*
	* import pi.iesm.events.models.{AckEventStreamId, EventStreamId}
import zio.prelude.*
import zio.stream.ZStream
import zio.{Queue, *}

object Queues:
    type Queues = Queue[EventStreamId] & Hub[AckEventStreamId]

    def queues(): ULayer[Queues] =
        (Queue.unbounded[EventStreamId] |> ZLayer.fromZIO) ++ (Hub.unbounded[AckEventStreamId] |> ZLayer.fromZIO)
	* */