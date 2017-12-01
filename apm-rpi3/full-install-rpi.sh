#!/bin/bash
echo '=> FULL INSTALLATION OF AUTOMATED-PERSONALIZED-MAILER STARTED <='

echo '=> Installation of chkconfig <='
apt-get install chkconfig -y

echo '=> Installation of Wildfly started <='
echo '====================> Wildfly installation started <===================='
WILDFLY_FILE=/tmp/automated-personalized-mailer/downloads/wildfly-11.0.0.CR1.tar.gz

if [ -f "$WILDFLY_FILE" ]; then
	echo 'Wildfly TGZ found.'
else
	echo 'No Wildfly TGZ found, it will download Wildfly from jboss.org'
	mkdir -p /tmp/automated-personalized-mailer/downloads
	cd /tmp/automated-personalized-mailer/downloads
	echo 'Download started, this process may take a while...'
	wget -q http://download.jboss.org/wildfly/11.0.0.CR1/wildfly-11.0.0.CR1.tar.gz
	echo 'Download finished.'
fi

cd /opt
tar -xzf $WILDFLY_FILE

ln -s /opt/wildfly-11.0.0.CR1 /opt/wildfly

cp /opt/wildfly/docs/contrib/scripts/init.d/wildfly.conf /etc/default/wildfly

cp /opt/wildfly/docs/contrib/scripts/init.d/wildfly-init-debian.sh /etc/init.d/wildfly

chown root:root /etc/init.d/wildfly
chmod +X /etc/init.d/wildfly

update-rc.d wildfly defaults
update-rc.d wildfly enable

mkdir -p /var/log/wildfly

useradd --system --shell /bin/false wildfly

chown -R wildfly:wildfly /opt/wildfly-11.0.0.CR1
chown -R wildfly:wildfly /opt/wildfly
chown -R wildfly:wildfly /var/log/wildfly

cd /opt/wildfly-11.0.0.CR1/standalone/configuration

sed -i s/'127.0.0.1'/'0.0.0.0'/g standalone.xml

systemctl start wildfly.service
systemctl enable wildfly.service
echo 'Wildfly started.'

cd /opt/wildfly/bin
sh add-user.sh -u 'admin' -p '@dM1n'
echo ''
echo 'User "admin" with pass "@dM1n" added to Wildfly Administation Console users'
echo ''

echo '=> Installation of PostgreSQL started <='
#!/bin/bash
echo '====================> Postgres installation started <===================='

echo 'Installing Postgres'
apt-get install postgresql -y

echo 'Installing Postgres Driver to Wildfly'
cd /tmp/automated-personalized-mailer
wget -q https://jdbc.postgresql.org/download/postgresql-42.1.4.jar
cp postgresql-42.1.4.jar /opt/wildfly/standalone/deployments
rm -rf postgresql-42.1.4.jar

echo 'Edit postgres.conf'
cd /etc/postgresql/9.?/main
sed -i s/'#listen_addresses = '/'listen_addresses = '/g postgresql.conf

sed -i '/# IPv4 local connections:/a host    all             all             127.0.0.1/24            trust' pg_hba.conf

echo 'Restarting Postgres'
/etc/init.d/postgresql restart

echo 'Adding datasource to Wildfly'
cd /opt/wildfly/bin
sh jboss-cli.sh --connect -u=admin -p=@dM1n --commands="data-source add --name=ApplicationDS --driver-name=postgresql-42.1.4.jar --connection-url=jdbc:postgresql://localhost:5432/postgres --jndi-name=java:jboss/jdbc/ApplicationDS --user-name=postgres --password=postgres --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000,data-source enable --name=ApplicationDS,exit"

echo '=> Initialization of database <='
#!/bin/bash
su - postgres -c "psql -f /tmp/automated-personalized-mailer/apm-vagrant/db_init.sql"

cd /opt/wildfly/bin
sh jboss-cli.sh --connect -u=admin -p=@dM1n --commands="data-source add --name=RecipientDS --driver-name=postgresql-42.1.4.jar --connection-url=jdbc:postgresql://localhost:5432/postgres --jndi-name=java:jboss/jdbc/RecipientDS --user-name=postgres --password=postgres --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000,data-source enable --name=RecipientDS,exit"

#!/bin/bash
echo '====================> APM application installation started <===================='

apt install maven -y
cd /tmp/automated-personalized-mailer/apm-application
mvn clean install
cd automated-personalized-mailer-web/target/
cp automated-personalized-mailer-web-1.0-SNAPSHOT.war /opt/wildfly/standalone/deployments/
cd /tmp/automated-personalized-mailer/apm-application
mvn clean