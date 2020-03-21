# BPM Project prototype

# Get it
Clone this github repository https://github.com/bpmetc20/ftd-bpm.git
```sh
$ git clone https://github.com/bpmetc20/ftd-bpm.git
```

# Build it
Install (if neccessary) [maven][maven-download].
Here's how to build the application.
```sh
$ cd ftd-bpm
$ mvn clean install
$ mvn package spring-boot:repackage
```
The resulting jar file target\bpm-0.0.1-SNAPSHOT.jar will be in the target directory

# Run it
```sh
$ java -jar target/bpm-0.0.1-SNAPSHOT.jar
```
# Use it
#####List deployments (bpm engine instances)
```sh
GET http://localhost:8091/process-api/repository/deployments
```
#####List definitions (bpm flows)
```sh
GET http://localhost:8091/process-api/repository/process-definitions
```
#####Get  definition content (XML format)
```sh
GET http://localhost:8091/process-api/repository/process-definitions/{definition-id}/resourcedata
```
#####Create process instance
```sh
PUT http://localhost:8091/process-api/runtime/process-instances

Body:
{
   "processDefinitionId":"ftd-project:1:24c66b32-6af0-11ea-812d-5c879cfa439e",
   "businessKey":"",
   "returnVariables":true,
   "variables": [
      {
        "name":"accepted",
        "value":false
      },
      {
        "name":"negotiated",
        "value":false
      }
   ]
}

processDefinitionId - as defined in response from List definitions (http://localhost:8091/process-api/repository/process-definitions) 
```
#####List process instances
```sh
GET http://localhost:8091/process-api/runtime/process-instances
```

#####Show active tasks
```sh
GET http://localhost:8091/process-api/runtime/tasks
```
#####Show task
```sh
GET http://localhost:8091/process-api/runtime/tasks/taskId
taskId - as defined in response from Show active tasks (http://localhost:8091/process-api/runtime/tasks)
```
#####Claim task
```sh
POST http://localhost:8091/process-api/runtime/tasks/{task-id}
taskId - as defined in response from Show active tasks (http://localhost:8091/process-api/runtime/tasks)

Body
{
  "action" : "claim",
  "assignee" : "rest"
}
```
#####Complete task
```sh
POST http://localhost:8091/process-api/runtime/tasks/{task-id}
taskId - as defined in response from Show active tasks (http://localhost:8091/process-api/runtime/tasks)
{
  "action" : "complete",
   "variables": [
      {
        "name":"accepted",
        "value":true
      },
      {
        "name":"negotiated",
        "value":false
      }
	]
}
```
[maven-download]: http://maven.apache.org/install.html
