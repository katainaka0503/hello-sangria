name := "sangria-example"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.4.0",
  "org.sangria-graphql" %% "sangria-circe" % "1.2.1",
  "org.sangria-graphql" %% "sangria-relay" % "1.4.0",

  "com.typesafe.akka" %% "akka-http" % "10.1.0",
  "de.heikoseeberger" %% "akka-http-circe" % "1.20.0",

  "io.circe" %%	"circe-core" % "0.9.2",
  "io.circe" %% "circe-parser" % "0.9.2",
  "io.circe" %% "circe-optics" % "0.9.2",
)
