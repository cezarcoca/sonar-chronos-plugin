#!/bin/bash

# property sonarUrl is optional. Default value is http://localhost:9000
mvn package org.codehaus.sonar:sonar-dev-maven-plugin::upload -DsonarHome=/Users/cezarcoca/tools/sonar/sonarqube-6.2 -DsonarUrl=http://localhost:9000
