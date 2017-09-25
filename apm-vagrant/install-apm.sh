#!/bin/bash
echo '====================> APM application installation started <===================='

apt install maven -y
cd /vagrant/apm-application
mvn clean install
cd automated-personalized-mailer-web/target/
cp automated-personalized-mailer-web-1.0-SNAPSHOT.war /opt/wildfly/standalone/deployments/
cd /vagrant/apm-application
mvn clean