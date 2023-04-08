package fp.scala.app

import doobie.util.transactor.Transactor
import zio.*

type AppLayer = AppConfig.ConfigLayer /*& UserRepository & JdbcConnection.ZTransactor*/

object AppLayer:
	lazy val live: ZLayer[Scope, Any, AppLayer] =
		//val cnxLayer: ZLayer[Scope, Any, Transactor[Task]] = AppConfig.live >>> JdbcConnection.live

		AppConfig.live //++ cnxLayer ++ (cnxLayer >>> UserRepository.live)
