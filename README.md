# Java Intensive Webinar (2020-08-10)

A multithread program that takes desktop screenshots in background and send them to the Dropbox server.

## Program of webinar

- Exception Handling.
- Threads and Processes.
- Starting and stopping streams.
- Connecting external libraries.
- Sending files to a remote server.

### Homework task
- The program should start a thread that takes a screenshot every 5 seconds
- After obtaining the screenshot a new separate thread should upload the image to the Dropbox server.
- The uploading of images have to be in dedicated thread 
- File names must be in the format <yyyyMMdd_HHmmss>.png

### Installation
- Obtain your own DropBox Access Token
- Rename app-examples.properties to app.properties
- Copy your token to app.properties file
- Tune any other properties like interval (in milliseconds), formatter or image type
