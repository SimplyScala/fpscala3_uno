val scala3Version = "3.2.2"
val ZioVersion = "2.0.10"

lazy val uno = project
	.in(file("."))
	.settings(
		name := "fpScala3_Uno",
		version := "0.1.0-SNAPSHOT",

		scalaVersion := scala3Version,

		//scalacOptions += "-rewrite",
		scalacOptions ++= Seq
			( "-Xmax-inlines", "80"
			, "-JXss1024m"
			,"-JXmx8G"
			),
		//scalacOptions += "-Wconf:cat=other-match-analysis:error",

		libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,

		/** ZIO ecosystem */
		libraryDependencies += "dev.zio" %% "zio" % ZioVersion,
		libraryDependencies += "dev.zio" %% "zio-streams" % ZioVersion,
		libraryDependencies += "dev.zio" %% "zio-prelude" % "1.0.0-RC5",

		libraryDependencies += "com.softwaremill.magnolia1_3" %% "magnolia" % "1.3.0",

		/** driver postgresql */
		libraryDependencies ++= Seq(
			"org.tpolecat" %% "doobie-core",
			"org.tpolecat" %% "doobie-postgres",
			"org.tpolecat" %% "doobie-hikari"
		).map(_ % "0.13.4"),

		/** tapir & web server */
		libraryDependencies ++= Seq(
			"com.softwaremill.sttp.tapir" %% "tapir-zio-http-server",
			"com.softwaremill.sttp.tapir" %% "tapir-core",
			"com.softwaremill.sttp.tapir" %% "tapir-json-zio"
		).map(_ % "1.2.1"),

		libraryDependencies += "dev.zio" %% "zio-json" % "0.4.2",
		libraryDependencies ++= Seq(
			"dev.zio" %% "zio-logging",
			"dev.zio" %% "zio-logging-slf4j-bridge"
		).map(_ % "2.1.4"),
		libraryDependencies ++= Seq(
			"dev.zio" %% "zio-config",
			"dev.zio" %% "zio-config-magnolia"
		).map(_ % "3.0.2"),
		libraryDependencies += "io.d11" %% "zhttp" % "2.0.0-RC10", // https://github.com/softwaremill/tapir/pull/2412
		libraryDependencies += "dev.zio" %% "zio-interop-cats" % "22.0.0.0",

		libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.14" % Test,
		libraryDependencies += "org.scalatest" %% "scalatest-funspec" % "3.2.14" % Test,

		libraryDependencies += "dev.zio" %% "zio-direct" % "1.0.0-RC7",

	)

	enablePlugins(JavaAppPackaging)

	Compile / packageDoc / mappings := Seq()