# Autumn [![main build status](https://github.com/dementisimus/Autumn/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/dementisimus/Autumn) [![develop build status](https://github.com/dementisimus/Autumn/actions/workflows/build.yml/badge.svg?branch=develop)](https://github.com/dementisimus/Autumn/tree/develop)

## _The Core._

**Autumn** is the **core** of all **dev.dementisimus projects**!

## **Requirements**

1. **Java 17**
2. **Spigot 1.20.1** **([PaperMC] recommended)**
3. **Access** to the **server console** (for the **automated setup**)

##### Maven

```xml
<repository>
    <id>dementisimus-dev-release</id>
    <url>https://repo.dementisimus.dev/release/</url>
</repository>

<dependency>
    <groupId>dev.dementisimus</groupId>
    <artifactId>autumn-(module)-api</artifactId>
    <version>INSERT_LATEST_RELEASE_VERSION_HERE</version>
    <scope>provided</scope>
</dependency>
```

##### Gradle

```
maven {
    name 'dementisimus-dev-release'
    url 'https://repo.dementisimus.dev/release/'
}

compileOnly group: 'dev.dementisimus', name: 'autumn-(module)-api', version: 'INSERT_LATEST_RELEASE_VERSION_HERE'
```

## **License**

» [Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]

## find us on

[<img src="https://discordapp.com/assets/e4923594e694a21542a489471ecffa50.svg" alt="" height="55" />](https://discord.gg/sTRg8A7)

# **Happy app creating!**

[planned features]: <https://github.com/dementisimus/Autumn/issues>

[PaperMC]: <https://papermc.io/downloads>

[GitHub Releases]: <https://github.com/dementisimus/Autumn/releases>

[Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License]: <https://creativecommons.org/licenses/by-nc-nd/4.0/>
