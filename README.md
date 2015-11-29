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
The reason is to save requests to github. There are possibilities to fix that, but that's not implemented yet.

### Start application

* Application is the main file.
* for setting members list, set -DgithubReaderService.membersResource=file://<YOURPATH>
* to get connection to your neo4j database, set password: -Dneo4j.password=<YOURPASSWORD>




