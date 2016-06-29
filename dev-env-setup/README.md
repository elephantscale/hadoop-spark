# Dev Environment Setup

We will be using the following :
* VirtualBox
* Hortonworks Sandbox

## Virtual Box
* Download Virtual Box from : https://www.virtualbox.org/
* Install VirtualBox

## Hortonworks Sandbox
* Download the Sandbox from : http://hortonworks.com/products/sandbox/
* Unpack the sandbox, and double-click on the OVA file.  It would be opened by VirtualBox
* Start the sandbox

## Accessing Sandbox
* In the web browser go to : http://127.0.0.1:8888  
* And follow the instructions
* From ssh client try the following
    - hostname : 127.0.0.1
    - port : 2222
    - user : root
    - password : (default is hadoop)
* If you are using command line ssh client you can do the following:
```
    $     ssh  -l  root   -p 2222   127.0.0.1
```
