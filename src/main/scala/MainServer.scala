//import doobie.util.transactor.Transactor
import fp.scala.app.AppLayer
import fp.scala.app.server.HttpServer
import zio.*
import zio.logging.slf4j.bridge.Slf4jBridge
import zio.logging.{LogFormat, consoleJson}

object MainServer extends ZIOAppDefault:
    override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
        Runtime.removeDefaultLoggers >>> consoleJson(LogFormat.colored, LogLevel.Info) >+> Slf4jBridge.initialize

    override def run: ZIO[Scope, Any, ExitCode] =
        (for
            server <- HttpServer.serverSetup
            _ <- server.startDefault
        yield ExitCode.success).provideSome[Scope](AppLayer.live)
