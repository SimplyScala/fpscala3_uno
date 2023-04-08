package fp.scala.utils.models.http.bindings.ziojson

import fp.scala.utils.models.http.RestNavigationLink as RNL
import zio.json.{DeriveJsonCodec, DeriveJsonEncoder, JsonCodec, JsonEncoder}

object RestNavigationLinkJsonCodec:
    import HttpVerbJsonCodec.httpVerbJsonCodec

    implicit val restNavigationLinkJsonCodec: JsonCodec[RNL] = DeriveJsonCodec.gen[RNL]
