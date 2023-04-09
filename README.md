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
docker-compose up --build
```
4. Once the containers have started, you can access the application at the **`URL`** **`http://{IP_ADDRESS}:{PORT}`**. By default **`URL`** is **`http://localhost:8080`**.

## Usage

### Uploading Objects
To upload an object, send a  request to **`URL/upload`**.

Use the form-data format for the request body and include the file that is desired to upload as the value for the *file* key. Maximum size allowed to upload is set to 10MB.

Example usage:
```curl
curl "URL/upload" --form "file=@path/to/your/file"
```

### Listing Objects
To list all objects in the storage service, send a request to **`URL/objects`**.

Example usage:
```curl
curl "URL/objects"
```

### Downloading Objects
To download an object, you can either send a request to **`URL/objects/{object}`**, where `{object}` is the name of the object you want to download, or simply visit the page **`URL/objects/{object}`** in your web browser.

If you choose to download the object using a `curl` command, you can specify the desired output object name and extension type using the  **`--output {objectname}.{extension}`** flag, where `{objectname}`  is the desired name of the downloaded object and  `{extension}` is the file extension.

Example usage:
```curl
curl "URL/objects/{object}"  --output {objectname}.{extension}
```

Make sure to replace `{object}`, `{objectname}`, and `{extension}` with the actual names and extensions you want to use.

### Deleting Objects
To delete an object, send a **`DELETE`** request to **`URL/delete/{object}`**, where `{object}` is the name of the object you want to delete.

Example usage:
```curl
curl -X DELETE "URL/delete/{object}"
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
- Adding Swagger documentation for easier API reference and testing.
- Implementing user authentication and user specific bucket.
- Implementing AWS Elastic Container Service and enabling auto-scaling.
- Implementing Infrastructure as Code and enabling easier management by using AWS CloudFormation.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
