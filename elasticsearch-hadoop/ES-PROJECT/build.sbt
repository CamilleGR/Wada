name := "ES-PROJECT"

version := "1.0"

scalaVersion := "2.11.5"

scalacOptions := Seq("-unchecked", "-encoding", "utf8")


libraryDependencies ++= Seq(
        "org.elasticsearch" % "elasticsearch" % "1.4.2",
        "org.apache.lucene" % "lucene-core" % "4.10.2",
        "org.apache.lucene" % "lucene-analyzers-common" % "4.10.2",
        "org.apache.lucene" % "lucene-queries" % "4.10.2",
        "org.apache.lucene" % "lucene-memory" % "4.10.2",
        "org.apache.lucene" % "lucene-highlighter" % "4.10.2",
        "org.apache.lucene" % "lucene-highlighter" % "4.10.2",
        "org.apache.lucene" % "lucene-sandbox" % "4.10.2",
        "org.apache.lucene" % "lucene-suggest" % "4.10.2",
        "org.apache.lucene" % "lucene-misc" % "4.10.2",
        "org.apache.lucene" % "lucene-join" % "4.10.2",
        "org.apache.lucene" % "lucene-grouping" % "4.10.2",
        "org.apache.lucene" % "lucene-spatial" % "4.10.2",
        "org.apache.lucene" % "lucene-expressions" % "4.10.2",
        "org.elasticsearch" % "elasticsearch-hadoop" % "2.0.2",
        "org.elasticsearch" % "elasticsearch-hadoop-pig" % "2.0.2",
        "org.elasticsearch" % "elasticsearch-hadoop-cascading" % "2.0.2"
)
