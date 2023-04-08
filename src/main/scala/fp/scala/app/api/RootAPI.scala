package fp.scala.app.api

import fp.scala.app.api.Paths
import sttp.capabilities.zio.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import sttp.tapir.server.ziohttp.*
import sttp.tapir.ztapir.*
import sttp.tapir.{Endpoint, PublicEndpoint}
import zhttp.http.*
import zhttp.http.middleware.Cors.CorsConfig
import zhttp.service.Server
import zio.*
import zio.prelude.*
import fp.scala.app.{AppLayer, ServerConfig}
import fp.scala.utils.models.http.RestNavigationLink
import fp.scala.utils.models.http.bindings.ziojson.RestNavigationLinkJsonCodec.restNavigationLinkJsonCodec
//import pi.app.api.authentification.SecuredEndpoint
//import pi.app.api.authentification.models.{User, *}
import fp.scala.app.api.{EndpointsError, RootAPI}
import fp.scala.app.api.{HostHeader, XForwardedProtoHeader}
//import pi.app.infrastructure.PiSession.PiSession
//import pi.prelude.http.bindings.ziojson.RestNavigationLinkJsonCodec.*
//import pi.prelude.http.{HttpVerb, RestNavigationLink}
//import HttpVerb.*
import fp.scala.utils.models.http.HttpVerb.GET
import sttp.tapir.ztapir.*
import sttp.capabilities.zio.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import sttp.tapir.server.ziohttp.*
import sttp.tapir.ztapir.*
import sttp.tapir.{Endpoint, PublicEndpoint}
import zhttp.http.*
import zhttp.http.middleware.Cors.CorsConfig
import sttp.model.HeaderNames
import zhttp.service.Server
import fp.scala.utils.base.dsl.*
import zio.*
import zio.prelude.*

object RootAPI /*extends SecuredEndpoint*/:
	/*private def securedEntries(proto: Option[XForwardedProtoHeader], host: HostHeader) = (user: User) =>
		RestNavigationLink("parcoursetudiants", s"${Paths.prefix(proto, host)}${Paths.PARCOURS}", GET) ::
		RestNavigationLink("plansdeformation", s"${Paths.prefix(proto, host)}${Paths.PLANS}", GET) ::
		Nil*/

	/*
	endpoint.piOptAuthCookie
            .errorOut(EndpointsError.handleOutError)
            .zServerSecurityLogic[AppLayer, Option[User]](authenticate)
            .in(header[Option[XForwardedProtoHeader]](HeaderNames.XForwardedProto))
            .in(header[HostHeader](HeaderNames.Host))
	*/

	def rootHttp: ZServerEndpoint[AppLayer, Any] =
		endpoint.get
			.in(header[Option[XForwardedProtoHeader]](HeaderNames.XForwardedProto))
			.in(header[HostHeader](HeaderNames.Host))
			.in("")
			.out(jsonBody[Seq[RestNavigationLink]])
			.serverLogic {
				/*(user: Option[User]) => */ (proto: Option[XForwardedProtoHeader], host: HostHeader) =>
				import zio.json.*
				val rootEndpoint = RestNavigationLink("self", Paths.prefix(proto, host), GET) :: Nil
				/*val securedEndpoints = user
					.map { securedEntries(proto, host) }
					.getOrElse(Nil)*/

				ZIO.succeed(rootEndpoint.right /* :: securedEndpoints*/)
			}
