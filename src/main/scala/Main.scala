package main

import zio.{ZIO, ZIOAppDefault}
import zio.Console.*

import java.io.IOException
import fp.scala.uno.domain.models.{CarteDeUno, CouleurDeCarte, ValeurNumeriqueDeCarte}
import ValeurNumeriqueDeCarte.*
import CouleurDeCarte.*
import fp.scala.utils.typeclass.eq.Eq.*
import fp.scala.utils.typeclass.show.Show.*
import fp.scala.utils.typeclass.ord.Ord.*

object Main extends ZIOAppDefault:
	override def run: ZIO[Any, IOException, Unit] = myAppLogic

	val myAppLogic: ZIO[Any, IOException, Unit] =
		for {
			_   <- printLine("FP Uno !")
			_   <- printLine(game)
			_   <- printLine(CarteDeUno.CarteNumerique(ZERO, Bleu) === CarteDeUno.CarteNumerique(ZERO, Bleu))
			_   <- printLine(ZERO < UN)
			/*name <- readLine
			_    <- printLine(s"Hello, ${name}, welcome to ZIO!")*/
		} yield ()

	private def game: String = {
		CarteDeUno.CarteNumerique(ZERO, Bleu).show
	}