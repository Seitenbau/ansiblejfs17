# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  # General Vagrant VM configuration.
  config.vm.box = "debian/wheezy64"

  # ssh setup
  config.ssh.insert_key = false # prevent generation of random keys, replacing provided unsecure keys
  config.ssh.private_key_path = ["files/id_rsa", "files/insecure_private_key"] # use the following private keys
  config.vm.provision "file", source: "files/id_rsa.pub", destination: "~/.ssh/authorized_keys" # push 

  config.vm.synced_folder "./files", "/vagrant", disabled: true
  config.vm.provider :virtualbox do |v|
    v.memory = 256
    v.linked_clone = true
  end

   # Ansible control server
  config.vm.define "ansible", primary:true do |ansible|
    ansible.vm.hostname = "sb-ansible.dev"
    ansible.vm.network "private_network", ip: "192.168.60.3"
    
    # provision private key for inter vm ssh communication
    ansible.vm.provision "file", source: "files/id_rsa", destination: "/home/vagrant/.ssh/id_rsa"
    ansible.vm.provision :shell, inline: <<-SHELL
      chmod 700 /home/vagrant/.ssh
      chown -R vagrant /home/vagrant/.ssh
      chmod 600 /home/vagrant/.ssh/id_rsa
      sudo service ssh restart
    SHELL

    # prevent StrictHostKeyChecking (unsecure, only advisable for demo purposes)
    ansible.vm.provision "file", source: "files/ssh_config", destination: "/home/vagrant/.ssh/config"

    # install ansible
    ansible.vm.provision :shell, inline: <<-SHELL
      apt-get update
      apt-get install -y build-essential libssl-dev libffi-dev python-dev
      apt-get install -y python-pip
      pip install ansible
    SHELL

    # provision inventory file
    ansible.vm.provision "file", source: "files/hosts", destination: "/tmp/hosts"
    ansible.vm.provision :shell, inline: <<-SHELL
      mkdir /etc/ansible
      mv /tmp/hosts /etc/ansible/hosts
    SHELL

    # provision demo service
    ansible.vm.provision "file", source: "files/todoservice/target/todoservice.jar", destination: "/home/vagrant/jfs2017/todoservice.jar"

    # provision demo playbook 
    ansible.vm.provision "file", source: "files/ansible", destination: "/home/vagrant/jfs2017/ansible"

  end # Ansible control server

  # Application server 1
  config.vm.define "app1" do |app1|
    app1.vm.hostname = "sb-app1.dev"
    app1.vm.network "private_network", ip: "192.168.60.4"
  end # Application server 1

  # Application server 2
  config.vm.define "app2" do |app2|
    app2.vm.hostname = "sb-app2.dev"
    app2.vm.network "private_network", ip: "192.168.60.5"
  end # Application server 2

  # Database server
  config.vm.define "db" do |db|
    db.vm.hostname = "sb-db.dev"
    db.vm.network "private_network", ip: "192.168.60.6"
  end # Database server
end