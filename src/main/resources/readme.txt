
Overview
========

Thank you for downloading the Spring Payment Services distribution. As this project is pretty
new you won't find it in any Maven repository. However, all project sources are included in 
the distribution, along with Maven configuration, so you can get up and running in your IDE.

The instructions for building are in projects/spring-payment/readme.txt. However do take
a look at the rest of this file to get a general idea.


Spring Travel Sample
====================

Probably the best place to start exploring. It is a web application that demonstrates use of 
the Spring Payment Services project to make credit card payments at the end of a hotel 
booking process. 

The application can run in two modes. In online mode payment requests are sent to a live 
CyberSource test server. In offline mode (the default) payment requests are matched to 
statically prepared responses from previous interactions with CyberSource test servers. 

The offline mode is the quickest way to get up and running. The online mode is not much harder 
but it requires you to register with CyberSource for a test account (a fairly quick procedure),
generate a security key, and then configure Travel sample to use your credentials. 

The properties for switching between online and offline modes can be found in the Travel sample
(projects/samples/travel) under src/main/resources/application.properties.

Instructions on registering with CyberSource for a test account can be found online and also 
in the readme for the CyberSource Client Integration Tests module 
(projects/spring-payment/client-cybs-int-tests).


Spring Payment Project
======================

In addition to running the sample, you may also wish to look at the Spring Payment source code 
and run the available tests. Once you've followed the build instructions you'll have all projects 
in your IDE. Most tests run in offline mode. The ones that run in online mode are isolated in 
the CyberSource Client Integration Tests module (projects/spring-payment/client-cybs-int-tests). 
See the readme in that module for more details.



