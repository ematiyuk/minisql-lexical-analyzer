# MiniSQL-Lexical-Analyzer
This is a lexical analyzer of SQL queries.

#### About

The lexical analyzer takes input SQL query string, partition it into substrings (lexemes) and then identifies the token class for each lexeme using either regular expression or finite automaton check.

*Note:* As of now it is supported analysing of reduced SELECT and SELECT UNION queries only.

#### How to run

1. Download the latest [release](https://github.com/ematiyuk/minisql-lexical-analyzer/releases) 
2. Extract binaries from .zip archive
3. Type the following in the command line:

```
java -jar "MiniSQL-Lexical-Analyzer.jar" 
```
