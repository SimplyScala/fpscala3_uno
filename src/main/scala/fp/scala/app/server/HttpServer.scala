package fp.scala.app.server

import fp.scala.app.ServerConfig
import fp.scala.app.api.{APIRoutes, Paths, RootAPI}
import sttp.capabilities.zio.ZioStreams
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.ztapir.*
import sttp.tapir.{Endpoint, PublicEndpoint}
import zhttp.http.*
import zhttp.http.middleware.Cors.CorsConfig
import zhttp.service.Server
import zio.{ZIO, ZLayer}
import fp.scala.app.AppLayer

object HttpServer:
	val serverSetup: ZIO[ServerConfig, Nothing, Server[AppLayer, Throwable]] =
		val apiRoutes: Http[AppLayer, Throwable, Request, Response] =
			ZioHttpInterpreter().toHttp(RootAPI.rootAPI :: APIRoutes.APIs)

		val corsConfig: CorsConfig = CorsConfig()

		for
			serverConfig <- ZIO.service[ServerConfig]
			_ <- ZIO.logInfo(s"Starting fp-uno on port ${serverConfig.port}")
		yield Server.port(serverConfig.port) ++ Server.app(
			((httpPrefix(Paths.prefix) >>> apiRoutes) ++
			(httpPrefix(Paths.prefixEvent) >>> (
				ZioHttpInterpreter().toHttp(APIRoutes.STREAM_APIs)))
			)	
				@@ Middleware.cors(corsConfig)
		)

	private def httpPrefix(prefix: Path) =
		Http.collect[Request] {
			case req if req.path.startsWith(prefix) =>
				req.setUrl(req.url.copy(path = Path.root ++ req.path.drop(prefix.segments.length)))
		}


/*
Server.port(serverConfig.port) ++ Server.app(
          ((httpPrefix(Paths.prefix) >>> apiRoutes) ++
              (httpPrefix(Paths.prefixEvent) >>> (ZioHttpInterpreter()
                  .toHttp(EventsAPI.endpoints) ++ ZioHttpInterpreter().toHttp(EventsAPI.updates))))
              @@ Middleware.cors(corsConfig)
        )

val serverSetup: ZIO[ServerConfig, Nothing, Server[AppLayer, Throwable]] =
        val apiRoutes: Http[AppLayer, Throwable, Request, Response] =
            ZioHttpInterpreter().toHttp(RootAPI.rootHttp ::
                (ParcoursEtudiantAPI.endpoints ++
                    PlansAPI.endpoints ++
                    ModuleAPI.endpoints ++
                    DiplomeAPI.endpoints ++
                    LabelAPI.endpoints
                )
            )

        val corsConfig: CorsConfig = CorsConfig()

        for
            serverConfig <- ZIO.service[ServerConfig]
            _ <- ZIO.logInfo(s"Starting iesm-back on port ${serverConfig.port}")
        yield Server.port(serverConfig.port) ++ Server.app(
          ((httpPrefix(Paths.prefix) >>> apiRoutes) ++
              (httpPrefix(Paths.prefixEvent) >>> (ZioHttpInterpreter()
                  .toHttp(EventsAPI.endpoints) ++ ZioHttpInterpreter().toHttp(EventsAPI.updates))))
              @@ Middleware.cors(corsConfig)
        )
*/