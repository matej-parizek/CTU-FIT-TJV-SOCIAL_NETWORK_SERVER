# Semestral Work - Server (Social Network)

## Overview

This project involves a server for a social network application, featuring functionalities for managing user posts, including complex operations such as co-authorship. Users can create posts and set other users as co-authors. When a post is created with a co-author, the post is also created for the co-author with added text. The condition for setting a co-author is that the users must follow each other.

**Additional Query**: Calculate the total number of likes for posts where the user is set as a co-author. (The text will include "coauthor: username".)

<img src="resources.readme/schema.png" alt="schema" width="600px" height="auto">

## Server Information

This server is designed for a social network application and uses Docker for data management. The database is configured in `application.properties` with `create-drop`, meaning data will be deleted after each restart. Authorization is implemented but only at the server-client level; it is not secure, which is sufficient for the application's intended functionality.

## REST API Documentation

- **API Documentation**: [Swagger UI](http://localhost:8090/swagger-ui/index.html#/)

### Quick Overview:

<img src="resources.readme/apiUser.png" alt="api-user" width="1000px">

<img src="resources.readme/apiPost.png" alt="api-post" width="1000px">

<img src="resources.readme/apiComment.png" alt="api-comment" width="1000px">

<img src="resources.readme/apiAuthorization.png" alt="api-authorization" width="1000px">

## Running the Application

### Required Applications

- **API Repository**: [Social Network Server](https://gitlab.fit.cvut.cz/parizmat/social_network_server)
- **Client Repository**: [Social Network Client](https://gitlab.fit.cvut.cz/parizmat/social_network_client)

### Requirements

- JVM
- Gradle
- Docker

### Starting the API

1. Start Docker containers:
    ```bash
    docker-compose up -d
    ```

2. Load images into Docker for the insert script (for images):
    ```bash
    docker cp ./src/main/resources/images/img1 social_network_server-postgres-1:/docker-entrypoint-initdb.d/
    docker cp ./src/main/resources/images/img2 social_network_server-postgres-1:/docker-entrypoint-initdb.d/
    docker cp ./src/main/resources/images/img3 social_network_server-postgres-1:/docker-entrypoint-initdb.d/
    docker cp ./src/main/resources/images/img4 social_network_server-postgres-1:/docker-entrypoint-initdb.d/
    docker cp ./src/main/resources/images/img5 social_network_server-postgres-1:/docker-entrypoint-initdb.d/
    ```

3. Run the application:
    ```bash
    ./gradlew bootRun
    ```

### Running the Client

1. Start the client application:
    ```bash
    ./gradlew bootRun
    ```

## URLs

- **Server**: [http://localhost:8090/](http://localhost:8090/)
- **Client**: [http://localhost:9080/](http://localhost:9080/)
