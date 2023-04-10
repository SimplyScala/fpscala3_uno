package fp.scala.uno.application.api

import fp.scala.uno.service.CommandHandlerError
import CommandHandlerError.*
import fp.scala.app.api.EndpointsError
import fp.scala.uno.domain.UnoErreur.*
import zio.json.ast.Json

object UnoAPIError {
	def toEndpointsError(e: CommandHandlerError) = e match {
		case DomainError(e) =>
			val j = e match {
				case IlFaut3JoueursMinimum => Json.Obj(("IlFaut3JoueursMinimum", Json.Obj()))
			}
			EndpointsError.BadRequest(j)

		case FromDbError(e) => EndpointsError.ServerError("Un problème s'est produit")
	}
}