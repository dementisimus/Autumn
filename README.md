# Autumn [![release](https://repo.dementisimus.dev/api/badge/latest/development/dev/dementisimus/autumn/autumn?color=40c14a&name=development&prefix=v)](https://repo.dementisimus.dev/#/release/dev/dementisimus/autumn) [![development](https://repo.dementisimus.dev/api/badge/latest/development/dev/dementisimus/autumn/autumn?color=40c14a&name=development&prefix=v)](https://repo.dementisimus.dev/#/development/dev/dementisimus/autumn)

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
    <id>dementisimus-repository-release</id>
    <url>https://repo.dementisimus.dev/release</url>
</repository>

<dependency>
    <groupId>dev.dementisimus.autumn</groupId>
    <artifactId>api</artifactId>
    <version>INSERT_LATEST_VERSION_HERE</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>dev.dementisimus.autumn</groupId>
    <artifactId>plugin</artifactId>
    <version>INSERT_LATEST_VERSION_HERE</version>
    <scope>provided</scope>
</dependency>
```

### Gradle

```
maven {
    name 'dementisimus-repository-release'
    url 'https://repo.dementisimus.dev/release'
}

compileOnly group: 'dev.dementisimus.autumn', name: 'api', version: 'INSERT_LATEST_VERSION_HERE'
compileOnly group: 'dev.dementisimus.autumn', name: 'plugin', version: 'INSERT_LATEST_VERSION_HERE'
```

### Example usage

» Javadocs: [click and replace INSERT_LATEST_VERSION_HERE]

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
[click and replace INSERT_LATEST_VERSION_HERE]: <https://repo.dementisimus.dev/javadoc/release/dev/dementisimus/autumn/api/INSERT_LATEST_VERSION_HERE/raw/index.html>
