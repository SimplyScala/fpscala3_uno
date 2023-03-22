package fp.scala.uno.domain.models

import ValeurNumeriqueDeCarte._
import CouleurDeCarte._
import JokerType._

object ListeDesCartes:
	/**
	 * 19 cartes numérotés de 0 à 9 par couleur (bleues, vertes, rouges et jaunes)
	 * 2 cartes +2 de chaque couleur
	 * 2 cartes Inversion par couleur
	 * 2 cartes Passer par couleur
	 * 4 Joker
	 * 4 Super Joker
	 */
	val pioche: Seq[CarteDeUno] = Seq(
		CarteJaune._0, CarteBleu._0, CarteRouge._0, CarteVerte._0,
		CarteJaune._1, CarteJaune._1,CarteBleu._1, CarteBleu._1,CarteRouge._1, CarteRouge._1,CarteVerte._1, CarteVerte._1,
		CarteJaune._2, CarteJaune._2,CarteBleu._2, CarteBleu._2,CarteRouge._2, CarteRouge._2,CarteVerte._2, CarteVerte._2,
		CarteJaune._3, CarteJaune._3,CarteBleu._3, CarteBleu._3,CarteRouge._3, CarteRouge._3,CarteVerte._3, CarteVerte._3,
		CarteJaune._4, CarteJaune._4,CarteBleu._4, CarteBleu._4,CarteRouge._4, CarteRouge._4,CarteVerte._4, CarteVerte._4,
		CarteJaune._5, CarteJaune._5,CarteBleu._5, CarteBleu._5,CarteRouge._5, CarteRouge._5,CarteVerte._5, CarteVerte._5,
		CarteJaune._6, CarteJaune._6,CarteBleu._6, CarteBleu._6,CarteRouge._6, CarteRouge._6,CarteVerte._6, CarteVerte._6,
		CarteJaune._7, CarteJaune._7,CarteBleu._7, CarteBleu._7,CarteRouge._7, CarteRouge._7,CarteVerte._7, CarteVerte._7,
		CarteJaune._8, CarteJaune._8,CarteBleu._8, CarteBleu._8,CarteRouge._8, CarteRouge._8,CarteVerte._8, CarteVerte._8,
		CarteJaune._9, CarteJaune._9,CarteBleu._9, CarteBleu._9,CarteRouge._9, CarteRouge._9,CarteVerte._9, CarteVerte._9,
		CarteJaune.plus2, CarteJaune.plus2,CarteBleu.plus2, CarteBleu.plus2,CarteRouge.plus2, CarteRouge.plus2,CarteVerte.plus2, CarteVerte.plus2,
		CarteJaune.passer, CarteJaune.passer,CarteBleu.passer, CarteBleu.passer,CarteRouge.passer, CarteRouge.passer,CarteVerte.passer, CarteVerte.passer,
		CarteJaune.sens, CarteJaune.sens,CarteBleu.sens, CarteBleu.sens,CarteRouge.sens, CarteRouge.sens,CarteVerte.sens, CarteVerte.sens,
		CarteDeUno.Plus4Cartes, CarteDeUno.Plus4Cartes, CarteDeUno.Plus4Cartes, CarteDeUno.Plus4Cartes,		
		CarteDeUno.ChangementDeCouleur, CarteDeUno.ChangementDeCouleur, CarteDeUno.ChangementDeCouleur, CarteDeUno.ChangementDeCouleur		
	)
	
	object CarteJaune:
		val _0 = CarteDeUno.CarteNumeric(ZERO, Jaune)
		val _1 = CarteDeUno.CarteNumeric(UN, Jaune)
		val _2 = CarteDeUno.CarteNumeric(DEUX, Jaune)
		val _3 = CarteDeUno.CarteNumeric(TROIS, Jaune)
		val _4 = CarteDeUno.CarteNumeric(QUATRE, Jaune)
		val _5 = CarteDeUno.CarteNumeric(CINQ, Jaune)
		val _6 = CarteDeUno.CarteNumeric(SIX, Jaune)
		val _7 = CarteDeUno.CarteNumeric(SEPT, Jaune)
		val _8 = CarteDeUno.CarteNumeric(HUIT, Jaune)
		val _9 = CarteDeUno.CarteNumeric(NEUF, Jaune)
		val plus2 = CarteDeUno.Joker(Plus2Cartes, Jaune)
		val passer = CarteDeUno.Joker(Passer, Jaune)
		val sens = CarteDeUno.Joker(ChangementDeSens, Jaune)
	
	object CarteBleu:
		val _0 = CarteDeUno.CarteNumeric(ZERO, Bleu)
		val _1 = CarteDeUno.CarteNumeric(UN, Bleu)
		val _2 = CarteDeUno.CarteNumeric(DEUX, Bleu)
		val _3 = CarteDeUno.CarteNumeric(TROIS, Bleu)
		val _4 = CarteDeUno.CarteNumeric(QUATRE, Bleu)
		val _5 = CarteDeUno.CarteNumeric(CINQ, Bleu)
		val _6 = CarteDeUno.CarteNumeric(SIX, Bleu)
		val _7 = CarteDeUno.CarteNumeric(SEPT, Bleu)
		val _8 = CarteDeUno.CarteNumeric(HUIT, Bleu)
		val _9 = CarteDeUno.CarteNumeric(NEUF, Bleu)
		val plus2 = CarteDeUno.Joker(Plus2Cartes, Bleu)
		val passer = CarteDeUno.Joker(Passer, Bleu)
		val sens = CarteDeUno.Joker(ChangementDeSens, Bleu)
	
	object CarteRouge:
		val _0 = CarteDeUno.CarteNumeric(ZERO, Rouge)
		val _1 = CarteDeUno.CarteNumeric(UN, Rouge)
		val _2 = CarteDeUno.CarteNumeric(DEUX, Rouge)
		val _3 = CarteDeUno.CarteNumeric(TROIS, Rouge)
		val _4 = CarteDeUno.CarteNumeric(QUATRE, Rouge)
		val _5 = CarteDeUno.CarteNumeric(CINQ, Rouge)
		val _6 = CarteDeUno.CarteNumeric(SIX, Rouge)
		val _7 = CarteDeUno.CarteNumeric(SEPT, Rouge)
		val _8 = CarteDeUno.CarteNumeric(HUIT, Rouge)
		val _9 = CarteDeUno.CarteNumeric(NEUF, Rouge)
		val plus2 = CarteDeUno.Joker(Plus2Cartes, Rouge)
		val passer = CarteDeUno.Joker(Passer, Rouge)
		val sens = CarteDeUno.Joker(ChangementDeSens, Rouge)
	
	object CarteVerte:
		val _0 = CarteDeUno.CarteNumeric(ZERO, Vert)
		val _1 = CarteDeUno.CarteNumeric(UN, Vert)
		val _2 = CarteDeUno.CarteNumeric(DEUX, Vert)
		val _3 = CarteDeUno.CarteNumeric(TROIS, Vert)
		val _4 = CarteDeUno.CarteNumeric(QUATRE, Vert)
		val _5 = CarteDeUno.CarteNumeric(CINQ, Vert)
		val _6 = CarteDeUno.CarteNumeric(SIX, Vert)
		val _7 = CarteDeUno.CarteNumeric(SEPT, Vert)
		val _8 = CarteDeUno.CarteNumeric(HUIT, Vert)
		val _9 = CarteDeUno.CarteNumeric(NEUF, Vert)
		val plus2 = CarteDeUno.Joker(Plus2Cartes, Vert)
		val passer = CarteDeUno.Joker(Passer, Vert)
		val sens = CarteDeUno.Joker(ChangementDeSens, Vert)

