package fp.scala.app

import doobie.util.transactor.Transactor
import fp.scala.app.infrastructure.JdbcConnection
import fp.scala.uno.repository.EventRepository
import fp.scala.uno.service.UnoCommandHandler
import fp.scala.uno.domain.events.UnoEvent
import fp.scala.uno.service.UnoEventJsonCodec.UnoEventJsonCodec
import zio.*

type AppLayer = AppConfig.ConfigLayer & UnoCommandHandler & JdbcConnection.ZTransactor/*& UserRepository & JdbcConnection.ZTransactor*/

object AppLayer:
	lazy val live: ZLayer[Scope, Any, AppLayer] =
		val cnxLayer: ZLayer[Scope, Any, Transactor[Task]] = AppConfig.live >>> JdbcConnection.live

		AppConfig.live ++ cnxLayer ++ (cnxLayer >>> EventRepository.live[UnoEvent] >>> UnoCommandHandler.live)
