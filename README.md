# Simple-JAVA-client-server-game

Hey Welcome!!!

This is a simple Java Client-Server Game based on UDP

Play like this :
    1. Start the Game
    2. Click on the button to Destroy it....
    3. That's it SIMPLE...

To RUN this first execute Server program and then execute Client program and ENJOY!!!

--------------------------------------------------------------------------------------------

How it works ??

Client Side :
    1. 2 ports are used @client side one for send and other for receive purpose
    2. When client hits "START" button sends reqeust to server for x&y co-ordinates(RANDOM VALUES) to place button
    3. Next each time when client destroys the button by clicking , a request is sent to server for next co-ordinate values
    
Server Side:
    1. 2 ports are used @server side one for send and other for receive purpose
    3. Receives request from client 
    2. Randomly generates values for x&y co-ordinates and send that to client
    
After given amount of time client sends "END" message to server and game ends!!!    
    
    
