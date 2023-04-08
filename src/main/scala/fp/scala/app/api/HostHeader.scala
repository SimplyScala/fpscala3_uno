package fp.scala.app.api

//import pi.app.api.authentification.models.User
import sttp.tapir.{cookie, Codec, CodecFormat, DecodeResult, Endpoint}
import zio.prelude.Subtype

object HostHeader extends Subtype[String]
type HostHeader = HostHeader.Type

implicit val hostCodec: Codec[String, HostHeader, CodecFormat.TextPlain] = Codec.string
    .mapDecode { s => DecodeResult.Value(HostHeader(s)) } { identity }

object XForwardedProtoHeader extends Subtype[String]
type XForwardedProtoHeader = XForwardedProtoHeader.Type

implicit val xForwardedProtoCodec: Codec[String, XForwardedProtoHeader, CodecFormat.TextPlain] = Codec.string
    .mapDecode { s => DecodeResult.Value(XForwardedProtoHeader(s)) } { identity }

case class SecuredQueryIn(xForwardedProtoHeader: Option[XForwardedProtoHeader], host: HostHeader)
