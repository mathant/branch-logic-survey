name := "branch-logic-survey"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)


mainClass in (Compile, packageBin) := Some("com.mysurvey.Main")