# DSpot GitHub App

This is a GitHub App that amplify the unit tests on a push on GitHub

## Getting started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

There must be these applications installed on the local machine:

* Java
* Maven
* git client

### Installing

To install this application you must get a copy of the project from GitHub:

```
https://github.com/STAMP-project/dspot-github-app
```

Then you need to compile it:

```
mvn clean package -DskipTests
```

### Run

To execute the application you must run this:

```
java -jar target/dspot-github-app.jar
```

#### Run it under corporate proxy

This application expose a REST service that should be called from GitHub so, if you are behind a corporate proxy, you must bypass it.
easiest way is to use some service like ngrok. You have to register to it and the you can run it:

```
./ngrok http 3000
```

In this way you expose the local port 3000 through the URL that ngrok will give you.
