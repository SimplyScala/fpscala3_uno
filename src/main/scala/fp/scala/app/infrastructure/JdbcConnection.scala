package fp.scala.app.infrastructure

import cats.effect.*
import cats.syntax.apply.*
import com.zaxxer.hikari.HikariConfig
import doobie.Transactor
import doobie.hikari.HikariTransactor
import fp.scala.app.DbConf
import fp.scala.app.infrastructure.models.DbError
import zio.interop.catz.*
import zio.prelude.AnySyntax
import zio.*

object JdbcConnection:
	type ZTransactor = Transactor[Task]
	private def getXA =
		implicit val runtime: Runtime[Any] = Runtime.default
		for
			dbConfig <- ZIO.service[DbConf]
				be <- ZIO.blockingExecutor
				executionContext = be.asExecutionContext
				blocker = Blocker.liftExecutionContext(executionContext)
				_ <- ZIO.logDebug("before pool")
				xa <- HikariTransactor
					.newHikariTransactor[zio.Task](
						"org.postgresql.Driver",
						s"jdbc:postgresql://${dbConfig.host}:${dbConfig.port}/${dbConfig.db}",
						dbConfig.user,
						dbConfig.password,
						executionContext,
						blocker
					)
					.toScoped
					.mapError { DbError.ConnectionError.apply }
				_ <- ZIO.logDebug("after pool")
		yield xa

	/** `lazy` doesn't prevent multiple loading, only the correct way to load layers make the trick
	 */
	lazy val live: ZLayer[DbConf & Scope, DbError, ZTransactor] = getXA |> ZLayer.fromZIO