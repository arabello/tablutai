name := "tablutai"

version := "1.0"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
	"org.scalactic" %% "scalactic" % "3.0.5",
	"com.typesafe.play" %% "play-json" % "2.7.2",
	"org.scalatest" %% "scalatest" % "3.0.5" % "test",
	"com.googlecode.aima-java" % "aima-core" % "3.0.0",
)

mainClass in assembly := Some("ai.tablut.Main")
assemblyDefaultJarName in assembly := "pelle"
target in assembly := baseDirectory.value / "bin"