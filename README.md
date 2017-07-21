Spring Boot Bluemix producer sample
===================================

What is this?
-------------
This is a Spring Boot project using Spring Kafka support to send messages to Bluemix Message Hub.

How do I run it?  
---------------
You will need to set the user and password properties in order for authentication to proceed. You can do this by setting the values in the application.properties file or by giving runtime arguments to the JVM (e.g. -Duser=abcxyz -Dpassword=123456). For testing within STS I override these properties in the run configuration. You may also need to change the bootstrap servers property but so far all of the Message Hub configurations that I've seen use the same servers.