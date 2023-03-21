package main

import zio.{ZIO, ZIOAppDefault}
import zio.Console.*

import java.io.IOException
import fp.scala.uno.domain.models.{CarteDeUno, ValeurNumeriqueDeCarte, CouleurDeCarte}
import ValeurNumeriqueDeCarte._
import CouleurDeCarte._

import fp.scala.utils.typeclass.show.Show.*

object Main extends ZIOAppDefault:
	override def run: ZIO[Any, IOException, Unit] = myAppLogic

	val myAppLogic: ZIO[Any, IOException, Unit] =
		for {
			_   <- printLine("FP Uno !")
			_   <- printLine(game)
			/*name <- readLine
			_    <- printLine(s"Hello, ${name}, welcome to ZIO!")*/
		} yield ()

	private def game: String = {
		CarteDeUno.CarteNumeric(ZERO, Bleu).show
	}


@main def hello: Unit = {
	println("Hello world!")
	println(msg)
	println(CarteDeUno.CarteNumeric(ZERO, Bleu).show)
}

def msg = "I was compiled by Scala 3. :)"
