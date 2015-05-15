
Overview
========

The Spring Payment Services project on git.springsource.org contains several repositories. 
All instructions here are relative to the current directory and assume you have the source code 
for each repository under a common directory so you might see a listing like this:

$ ls -l
drwxr-xr-x 6 rossen rossen 4096 2010-10-13 11:03 insight-plugin
drwxr-xr-x 4 rossen rossen 4096 2010-10-14 01:41 samples
drwxr-xr-x 7 rossen rossen 4096 2010-10-13 11:03 spring-mapper
drwxr-xr-x 9 rossen rossen 4096 2010-10-14 11:27 spring-payment

The spring-payment project can be built with Java 1.5 and Maven 2.2.1. However the samples/travel
project requires Java 1.6 due to its current use of Spring Social to send status messages to 
Twitter. This requirement is expected to drop to Java 1.5 some time soon.


Installing Required Dependencies
================================

Before you can build and run sample code you'll need a few dependencies in your local Maven 
repository. Follow the steps below to install them.

1. CyberSource client dependencies

Download the CyberSource Simple Order API SDK from:
http://www.cybersource.com/support_center/implementation/downloads/simple_order/matrix.html

Unzip to a location of your choice, for example ~/dev/cybs.

Install the CyberSource jars in your local Maven repository 

mvn install:install-file -DgroupId=com.cybersource -DartifactId=cybersource-client -Dversion=1.5 -Dpackaging=jar -Dfile=~/cybs/lib/cybsclients15.jar
mvn install:install-file -DgroupId=com.cybersource -DartifactId=cybersource-security -Dversion=1.5 -Dpackaging=jar -Dfile=~/cybs/lib/cybssecurity.jar

2. Spring Mapper

cd ../spring-mapper
mvn install

3. Spring Payment dependencies

cd ../spring-payment
mvn install


Building the Travel Sample
==========================

You're now ready to build the Spring Travel Sample (double-check 'java -version' shows 1.6).

cd ../samples
mvn package

You can deploy the .war file in travel/target to a servlet container or follow the next step
on importing the projects into an IDE.


Importing Into Eclipse (or other IDE)
=====================================

All projects come with Eclipse settings. In addition to having all required dependnecies in your 
local Maven repository as described in the sections above, you'll also need to ensure an M2_REPO 
variable is defined in your Eclipse preferences (Java - Build Path - Classpath Variables) before 
importing the projects. 

This M2_REPO variable should point to your local Maven repository (typically ~/.m2/repository). 
Note that if using the SpringSource Tool Suite or (m2eclipse) that variable will already exist.

It is also recommended that you have Java 1.6 library installed in your Eclipse preferences 
(Java - Installed JREs) as this is needed for the Travel sample project.

Be sure to check that your Servlet container is configured to run with Java 1.6. Double-click
the server in the Servers view and click the Runtime Environment link.

Maven is supported in all major IDEs so you should be able to get up to work with other IDEs.


CyberSource Integration Tests
=============================

The tests in all projects except client-cybs-int-tests can be run completely offline. 
The integration tests in client-cybs-int-tests require that you register with CyberSource for a
merchant account and generate a security key. For more information see the instructions in
client-cybs-int-tests/readme.txt for more information.


