import sbt.Keys._
import sbt._

object Settings extends AutoPlugin {

  object autoImport {
    implicit final class ProjectSettings(val project: Project) extends AnyVal {
      def withDefaultSettings: Project =
        project
        .settings(projectMetadataSettings)
        .settings(scalaSettings)
        .settings(scalacSettings)
        .settings(resolverSettings)
        .settings(testSettings)
    }
  }

  private lazy val projectMetadataSettings = Seq(
    name := "mercury",
    organization := "com.github.sophiecollard",
    homepage := Some(url("https://github.com/sophiecollard")),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
  )

  private lazy val scalaSettings = Seq(
    scalaVersion := "2.13.0"
  )

  private lazy val scalacSettings = Seq(
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-language:higherKinds",
      "-Xfatal-warnings"
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full)
  )

  private lazy val resolverSettings = Seq(
    resolvers += Resolver.sonatypeRepo("releases") // required by kind-projector plugin
  )

  private lazy val testSettings = Seq(
    parallelExecution in Test := false,
    scalacOptions in Test ++= Seq("-Yrangepos") // required by specs2
  )

}
