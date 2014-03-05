import sbt._
import sbt.Keys._

object ProjectBuild extends Build {
  lazy val project = Project(
    id = "root",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      organization := "com.tresata",
      name := "cascading-opencsv",
      version := "0.1-SNAPSHOT",
      crossPaths := false,
      autoScalaLibrary := false,
      libraryDependencies ++= Seq(
        "cascading" % "cascading-hadoop" % "2.1.6" % "compile",
        "net.sf.opencsv" % "opencsv" % "2.3" % "compile",
        "org.slf4j" % "slf4j-api" % "1.6.6" % "compile",
        "org.apache.hadoop" % "hadoop-core" % "1.0.4" % "provided"
      ),
      publishMavenStyle := true,
      publishTo <<= version { (v: String) =>
        if (v.trim.endsWith("SNAPSHOT"))
          Some("tresata-snapshots" at "http://server01:8080/archiva/repository/snapshots")
        else
          Some("tresata-releases" at "http://server01:8080/archiva/repository/internal")
      },
      credentials += Credentials(Path.userHome / ".m2" / "credentials_internal"),
      credentials += Credentials(Path.userHome / ".m2" / "credentials_snapshots"),
      credentials += Credentials(Path.userHome / ".m2" / "credentials_proxy")
    )
  )
}
