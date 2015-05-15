
Overivew
========

This module contains integration tests that run against live CyberSource test servers. 
To run the tests you will need to register for a CyberSource test account, generate
a security key, and update the pom.xml with your registration details. 

You will also need CyberSource client dependencies installed manually in your local
Maven repository. See ../client-cybs/readme.txt for details.

Note that the tests can be executed from inside Eclipse but you will need to run them
at least once from the command line in order for Maven to apply filtering to the
src/test/resources/cybersource.properties and place it under target/test-classes.


STEP-BY-STEP INSTRUCTIONS
=========================

1. Register for CyberSource test account at:

https://apps.cybersource.com/cgi-bin/register/reg_form.pl

2. Generate a security key 

See Appendix A of the "CyberSource Simple Order API Client for Java":
http://apps.cybersource.com/library/documentation/dev_guides/Web_Services_Clients_Java/html/app_key_generation.htm

3. Update the properties section in the pom.xml of this project:

<cybs.merchantID>...you merchant key here...</cybs.merchantID>
<cybs.keysDirectory>...the directory that contains your merchant security key...</cybs.keysDirectory>

4. Run the tests from the command line:

# if running from this project
mvn test

# if running from the root directory of the spring-payment repository
mvn -P integration test
 
