val commonSettings = Seq(
  scalaVersion := "2.13.4",
  addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.11.1" cross CrossVersion.full),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
  organization := "io.github.dimitarg",
  version := "0.0.1-SNAPSHOT",
  resolvers ++= Seq(
    Resolver.jcenterRepo,
    Resolver.bintrayRepo("dimitarg", "maven")
  ),
  testFrameworks += new TestFramework("weaver.framework.TestFramework"),
  Test / fork := true
)

val deps = Seq(
  "org.http4s"          %% "http4s-ember-server"           % "0.21.14",
  "io.github.dimitarg"  %% "weaver-test-extra"             % "0.3.9"      % "test"
)

lazy val root = (project in file("."))
  .settings(
    name := "bb-webhook-root",
  )
  .settings(commonSettings)
  .settings(libraryDependencies ++= deps)
  .aggregate(server, integrationTests)

lazy val server = (project in file("modules/server"))
  .settings(
    name := "bb-webhook"
  )
  .settings(commonSettings)
  .settings(libraryDependencies ++= deps)

lazy val integrationTests = (project in file("modules/integrationTests"))
  .settings(
    name := "bb-webhook-integration-tests"
  )
  .settings(commonSettings)
  .settings(libraryDependencies ++= deps)
  .dependsOn(server)