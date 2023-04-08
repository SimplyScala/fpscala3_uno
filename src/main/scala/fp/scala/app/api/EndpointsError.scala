package fp.scala.app.api

import sttp.model.StatusCode
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.*
import zio.json.*
import zio.json.ast.Json

enum EndpointsError:
	/** 400 * */
	case BadRequest(error: Json)
	/** 401 * */
	case Unauthorized
	/** 403 * */
	case Forbidden
	/** 404 * */
	case NotFound
	/** 503 * */
	case ServerError(error: String)

object EndpointsError:
	def badRequest[A](a: A)(implicit A: JsonEncoder[A]): BadRequest = a.toJsonAST.fold(
		_ => BadRequest(Json.Null),
		j => BadRequest(j)
	)

    //def fromDbError(e: DbError): EndpointsError = ServerError(DbError.showError(e))

	implicit val EndPointsErrorCodec: JsonCodec[EndpointsError] = DeriveJsonCodec.gen[EndpointsError]
	implicit val ForbiddenCodec: JsonCodec[Forbidden.type] = DeriveJsonCodec.gen[Forbidden.type]
	implicit val UnauthorizedCodec: JsonCodec[Unauthorized.type] = DeriveJsonCodec.gen[Unauthorized.type]
	implicit val NotFoundCodec: JsonCodec[NotFound.type] = DeriveJsonCodec.gen[NotFound.type]
	implicit val ServerErrorCodec: JsonCodec[ServerError] = DeriveJsonCodec.gen[ServerError]
	implicit val BadRequestCodec: JsonCodec[BadRequest] = DeriveJsonCodec.gen[BadRequest]

	private val jsonUnauthorized = jsonBody[Unauthorized.type].description("Unauthorized")
	private val jsonBadRequest = jsonBody[BadRequest].description("Bad Request")
	private val jsonServerError = jsonBody[ServerError].description("ServerError")
	private val jsonForbidden = jsonBody[Forbidden.type].description("Forbidden")
	private val jsonNotFound = jsonBody[NotFound.type].description("Not Found")

	def handleOutError: EndpointOutput.OneOf[EndpointsError, EndpointsError] = oneOf[EndpointsError](
		oneOfVariantValueMatcher(StatusCode.Unauthorized, jsonUnauthorized) { case Unauthorized => true },
		oneOfVariantValueMatcher(StatusCode.BadRequest, jsonBadRequest) { case _: BadRequest => true },
		oneOfVariantValueMatcher(StatusCode.NotFound, jsonNotFound) { case NotFound => true },
		oneOfVariantValueMatcher(StatusCode.Forbidden, jsonForbidden) { case Forbidden => true },
		oneOfVariantValueMatcher(StatusCode.InternalServerError, jsonServerError) { case _: ServerError => true }
	)
