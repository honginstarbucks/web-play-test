name := """play-poc"""
organization := "sbux.poc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += ws
libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test


PlayKeys.devSettings:=Seq("play.server.http.port" -> "9001")
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "sbux.poc.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "sbux.poc.binders._"
