import zio.{ZIO, ZIOAppDefault}
import zio.Console.*

import java.io.IOException

object Main extends ZIOAppDefault:
	override def run: ZIO[Any, IOException, Unit] = myAppLogic

	val myAppLogic: ZIO[Any, IOException, Unit] =
		for {
			_    <- printLine("FP Uno !")
			/*name <- readLine
			_    <- printLine(s"Hello, ${name}, welcome to ZIO!")*/
		} yield ()

/*@main def hello: Unit =
  println("Hello world!")
  println(msg)

def msg = "I was compiled by Scala 3. :)"*/
