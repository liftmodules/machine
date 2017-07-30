import LiftModule.{liftVersion, liftEdition}

name := "machine"

organization := "net.liftmodules"

version := "1.4.0-SNAPSHOT"

liftVersion := "3.1.0"

liftEdition := (liftVersion apply { _.substring(0,3) }).value

moduleName := name.value + "_" + liftEdition.value

scalaVersion := "2.12.1"

crossScalaVersions := Seq("2.12.1", "2.11.8")

scalacOptions ++= Seq("-unchecked", "-deprecation")

resolvers += "CB Central Mirror" at "http://repo.cloudbees.com/content/groups/public"

resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

libraryDependencies += "net.liftweb" %% "lift-webkit" % liftVersion.value % "provided"
libraryDependencies += "net.liftweb" %% "lift-mapper" % liftVersion.value % "provided"

// For unit tests:

libraryDependencies += "com.h2database" % "h2" % "1.4.193" % "test"

libraryDependencies +=  "ch.qos.logback" % "logback-classic" % "1.1.8" % "test"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.6" % "test"

parallelExecution in Test := false


publishTo := (version.value.endsWith("SNAPSHOT") match {
 	case true  => Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
 	case false => Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
})

credentials ++= (for {
  username <- Option(System.getenv().get("SONATYPE_USERNAME"))
  password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }


pomExtra := (
	<url>https://github.com/liftmodules/machine</url>
	<licenses>
		<license>
	      <name>Apache 2.0 License</name>
	      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
	      <distribution>repo</distribution>
	    </license>
	 </licenses>
	 <scm>
	    <url>git@github.com:liftmodules/machine.git</url>
	    <connection>scm:git:git@github.com:liftmodules/machine.git</connection>
	 </scm>
	 <developers>
	    <developer>
	      <id>liftmodules</id>
	      <name>Lift Team</name>
	      <url>http://www.liftmodules.net</url>
	 	</developer>
	 </developers>
 )
