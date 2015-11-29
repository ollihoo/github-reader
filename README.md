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

* Application is the main file.
* for setting members list, set -DgithubReaderService.membersResource=file://[YOUR_PATH]
* to get connection to your neo4j database, set password: -Dneo4j.password=[YOUR_PASSWORD]
* set your access token by using -Dgithub.accessToken=[YOUR_ACCESS_TOKEN]

### Fill database

Simply go to this page: http://localhost:8080/internal/getdata

### Get a list of all members in given organization

http://localhost:8080/employees

### Search for a certain language

http://localhost:8080/language



