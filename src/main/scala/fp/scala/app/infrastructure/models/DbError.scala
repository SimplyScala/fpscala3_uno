package fp.scala.app.infrastructure.models

enum DbError:
	case ConnectionError(e: Throwable)
	case DbException(e: Throwable)
	case DecodingError(e: String)

object DbError:
	def showError(e: DbError): String = e match
		case DbError.ConnectionError(e) => e.getMessage
		case DbError.DbException(e)     => e.getMessage
		case DbError.DecodingError(e)   => e
