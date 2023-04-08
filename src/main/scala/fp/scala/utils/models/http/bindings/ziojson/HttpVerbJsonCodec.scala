package fp.scala.utils.models.http.bindings.ziojson

import fp.scala.utils.models.http.HttpVerb
import zio.json.*

object HttpVerbJsonCodec:
    private def encode(x: String) = x match
        case "GET"     => Right(HttpVerb.GET)
        case "HEAD"    => Right(HttpVerb.HEAD)
        case "POST"    => Right(HttpVerb.POST)
        case "PUT"     => Right(HttpVerb.PUT)
        case "DELETE"  => Right(HttpVerb.DELETE)
        case "OPTIONS" => Right(HttpVerb.OPTIONS)
        case "PATCH"   => Right(HttpVerb.PATCH)
        case "CONNECT" => Right(HttpVerb.CONNECT)
        case "TRACE"   => Right(HttpVerb.TRACE)
        case v         => Left(s"$v is not a valid HttpVerb")

    private def decode(v: HttpVerb) = v match
        case HttpVerb.GET     => "GET"
        case HttpVerb.HEAD    => "HEAD"
        case HttpVerb.POST    => "POST"
        case HttpVerb.PUT     => "PUT"
        case HttpVerb.DELETE  => "DELETE"
        case HttpVerb.OPTIONS => "OPTIONS"
        case HttpVerb.PATCH   => "PATCH"
        case HttpVerb.CONNECT => "CONNECT"
        case HttpVerb.TRACE   => "TRACE"

    implicit val httpVerbJsonCodec: JsonCodec[HttpVerb] = JsonCodec.string.transformOrFail(encode, decode)
