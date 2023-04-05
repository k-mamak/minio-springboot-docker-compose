# Object Storage Application

This is an application for managing objects in an object storage service. It allows users to upload, download, delete, and get list of objects using HTTP requests.

## Prerequisites

Before you can run this application, you will need to have the following installed on your machine:

- Docker
- Docker Compose

## Getting Started
To get started, follow these steps:

1. Clone the repository to your local machine.

2. To specify environment variables, create a .env file in the root directory of the project and set the values for `{IP_ADDRESS}` and `{PORT}`.

3. Run the following command to start the object storage service and the application:

```cmd 
docker-compose up
```
4. Once the containers have started, you can access the application at the **`URL`** **`http://{IP_ADDRESS}:{PORT}`**.

## Usage

### Uploading Objects
To upload an object, send a POST request to **`URL/upload`**.

Use the form-data format for the request body and include the file that is desired to upload as the value for the *file* key.

Example usage:
```curl
curl --location 'URL/upload' --form 'file=@"/path/to/your/file"'
```
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

## Summary

![Diagram](../assets/diagram.png?raw=true)

The application uses the Factory pattern to create an instance of the *ObjectStorageService* component. The Factory pattern is a creational pattern that provides an interface for creating object.

We have a *ObjectStorageServiceFactory* class that creates an instance of the *ObjectStorageService* component based on the configuration specified in the `.env` file. The *ObjectStorageService* component is then used in *ObjectStorageController* class to handle object management requests.

Additional object storage services can be easily implemented if needed. *ObjectStorageServiceFactory* allows for the creation of different *ObjectStorageService* components depending on the needs of the application. Therefore, the application can be easily extended to support multiple object storage services without major changes to the existing codebase.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
