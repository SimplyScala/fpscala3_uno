package fp.scala.uno.service

import zio.{Hub, Tag, ZLayer}
import zio.prelude.*

object EventStreamer:
	type EventRecever[E] = Hub[E]
	
	def live[E: Tag] = Hub.unbounded[E] |> ZLayer.fromZIO