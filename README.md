# ReportPortal. Tfs Integration Module

This microservice is an alternative approach for integrating TFS as a bug tracking system in ReportPortal.

## Why does this repository exist?

The original [service-tfs](https://github.com/reportportal/service-tfs) microservice has not been updated since August 2017.

The reason for this was explained in the official Slack channel by [Dzmitry Humianiuk](https://github.com/DzmitryHumianiuk):
"*[...] there is no active plans for it. official sdk has memory leak issues and laters update were in 2013.*"

Because of this the official TFS microservice is not compatible with later versions of ReportPortal.

**This** project brings back the TFS microservice in ReportPortal without the deprecated TFS-JDK from Microsoft.

## How does it work?

This microservice delegates each request to a separate TFS RESTful-Web API.
This separate TFS RESTful-Web API can ve written in any programming language that has better support for interactions with TFS than Java.
(e.g. C#, Python, ...)

To configure the URL of the TFS RESTful-Web API you have to set the `RP_TFS_MICROSERVICE_URL` environment variable.
(e.g. `RP_TFS_MICROSERVICE_URL=http://my-tfs-api-domain.tld:8090`)

## Docker image

The docker image is called `bluefu/service-tfs` and can be found [here](https://cloud.docker.com/u/bluefu/repository/docker/bluefu/service-tfs) on DockerHub.

## Example configuration in docker-compose

An example configuration of a `docker-compose.yml` file could look like this:

```yaml
## Add this service to your ReportPortal docker-compose configuration.
  tfs:
    image: bluefu/service-tfs:latest
    environment:
      - RP_PROFILES=docker
      - RP_TFS_MICROSERVICE_URL=http://my-tfs-api-domain.tld:8090
    restart: always
```

## Documentation of separate TFS RESTful-Web API

This is a documentation how you should design your separate TFS RESTful-Web API.

**Hint**: RAML documentation will follow soon.

### General Query Parameters

Each request contains the URI and the project you've configured in the BTS dialogue in ReportPortal as query parameters.

**Query Parameters**

- `uri`: URI of the TFS server (e.g. `https://my-tfs-server.tld/tfs/Example`)
- `project`: Target project on the TFS server (e.g. `myproject`)

### Get Issue Types

**Definition**

`GET /api/issuetypes`

**Response**

- `200 Ok` on success.
- Return: List of `String` objects

### Get Ticket Fields

**Definition**

`GET /api/ticketfields`

**Query Parameters**

- `type`: Issue Type

**Response**

- `200 Ok` on success.
- Return: List of `PostFormField` objects

### Get Ticket by ID

**Definition**

`GET /api/ticket/<id:string>`

**Response**

- `200 Ok` on success.
- Return: `Ticket` object.

### Create Ticket

**Definition**

`POST /api/ticket`

**Request Body**

- `PostTicketRQ` object

**Response**

- `201 Created` on success.
- Return: `Ticket` object.

### Check connection to TFS server

**Definition**

`GET /api/welcome`

**Response**

- `200 Ok` on success.
- `Boolean` object (`true` if connection established, `false` otherwise)
