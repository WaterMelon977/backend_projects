# GitHub Trending CLI

A Java CLI tool that fetches trending GitHub repositories using the GitHub REST API.

## Project URL
https://roadmap.sh/projects/github-trending-cli

## Features
- Filter by programming language
- Sort repositories by stars
- Configurable limit and duration
- Clean CLI output

## Architecture
- **Language**: Java 25
- **Networking**: `java.net.http.HttpClient` (Native Java HTTP Client)
- **JSON Parsing**: Jackson Databind for efficient deserialization of GitHub API responses
- **Build System**: Maven
- **Packaging**: `maven-shade-plugin` to generate an executable "fat-jar" with all dependencies bundled

## Run

### 1. Build the project
```bash
mvn clean package -DskipTests
```

### 2. Run the JAR
```bash
java -jar target/git-trending-cli.jar --language java --duration week --limit 5
```

### Arguments
- `--language`: Programming language (e.g., `java`, `python`, `go`)
- `--duration`: Time period (`day`, `week`, `month`, `year`)
- `--limit`: Number of results to display (1-100)
