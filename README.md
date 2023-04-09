# Object Storage Application

This is an application for managing objects in an object storage service. It allows users to upload, download, delete, and get list of objects using HTTP requests.

## Prerequisites

Before you can run this application, you will need to have the following installed on your machine:

- Docker
- Docker Compose

## Getting Started
To get started, follow these steps:

1. Clone the repository to your local machine.

2. To specify properties, edit application.properties file in the project\src\main\resources directory of the project.

3. Run the following command to start the object storage service and the application:

```cmd 
docker-compose up
```
4. Once the containers have started, you can access the application at the **`URL`** **`http://{IP_ADDRESS}:{PORT}`**. By default **`URL`** is **`http://localhost:8080`**.

## Usage

### Uploading Objects
To upload an object, send a  request to **`URL/upload`**.

Use the form-data format for the request body and include the file that is desired to upload as the value for the *file* key.

Example usage:
```curl
curl --location 'URL/upload' --form 'file=@/path/to/your/file'
```
### Downloading Objects
To download an object, send a **`GET`** request to **`URL/objects/{object}`**, where `{object}` is the name of the object you want to download.

Example usage:
```curl
curl 'URL/objects/{object}'
```

### Listing Objects
To list all objects in the storage service, send a **`GET`** request to **`URL/objects`**.

Example usage:
```curl
curl 'URL/objects'
```

### Deleting Objects
To delete an object, send a **`DELETE`** request to **`URL/delete/{object}`**, where `{object}` is the name of the object you want to delete.

Example usage:
```curl
curl --request DELETE 'URL/delete/{object}' 
```

## Technologies Used
- Java
- Spring Boot
- Maven
- Minio

## Class Diagram

![Diagram](../assets/diagram.png?raw=true)

## Future work ideas
- Adding more tests and increase code coverage.
- Implementing user authentication and user specific bucket.
- Implementing AWS Elastic Container Service and enabling auto-scaling.
- Implementing Infrastructure as Code and enabling easier management by using AWS CloudFormation.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
