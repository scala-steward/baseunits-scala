import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import org.scalastyle.sbt.ScalastylePlugin._

import scalariform.formatter.preferences._

val compileScalaStyle = taskKey[Unit]("compileScalaStyle")

lazy val scalaStyleSettings = Seq(
  (scalastyleConfig in Compile) := file("scalastyle-config.xml"),
  compileScalaStyle := scalastyle.in(Compile).toTask("").value,
  (compile in Compile) <<= (compile in Compile) dependsOn compileScalaStyle
)

val formatPreferences = FormattingPreferences()
  .setPreference(RewriteArrowSymbols, false)
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(SpacesAroundMultiImports, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(AlignArguments, true)

val commonSettings =
  scalaStyleSettings ++ SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile := formatPreferences
    , ScalariformKeys.preferences in Test := formatPreferences) ++
    site.settings ++ site.includeScaladoc() ++ Seq(
    sonatypeProfileName := "org.sisioh",
    organization := "org.sisioh",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.10.5", "2.11.8", "2.12.0"),
    scalacOptions ++= Seq(
      "-feature"
      , "-deprecation"
      , "-unchecked"
      , "-encoding", "UTF-8"
//      , "-Xfatal-warnings"
      , "-language:existentials"
      , "-language:implicitConversions"
      , "-language:postfixOps"
      , "-language:higherKinds"
    ),
    resolvers ++= Seq(
      "Twitter Repository" at "http://maven.twttr.com/",
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Sonatype Release Repository" at "https://oss.sonatype.org/content/repositories/releases/",
      "Sonatype Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
      "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      "Artima Maven Repository" at "http://repo.artima.com/releases"
    ),
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.8.1" % "test",
      "org.scalactic" %% "scalactic" % "3.0.1",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test",
      "com.novocode" % "junit-interface" % "0.8" % "test->default",
      "org.mockito" % "mockito-core" % "1.9.5" % "test",
      "commons-io" % "commons-io" % "2.4"
    ),
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := {
      _ => false
    },
    pomExtra := (
      <url>https://github.com/sisioh/baseunits-scala</url>
        <licenses>
          <license>
            <name>Apache License Version 2.0</name>
            <url>http://www.apache.org/licenses/</url>
            <distribution>repo</distribution>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:sisioh/baseunits-scala.git</url>
          <connection>scm:git:git@github.com:sisioh/baseunits-scala.git</connection>
        </scm>
        <developers>
          <developer>
            <id>j5ik2o</id>
            <name>Junichi Kato</name>
            <url>http://j5ik2o.me</url>
          </developer>
        </developers>
      ),
    credentials := {
      val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
      Credentials(ivyCredentials) :: Nil
    }
  )

lazy val library = Project(
  id = "baseunits-scala-library",
  base = file("library"),
  settings = commonSettings ++ Seq(
    name := "baseunits-scala"
  )
)

lazy val example = Project(
  id = "baseunits-scala-example",
  base = file("example"),
  settings = commonSettings ++ Seq(
    name := "baseunits-scala-example"
  )
) dependsOn library

lazy val root = Project(
  id = "baseunits-scala-project",
  base = file("."),
  settings = commonSettings ++ Seq(
    name := "baseunits-scala-project"
  )
) aggregate(library, example)

