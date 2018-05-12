name := "replfun"

version := "1.0"

scalaVersion := "2.12.4"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:higherKinds",
  "-Ypartial-unification"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.parboiled"  %% "parboiled"             % "2.1.4",
  "org.jline"          %  "jline"                 % "3.0.1",
  "org.scalaz"     %% "scalaz-core"           % "7.2.21",
  "com.slamdata"   %% "matryoshka-core"       % "0.21.3",
  "org.scalatest"  %% "scalatest"             % "3.0.5"   % Test,
  "com.slamdata"   %% "matryoshka-scalacheck" % "0.21.3"  % Test
)

//enablePlugins(JavaAppPackaging)
