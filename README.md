# Ansible Demo for Java Forum Stuttgart 2017
Demoprojekt für unseren Vortrag "Lohnt sich der Steinige Weg zum automatisiertem Deployment", den wir auf dem Java Forum Stuttgart am 6.7.2017 gehalten haben.

## Vorbedingungen
Die Demoumgebung wurde mit Vagrant umgesetzt, um sie in Betrieb zu nehmen muss auf dem Hostsystem Vagrant (https://www.vagrantup.com/) installiert sein.

## Inbetriebnahme
Projekt auschecken, in das Projektverzeichnis navigieren und folgenden Befehl ausführen

```bash
vagrant up
```

## Beispiele für AdHoc Kommandos

Hostnamen der Maschinen abrufen:
```bash
$ ansible all -a "hostname"
```

HTOP auf dem Datenbankserver installieren:
```bash
$ ansible db -s -m apt -a "name=htop state=present"
```

Datei auf den Webservern anlegen:
```bash
$ ansible app -m file -a "path=/tmp/jfs2017 state=touch"
```

## Testsystem mit ansible provisionieren
Für die erfolgreiche Ausführung wird eine Internetverbindung benötigt, da u.A. MySQL über das mysql Modul installiert wird, welches eine Internetverbindung benötigt. Für den Play wird das default Inventory `/etc/ansible/hosts` herangezogen.

```bash
$ cd jfs2017/ansible
$ ansible-playbook playbook.yml
```

## Demoservice Aufrufe
Nach der Installation lässt sich der Service mit folgenden Beispielbefehlen aufrufen:

Alle Todos abrufen:
```bash
$ wget -qO- 192.168.60.4:8080/all
```
Todo hinzufügen:
```bash
$ wget --post-data=test 192.168.60.4:8080/todo1
```