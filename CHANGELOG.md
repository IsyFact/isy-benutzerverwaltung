# 1.6.0
- `IFS-458`: Alle pom.xml Dateien mit tidy:pom formatiert und getestet.
- `IFS 461`: Update Hibernate-Validator auf `5.4.3.Final`
    * Dependency `org.glassfish:javax.el:3.0.0` in Parent-POM verschoben, da von allen Modulen zum Testen benötigt
- `IFS-468`: Update Dozer auf `6.5.0`
    *  Neue Option `wildcard-case-insensitive` löst Case-Sensitive-Problem
    *  GroupID wurde auf `com.github.dozermapper` umgestellt