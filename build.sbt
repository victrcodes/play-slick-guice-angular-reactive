name := """play-slick-guice-angular-reactive"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

routesGenerator := InjectedRoutesGenerator
