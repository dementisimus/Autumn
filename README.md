# Autumn [![main build status](https://github.com/dementisimus/Autumn/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/dementisimus/Autumn) [![develop build status](https://github.com/dementisimus/Autumn/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/dementisimus/Autumn/tree/develop)

## _Where ideas fall into place._

## **Features**

- Automated & custom setup
- (Infinite)InventoryFactory
- ItemFactory
- Brigadier commands
- (Zip)FileDownloader
- i18n
- UserInput
- Storage: MongoDB, MariaDB/SQLite, File
- NPCs

## **Requirements**

1. **Java 21**
2. **Paper 1.21**

## **Development**

### Maven

```xml
<repository>
    <id>dementisimus-dev-release</id>
    <url>https://repo.dementisimus.dev/release/</url>
</repository>

<dependency>
    <groupId>dev.dementisimus.autumn</groupId>
    <artifactId>api/plugin</artifactId>
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

compileOnly group: 'dev.dementisimus.autumn', name: 'api/plugin', version: 'INSERT_LATEST_RELEASE_VERSION_HERE'
```

### Example usage

» Javadocs: https://docs.dementisimus.dev/release/Autumn/api/INSERT_LATEST_RELEASE_VERSION_HERE/

```java
AutumnInitializer initializer = CustomAutumnInitializer.of(this);

//configure the initializer

initialize(autumn -> {

    //Register commands or events via autumn.registerCommand() / autumn.registerListener()
    //etc    

});
```

## **License**

» [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]

# **Here’s to an Autumn of innovation!**

[Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]: <https://creativecommons.org/licenses/by-nc-nd/4.0/>
