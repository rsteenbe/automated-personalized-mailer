
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/xenial64"
  
  config.vm.network 'forwarded_port', guest: 8080, host: 8080  
  config.vm.network 'forwarded_port', guest: 9990, host: 9990
  
  config.vm.provision "shell", path: "apm-vagrant/install-java.sh"
  config.vm.provision "shell", path: "apm-vagrant/install-wildfly.sh"
  config.vm.provision "shell", path: "apm-vagrant/install-postgres.sh"
  config.vm.provision "shell", path: "apm-vagrant/init-db.sh"
  config.vm.provision "shell", path: "apm-vagrant/install-apm.sh"
end
