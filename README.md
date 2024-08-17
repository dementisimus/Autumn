# Autumn [![main build status](https://github.com/dementisimus/Autumn/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/dementisimus/Autumn) [![develop build status](https://github.com/dementisimus/Autumn/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/dementisimus/Autumn/tree/develop)

## _Where ideas fall into place._

## **Features**

- ToDo

## **Requirements**

1. **Java 21**
2. **Paper 1.21**
3. **Access** to the **server console** (for the **automated setup**)

## **Development**

##### repository currently not available

### Maven

```xml
<repository>
    <id>dementisimus-dev-release</id>
    <url>https://repo.dementisimus.dev/release/</url>
</repository>

<dependency>
    <groupId>dev.dementisimus.autumn</groupId>
    <artifactId>INSERT_MODULE_HERE-api</artifactId>
    <version>INSERT_LATEST_RELEASE_VERSION_HERE</version>
    <scope>provided</scope>
</dependency>
```

### Gradle

```
maven {
    name 'dementisimus-dev-release'
    url 'https://repo.dementisimus.dev/release/'
}

compileOnly group: 'dev.dementisimus.autumn', name: 'INSERT_MODULE_HERE-api', version: 'INSERT_LATEST_RELEASE_VERSION_HERE'
```

### Example usage
```java
ToDo
```

## **License**

» [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]

# **Here’s to an Autumn of innovation!**

[Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]: <https://creativecommons.org/licenses/by-nc-nd/4.0/>
