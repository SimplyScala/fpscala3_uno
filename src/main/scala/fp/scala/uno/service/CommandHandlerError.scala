package fp.scala.uno.service

import fp.scala.app.infrastructure.models.DbError
import fp.scala.uno.domain.UnoErreur

enum CommandHandlerError:
	case DomainError(error: UnoErreur) extends CommandHandlerError
	case FromDbError(e: DbError) extends CommandHandlerError