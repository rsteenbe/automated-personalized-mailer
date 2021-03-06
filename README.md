# Automated Personalized Mailer

*Author* : [Renco Steenbergen](https://www.linkedin.com/in/renco-steenbergen-87b52a119/)

*Summary* : Java mailing tool that can be used to send multiple e-mails with the same content, including personalized salutations. 

*IDE & Technologies* : IntelliJ IDEA, Notepad++, Maven 3.5.0, JPA 2.0, RESTful Webservices, AngularJS, HTML, CSS, Vagrant, Bash (Shell scripting), Java 8, Wildfly 11, PostgreSQL 9.5

## Purpose

The project to build a personalized mail tool started as a hobby project to get used to working with RESTful webservices, Java persistence and AngularJS. Now the tool can be used by everybody who is interested on an easy to use e-mail tool.

This is a Java mailing tool that can be used to send a personal e-mail to multiple adresses. Vagrant can be used to play around with the application quickly. The application can also be used on a Raspberry Pi as a local application.

Example: If multiple e-mails with the same content need to be sent, one to Joe, one to Chris and one to Daisy. It is nice to send them an e-mail and say 'Hi' without calling them by name, but it makes an e-mail even more personalized when both Joe, Chris and Daisy are called by name. That is what this tool is for!

**CAUTION:** Before using this tool, be sure you are informed about the mailing laws in your country. There are certain rules to sending e-mails for marketing purposes. In the Netherlands it is only allowed to send group mails to Subscribers or existing Customers. Use this tool at your own risk! The owner (Renco) is not responsible for any damage caused to the user of this application.

## Quick Start - Windows 7

* Before running the box download [Vagrant](https://www.vagrantup.com/) and [VirtualBox](https://www.virtualbox.org/wiki/Downloads).


* Now open `smtp.properties` which is located at `${project-dir}/apm-application/automated-personalized-mailer-mail/src/main/resources/`
* Replace the property values within brackets and add your own credentials. Save the file.


* Open the command prompt `cmd.exe` and go to `${project-dir}` (By default `automated-personalized-mailer`)
* Run the following command in the command prompt: `vagrant up --provider=virtualbox`. This process may take a while...
* Enter the following URL in the browser: `http://localhost:8080/apm`
* Enter your name, e-mail and a random category (e.g. Test) and press submit.
* Add another name, your e-mail and use the same category as before.
* Go to the top menu and click on Mailing List, check if the mails are added.
* Go to the top menu again and click on Compose Mail
* Enter the category again, add a Subject and type the following "Hi \<recipient\>!"
* Now check your e-mail and see if it works!

## Quick Start -Raspberry Pi 3

* Before installing the application, be sure the raspberry pi is configured. Follow the [NOOBS Setup](https://www.raspberrypi.org/help/noobs-setup/2/)
and be sure the Raspberry SSH is enabled, follow the [SSH Tutorial](https://www.raspberrypi.org/documentation/remote-access/ssh/)
* Run the following commands: 
`sudo apt-get update`
`sudo apt-get install git-core`

* When everything is configured, go to the temporary folder using the following command `cd /tmp`
* Enter the command `git clone https://github.com/rsteenbe/automated-personalized-mailer.git`

* Now open `smtp.properties` which is located at `/tmp/automated-personalized-mailer/apm-application/automated-personalized-mailer-mail/src/main/resources/`
* Replace the property values within brackets and add your own credentials. Save the file.

* Run the following command in the terminal: `sudo sh /tmp/automated-personalized-mailer/apm-rpi/full-install-rpi3.sh`. This process may take a while...
* Enter `hostname -I` to check the ip address and go to the browser and enter `<ip-address>/apm`

## Import Xlsx Excel files

* After running the quick start installation, go to `http://localhost:8080/apm` and click the 'Import Mailing' tab in the top menu. Browse the excel file `test.xlsx` and click the `Import XLSX` button.

* Now check `http://localhost:8080/apm/#/mailinglist` to see if the e-mails are imported.

## Database Backup

* In case the database needs a back-up, open the command prompt and go to `${project-dir}`
* Be sure the vagrant box is built as written in the Quick Start.
* Enter the following command `vagrant ssh`.
* Now login as postgres user by typing the command `sudo su - postgres`. 
* Go to the `vagrant` dir by typing following command `cd /vagrant`.
* Enter `pg_dumpall > apm_db.sql` to create a backup.

## Database Restore

* Follow the steps from the Database Backup, except the last one.
* Enter `psql postgres < db_file` (For example `psql postgres < apm_db.sql`)