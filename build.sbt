val scala3Version = "3.2.2"
val ZioVersion = "2.0.10"

lazy val root = project
	.in(file("."))
	.settings(
		name := "fpScala3_Uno",
		version := "0.1.0-SNAPSHOT",

		scalaVersion := scala3Version,

		scalacOptions += "-rewrite",
		scalacOptions := Seq("-Xmax-inlines", "140"),
		//scalacOptions += "-Wconf:cat=other-match-analysis:error",

		libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,

		libraryDependencies += "dev.zio" %% "zio" % ZioVersion,
		libraryDependencies += "dev.zio" %% "zio-streams" % ZioVersion,
		libraryDependencies += "dev.zio" %% "zio-prelude" % "1.0.0-RC5",

		libraryDependencies += "com.softwaremill.magnolia1_3" %% "magnolia" % "1.3.0",

		libraryDependencies ++= Seq(
			"org.tpolecat" %% "doobie-core",
			"org.tpolecat" %% "doobie-postgres",
			"org.tpolecat" %% "doobie-hikari"
		).map(_ % "0.13.4"),

		libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % Test,
		libraryDependencies += "org.scalatest" %% "scalatest-funspec" % "3.2.14" % Test,

		Compile / packageDoc / mappings := Seq()
	)