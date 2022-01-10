# Pokedox

**How to run**


Clone the Pokedox project then
open intelliJ idea and clone using VCS, Paste the link gotten from the cloning
Ensure to install _java_ JDK 1.8
Install _maven_
the dependencies will be downloaded automatically, and the project should be ready to
run but if that's not the case then kindly run the `mvn install` command in the directory of the project where you can
find the _pom.xml_ file.

**Translation**

The translation decision is made using a _chain of command pattern_.

**Retries**

Retries are executed twice, except the rate limit has been hit, this sufficiently caters for intermittent access denied exception .

**Caching**

All unique pokemon requests are cached to improve performance and evicted after a translation of the pokemon has been
made to prevent returning a translated pokemon on a non translated endpoint

**Testing**

The application can be tested by running the _mvn test_ command in the project root directory.

**Api Documentation**

The api Documentation can be found on the swagger link at http://localhost:5000/swagger-ui/
you can find details about the application like health by executing the actuator endpoint which you'll find on swagger.

**Containerization**

I added a docker file for app containerization please run a maven install in the root directory _"mvn install"_
Next run a docker build : "docker build -t pokedox-application.jar ."
And Finally : "docker run -p 5000:5000 pokedox-application.jar"