# sisioh config

[![Build Status](https://travis-ci.org/sisioh/sisioh-config.svg?branch=develop)](https://travis-ci.org/sisioh/sisioh-config)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.sisioh/sisioh-config_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.sisioh/sisioh-config_2.11)
[![Scaladoc](http://javadoc-badge.appspot.com/org.sisioh/sisioh-config.svg?label=scaladoc)](http://javadoc-badge.appspot.com/org.sisioh/sisioh-config_2.11)
[![Reference Status](https://www.versioneye.com/java/org.sisioh:sisioh-config_2.11/reference_badge.svg?style=flat)](https://www.versioneye.com/java/org.sisioh:sisioh-config_2.11/references)


sisioh-config is scala wrapper for typesafe [config](https://github.com/typesafehub/config).


## Installation

Add the following to your sbt build (Scala 2.10.x, and Scala 2.11.x):

### Release Version

```scala
resolvers += "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "org.sisioh" %% "sisioh-config" % "0.0.7"
```

### Snapshot Version

```scala
resolvers += "Sonatype OSS Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "org.sisioh" %% "sisioh-config" % "0.0.8-SNAPSHOT"
```

## usage

### read raw value

conf/application.conf

``` 
foo.bar1 = value1
foo.bar2 = value2
foo.bar3 = 1
```

```scala
val config = Configuration.parseFile(new File("conf/application.conf"))
val Some(bar1) = config.getStringValue("foo.bar1") // value1
val Some(bar2) = config.getStringValue("foo.bar2") // value2
val Some(bar3) = config.getIntValue("foo.bar3") // 1
```

### read raw value as Seq

conf/application.conf

``` 
foo = [value1, value2]
```

```scala
val config = Configuration.parseFile(new File("conf/application.conf"))
val Some(values) = config.getStringValues("foo") // Seq(value1, value2)
```

### read ConfigurationValue

conf/application.conf

``` 
foo.bar1 = value1
foo.bar2 = value2
foo.bar3 = 1
```

```scala
val config = Configuration.parseFile(new File("conf/application.conf"))
val Some(bar1ConfigValue) = config.getConfigurationValue("foo.bar1")
val Some(bar1) = bar1ConfigValue.valueAsString // value1
// ...
```

### read Configuration

conf/application.conf

```
foo.bar1 = value1
foo.bar2 = value2
foo.bar3 = 1
```

```scala
val config = Configuration.parseFile(new File("conf/application.conf"))
val Some(foo) = config.getConfiguration("foo")
val Some(bar1) = foo.getStringValue("bar1") // value1
val Some(bar2) = foo.getStringValue("bar2") // value2
val Some(bar3) = foo.getIntValue("bar3") // 1
```

### read ConfigurationObject

conf/application.conf

```
db = { driverClassName: com.mysql.jdbc.Driver, url: jdbc:mysql://localhost/test }
```

```scala
val config = Configuration.parseFile(new File("conf/application.conf"))
val Some(dbConfigObject) = config.getConfigurationObject("db")
val Some(driverClassName) = dbConfigObject.get("driverClassName") // com.mysql.jdbc.Driver
val Some(url) = dbConfigObject.get("url") // jdbc:mysql://localhost/test
```
