import sbt.Keys._
import sbt._

object Dependencies extends AutoPlugin {

  object autoImport {
    implicit final class ProjectDependencies(val project: Project) extends AnyVal {
      def withDependencies: Project =
        project.settings(dependencySettings(project.id))
    }
  }

  private def dependencySettings(projectId: String): Seq[Def.Setting[_]] = {
    val cats = Seq(
      "org.typelevel" %% "cats-core",
      "org.typelevel" %% "cats-effect"
    ).map(_ % "2.0.0")

    val fs2 = Seq(
      "co.fs2" %% "fs2-core"
    ).map(_ % "2.0.0")

    val specs2 = Seq(
      "org.specs2" %% "specs2-core"
    ).map(_ % "4.6.0" % Test)

    val indexerCoreDependencies = Nil

    val routerCoreDependencies = cats ++ fs2 ++ specs2

    val routerDijkstraDependencies = Nil

    val utilsDependencies = cats

    libraryDependencies ++= {
      projectId match {
        case "indexerCore"    => indexerCoreDependencies
        case "routerCore"     => routerCoreDependencies
        case "routerDijkstra" => routerDijkstraDependencies
        case "utils"          => utilsDependencies
        case _                => Nil
      }
    }
  }

}
