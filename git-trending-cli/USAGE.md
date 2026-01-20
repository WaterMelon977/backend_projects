mvn compile exec:java "-Dexec.args=--language <lang> --duration <period> --limit <number>"
```

mvn compile exec:java "-Dexec.args=--language java --duration month --limit 10"
mvn compile exec:java "-Dexec.args=--language python --duration day --limit 3"

mvn compile exec:java "-Dexec.args=--language javascript --duration year --limit 5"
```