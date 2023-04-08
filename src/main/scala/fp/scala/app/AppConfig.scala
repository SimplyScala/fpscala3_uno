package fp.scala.app

import zio.config.*
import zio.{Cause, ZIO, ZLayer}
import zio.prelude.AnySyntax

case class DbConf(host: String, port: String, db: String, user: String, password: String)

private def dbConfFromPgAddon(conf: CleverEnv) =
	DbConf(
		conf.POSTGRESQL_ADDON_HOST,
		conf.POSTGRESQL_ADDON_PORT,
		conf.POSTGRESQL_ADDON_DB,
		conf.POSTGRESQL_ADDON_USER,
		conf.POSTGRESQL_ADDON_PASSWORD
	)

case class AppConfig(serverConfig: ServerConfig, secret: String, db: DbConf)

case class ServerConfig(port: Int)

object AppConfig:
	type ConfigLayer = AppConfig & ServerConfig & DbConf

	val live: ZLayer[Any, Unit, ConfigLayer] =
		val cleverConf: ZLayer[Any, ReadError[String], CleverEnv] = ZConfig.fromSystemEnv(cleverEnv)
		ZLayer
			.fromZIO(
				ZIO.service[CleverEnv]
					.provide(cleverConf)
					.map { cleverEnv =>
						val config = AppConfig(
							ServerConfig(cleverEnv.PORT),
							cleverEnv.APPLICATION_SECRET,
							dbConfFromPgAddon(cleverEnv)
						)
						ZLayer.succeed(config) ++
							ZLayer.succeed(config.serverConfig) ++
							ZLayer.succeed(config.db)
					}
			)
			.flatten
			.mapError { e => ZIO.logErrorCause("Config error", Cause.fail(e)) }
