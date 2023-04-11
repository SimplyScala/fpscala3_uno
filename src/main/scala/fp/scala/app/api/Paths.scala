package fp.scala.app.api

import zhttp.http.*

object Paths:
	private val API = "api"
	private val V1 = "v1"
	private val V_EVENT = "vEvent"
	val UNO_GAME = "unogame"

	def prefix(proto: Option[XForwardedProtoHeader], host: HostHeader): String = s"${proto.getOrElse("http")}://$host/$API/$V1/"

	def prefix: Path = Path.root / API / V1
	def prefixEvent: Path = Path.root / API / V_EVENT
