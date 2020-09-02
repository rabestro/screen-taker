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
- Copy your token to `screentaker.properties` file
- Tune any other properties like interval (in milliseconds), formatter or image type
- Tune logging properties in `logging.properties` file. 

### Run the program

Open the terminal and type a command:
```
java -jar ScreenTaker-1.1.0.jar 
``` 
If you prefer to run the program in the background (linux) add an ampersand in the end:
```
java -jar ScreenTaker-1.1.0.jar &
```
