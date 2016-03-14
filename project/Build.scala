import play.sbt.PlayScala
import sbt._
import sbt.Keys._

object Build extends Build {

	val appScalaVersion = "2.11.7"

	lazy val main = Project("demo", base = file(".")).enablePlugins(PlayScala).settings(
		scalaVersion := appScalaVersion,
		libraryDependencies ++= List(
			"com.typesafe.slick" %% "slick" % "3.1.1",
			"com.typesafe.slick" %% "slick-codegen" % "3.1.1",
			"com.typesafe.play" %% "play-slick" % "2.0.0",
			"com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
			"com.h2database" % "h2" % "1.4.190",
			"org.scalatest" %% "scalatest" % "2.2.1" % "test",
			"org.scalatestplus" %% "play" % "1.4.0" % "test",
			"org.mockito" % "mockito-all" % "1.10.19"
		),
		slick <<= slickCodeGenTask/*,
		sourceGenerators in Compile <+= slickCodeGenTask*/
	)

	lazy val slick = TaskKey[Seq[File]]("db")

	lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
		val outputDir = "app"
		val url = "jdbc:h2:mem:test;INIT=runscript from 'conf/resources/create.sql'"
		val jdbcDriver = "org.h2.Driver"
		val slickDriver = "slick.driver.H2Driver"
		val pkg = "db"
		toError(r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg), s.log))
		val fname = outputDir + "Tables.scala"
		Seq(file(fname))
	}

}