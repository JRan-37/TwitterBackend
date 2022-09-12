# Twitter Backend API

Twitter Backend API is a backend spring boot application that interfaces with twitter's
Java SDK API and stores requested data in a connected MySQL database.

* **User Account Service** and **JWT Token Authentication** support.
* **Maven** based project.

## Uses

* Aggregate user data for analysis
* Manual twitter post/response from 3rd party website/application (work in progress)
* AI Training Data Collection
* AI Auto-Response (work in progress)

## How It Works

1. Launches a Spring Boot service that connects to the database specified in the applications.properties file.
2. Run user commands via HTTP Endpoints.
3. Interfaces with Twitter's Java SDK API to gather data.
4. Saves data to database if any exists.


## Usage
Endpoints:
* POST /tweets/by-user/{id} - Add twitter posts from a user to database
* GET /tweets/by-user/{id} - Return twitter posts saved in database by user
* POST /tweets/new-post - Post a new tweet
* POST /tweets/comment - Post a comment on a tweet