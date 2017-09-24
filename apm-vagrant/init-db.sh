#!/bin/bash
su - postgres -c "psql -f /vagrant/apm-vagrant/db_init.sql"

cd /opt/wildfly/bin
sh jboss-cli.sh --connect -u=admin -p=@dM1n --commands="data-source add --name=RecipientDS --driver-name=postgresql-42.1.4.jar --connection-url=jdbc:postgresql://localhost:5432/postgres --jndi-name=java:jboss/jdbc/RecipientDS --user-name=postgres --password=postgres --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000,data-source enable --name=RecipientDS,exit"
