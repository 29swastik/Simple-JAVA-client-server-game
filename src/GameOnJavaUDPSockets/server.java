package GameOnJavaUDPSockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;


public class server 
{
    
    DatagramSocket ds = null;       // this port to receive data from client
    DatagramSocket dsoc = null;    // this port to send data to client
    Random r = new Random();
    static boolean flag = true;    // to keep the connection ON and disconnect at the end
    
    public server() throws SocketException
    {
        ds = new DatagramSocket(5959);    
        dsoc = new DatagramSocket(4321);  
        
    }
    
    public void serverAction() throws IOException  // to implement send and receive functions
    {
         
        while(flag)
        {
            String str = "";
            
            str = serverReceive();
            
            if(str.equals("start"))
                serverSend();
            
            else if(str.equals("end"))
                flag = false;
             
        }       
        
        return;

    }   
    
    
    public String serverReceive() throws IOException   // server receives "start" from client on button(start&target button) click
    {
        byte b[] = new byte[1024];
        DatagramPacket d = new DatagramPacket(b, b.length);

        ds.receive(d);
 
        String str = new String(b, 0, d.getLength());

        return(str);
        
    }
    
    public void serverSend() throws UnknownHostException, IOException   // server sends  x&y co-ordinates to client to position button
    {
        int x = r.nextInt(800);
        int y = r.nextInt(500);

        String s = x + " " + y;
        byte buff[] = s.getBytes();
        DatagramPacket dp = null;
        
        dp = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), 3000);   // client receiving x-y-co-ordi @3000 port number
        
        dsoc.send(dp);
    
    }
    
    public static void main(String[] args) throws SocketException, IOException, InterruptedException 
    {
      
      server ser = new server();  // to initialise ports  
      ser.serverAction();         // function to send and receive data

      return;
    }  



}