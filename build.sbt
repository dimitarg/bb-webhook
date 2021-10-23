val scalaVer = "2.13.4"

val commonSettings = Seq(
  ThisBuild / scalaVersion := scalaVer,
  ThisBuild / crossScalaVersions := Seq(scalaVer),
  ThisBuild / licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
  addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.11.1" cross CrossVersion.full),
  addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
  organization := "io.github.dimitarg",
  resolvers ++= Seq(
    Resolver.jcenterRepo,
    Resolver.bintrayRepo("dimitarg", "maven")
  ),
  testFrameworks += new TestFramework("weaver.framework.TestFramework"),
  Test / fork := true,
  test in assembly := {}
)

val ghWorkflowSettings = Seq(
  ThisBuild / githubWorkflowScalaVersions := Seq(scalaVer),
  ThisBuild / githubWorkflowBuild := Seq(WorkflowStep.Sbt(List("coverage", "test", "coverageReport"))),
  ThisBuild / githubWorkflowEnv += "BINTRAY_USER" -> "${{ secrets.BINTRAY_USER }}",
  ThisBuild / githubWorkflowEnv += "BINTRAY_PASS" -> "${{ secrets.BINTRAY_PASS }}",
  ThisBuild / githubWorkflowBuildPostamble := Seq(WorkflowStep.Run(
    commands = List("bash <(curl -s https://codecov.io/bash)")
  )),
  ThisBuild / githubWorkflowPublishPreamble := Seq(WorkflowStep.Run(
    List("git config user.name \"Github Actions (dimitarg/bb-webhook)\"")
  )),
  ThisBuild / githubWorkflowPublish := Seq(WorkflowStep.Sbt(List("release with-defaults")))
)

val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", file)
      if file == "MANIFEST.MF" =>
        MergeStrategy.discard
    case PathList(ps @ _*)
      if  ps.last == "NOTICE" || ps.last == "LICENSE" =>
        MergeStrategy.discard
    case _ => MergeStrategy.singleOrError
  },
  artifact in (Compile, assembly) ~= { art =>
      art.withClassifier(`classifier` = Some("assembly"))
  } 
) ++ addArtifact(artifact in (Compile, assembly), assembly).settings

val deps = Seq(
  "org.http4s"          %% "http4s-ember-server"           % "0.21.14",
  "io.github.dimitarg"  %% "weaver-test-extra"             % "0.4.8"      % "test"
)

lazy val root = (project in file("."))
  .settings(
    name := "bb-webhook-root",
  )
  .settings(commonSettings)
  .settings(skip in publish := true)
  .settings(ghWorkflowSettings)
  .aggregate(server, integrationTests)

lazy val server = (project in file("modules/server"))
  .settings(
    name := "bb-webhook"
  )
  .settings(commonSettings)
  .settings(assemblySettings)
  .settings(libraryDependencies ++= deps)

lazy val integrationTests = (project in file("modules/integrationTests"))
  .settings(
    name := "bb-webhook-integration-tests"
  )
  .settings(commonSettings)
  .settings(skip in publish := true)
  .settings(libraryDependencies ++= deps)
  .dependsOn(server)