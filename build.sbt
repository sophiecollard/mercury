lazy val indexerCore = project
  .in(file("indexer-core"))
  .withDefaultSettings
  .withDependencies

lazy val routerCore = project
  .in(file("router-core"))
  .withDefaultSettings
  .withDependencies

lazy val routerDijkstra = project
  .in(file("router-dijkstra"))
  .dependsOn(routerCore)
  .withDefaultSettings
  .withDependencies

lazy val utils = project
  .in(file("utils"))
  .withDefaultSettings
  .withDependencies
