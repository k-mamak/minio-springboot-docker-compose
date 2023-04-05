# Object Storage Application

This is an application for managing objects in an object storage service. It allows users to upload, download, list, and delete objects using HTTP requests.

## Prerequisites

Before you can run this application, you will need to have the following installed on your machine:

- Docker
- Docker Compose

## Getting Started
To get started, follow these steps:

1. Clone the repository to your local machine.

2. Run the following command to start the Minio service and the application:

```cmd 
docker-compose up
```
3. Once the containers have started, you can access the application at the **URL** **`http://{IP_ADDRESS}:{PORT}`**, where `{PORT}` is the port number specified in your .env file. If the `{PORT}` is publicly available; `{IP_ADDRESS}` is public IP address, otherwise `{IP_ADDRESS}` is `localhost`.

## Usage

### Uploading Objects
To upload an object, send a POST request to **`URL/objects`** with the following parameters:

- file: The file to upload.

### Downloading Objects
To download an object, send a GET request to **`URL/objects/{object}`**, where `{object}` is the name of the object you want to download.

### Listing Objects
To list all objects in the storage service, send a GET request to **`URL/objects`**.

### Deleting Objects
To delete an object, send a DELETE request to **`URL/delete/{object}`**, where `{object}` is the name of the object you want to delete.

## Technologies Used
- Java
- Spring Boot
- Maven
- Minio

## Design Pattern Used: Factory Pattern

![Diagram](../assets/diagram.png?raw=true)

This application uses the Factory pattern to create an instance of the *ObjectStorageService* component. The Factory pattern is a creational pattern that provides an interface for creating object.

In this application, we have a *ObjectStorageServiceFactory* class that creates an instance of the *ObjectStorageService* component based on the configuration specified in the `.env` file. The *ObjectStorageService* component is then injected into the *ObjectStorageController* class to handle object management requests.

Additional object storage services can be easily implemented if needed. *ObjectStorageServiceFactory* allows for the creation of different *ObjectStorageService* components depending on the needs of the application. Therefore, the application can be easily extended to support multiple object storage services without major changes to the existing codebase.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
