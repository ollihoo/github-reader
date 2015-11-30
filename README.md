# github-reader
How to get data from github and work on data with neo4J

## Prerequisites

* install neo4j community server; version 2.3.1 (http://neo4j.com/download/)
* start a new database; check it by calling http://localhost:7474
* first time you will ask for a password; save it! You will need it for your personal setup of this application


## Start application

### Fill database

By default, there is a members list loaded by github (https:///api.github.com/orgs/github/members)
I recommend to download this file once and to add this as a source file.
The reason is to save requests to github. If you want to do more than 50 requests per hour,
get your personal access token (see https://developer.github.com/v3/#authentication)

### Start application

* do gradle build
* go into build/libs
* do java -Dneo4j.password=[YOUR_PASSWORD] -jar github-reader-0.0.1-SNAPSHOT.jar

For setting up the list of members, add
* the path to your members list, i.e. -DgithubReaderService.membersResource=file://[YOUR_PATH]
* your access token by using -Dgithub.accessToken=[YOUR_ACCESS_TOKEN]

### Fill database

Simply go to this page: http://localhost:8080/internal/getdata

### Get a list of all members in given organization

http://localhost:8080/employees

### Search for a certain language

http://localhost:8080/language



