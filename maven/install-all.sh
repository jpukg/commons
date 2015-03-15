#!/bin/sh
mvn install -f samenea-projects-pom.xml
mvn install -f samenea-repository-pom.xml
mvn install -f samenea-model-pom.xml
mvn install -f samenea-service-pom.xml
mvn install -f samenea-web-pom.xml
