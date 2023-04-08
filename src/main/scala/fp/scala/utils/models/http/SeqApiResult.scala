package fp.scala.utils.models.http

import fp.scala.utils.models.http.bindings.ziojson.RestNavigationLinkJsonCodec.restNavigationLinkJsonCodec
import zio.json.*

case class SeqApiResult[A](result: Seq[A], _links: Seq[RestNavigationLink])

implicit def seqApiResultACodec[A](implicit seqCodec: JsonCodec[Seq[A]]): JsonCodec[SeqApiResult[A]] =
	DeriveJsonCodec.gen[SeqApiResult[A]]
