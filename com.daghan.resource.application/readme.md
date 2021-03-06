# 


${Bundle-Description}

## Starting the application 

### Starting on remote raspberry pi
use the debugRemote.bndrun to run it on the remote raspberryPi. You need to start remote agent on the raspberry pi. 

Login to you raspberry pi using ssh (default password for pi is raspberry)
$ ssh pi@10.1.1.9
Obviously you have to change the IP number to the one you are using. And than run the osgi agent on the raspberry pi
$ sudo bndremote -a (See [2] below)

Go back to your eclipse and please change the -runremote configuration in debugRemote.bndrun based on your raspberryPi address e.g.

-runremote: \
	pi; \
		jdb=1044; \
		host=10.1.1.9; \
		shell=-1
 
right click on debugRemote.bndrun and select debug as -> BND native launcher

### Starting on the local machine
right click on debug.bndrun and select debug as -> Bnd OSGi run launcher

## References

1- Add our ssh key to raspberry so you dont need to write the password all the time
http://www.howtogeek.com/168147/add-public-ssh-key-to-remote-server-in-a-single-command/
2- Install jpm (instructions taken from http://enroute.osgi.org/tutorial_iot/100-prerequisites.html)
$ curl https://bndtools.ci.cloudbees.com/job/bnd.master/719/artifact/dist/bundles/biz.aQute.jpm.run/biz.aQute.jpm.run-3.0.0.jar>jpm.jar
$ sudo java -jar jpm.jar init
$ jpm version

Install remote debug agent
$ sudo jpm install -f biz.aQute.remote.main@*
3 - installing firewall on raspberrypi
sudo apt-get install ufw
a - disable sudo ufw disable
b- open a port
 sudo ufw default allow incoming
 sudo ufw default allow outgoing
 sudo ufw allow 1044/tcp