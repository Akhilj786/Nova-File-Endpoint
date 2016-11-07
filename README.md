# Nova-File-Endpoint
In order to acheive this task I have used Scala/Play(Activator),PostgreSql,Docker and Slick

# Prequiste:
1. ``` Docker setup``` 
2. ```Activator(PlayFramework) setup``` 
3. ```Add activator and docker to $PATH```.

# Setup:
1) Clone this repo.
2) PostgreSql: Use ``` docker-compose up```

# How to start application
1. ```activator run``` and this starts the application on "9000" port#.

# CURL and File Uploading
For file uploading I have used [multipartFormData](http://stackoverflow.com/a/4073451).After starting the application you can give a call to below curl commands.

1. ###### GET All Meta-Data:
    **curl** http://localhost:9000/

2. ###### Upload a Metadata:
    use http://localhost:9000/files to upload the file.

3. ###### Delete a Metadata: 
   e.g:DELETE  /data/:id   
    **curl** -X DELETE localhost:9000/data/3

4. ###### Update a Metadata:
   e.g: PUT    /data/:id     
    **curl** -X PUT -H 'Content-Type: application/json' -d '{"id":3,"Name": "Testing","ContentType":".html" }' localhost:9000/data/3

5. ###### GET a Metadata: 
   e.g:GET  /data/:id   
    **curl** -X GET localhost:9000/data/3
