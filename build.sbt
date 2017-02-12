lazy val commonSettings = Seq(
  organization := "com.github.laysakura",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint"),
  version := (version in ThisBuild).value,
  parallelExecution in ThisBuild := false,
  resolvers += ("twitter" at "https://maven.twttr.com")
)

lazy val versions = new {
  val logback = "1.1.7"
  val finatra = "2.8.0"
  val scrooge = "4.14.0"

  val guice = "4.0"
  val scalatest = "3.0.0"
  val specs2 = "2.4.17"
}

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  aggregate(verboseService)

lazy val common = (project in file("common")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % versions.logback,
      "com.twitter" %% "scrooge-core" % versions.scrooge,
      "com.twitter" %% "inject-core" % versions.finatra,
      "com.twitter" %% "inject-app" % versions.finatra,

      "org.scalatest" %% "scalatest" % versions.scalatest % "test",
      "org.specs2" %% "specs2-mock" % versions.specs2 % "test"
    )
  )

lazy val verboseService = (project in file("verboseService")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finatra-thrift" % versions.finatra,

      "com.twitter" %% "finatra-thrift" % versions.finatra % "test",
      "com.twitter" %% "inject-app" % versions.finatra % "test",
      "com.twitter" %% "inject-core" % versions.finatra % "test",
      "com.twitter" %% "inject-modules" % versions.finatra % "test",
      "com.twitter" %% "inject-server" % versions.finatra % "test",
      "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

      "com.twitter" %% "finatra-thrift" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",
      "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests"
    )
  ).
  aggregate(verboseServiceIdl).
  dependsOn(verboseServiceIdl % "test->test;compile->compile")

lazy val verboseServiceIdl = (project in file("verboseServiceIdl")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finatra-thrift" % versions.finatra
    ),
    scroogeThriftSourceFolder in Compile := baseDirectory { base => base / "src/main/thrift" }.value,
    scroogeThriftDependencies in Compile := Seq("finatra-thrift_2.11")
  ).
  aggregate(common).
  dependsOn(common % "test->test;compile->compile")
