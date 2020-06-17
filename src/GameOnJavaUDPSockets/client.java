package GameOnJavaUDPSockets;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class client implements ActionListener {
    
    static JFrame frame = null;
    JButton b = null;
    static JButton end = null;
    JButton start = null;
    Container con = null;
    DatagramSocket ds = null;
    DatagramPacket dp = null;
    static int missHit = 0;
    static int hits = -1;
    static boolean flag = true;
    
    public client() throws SocketException   // frame and start button initialisation
    {
      frame = new JFrame("Client");
      frame.setBounds(200,50,900,600);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      frame.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e)
      {
      missHit += 1;
      }
      });
      
      con = frame.getContentPane();
      con.setLayout(null);
      con.setBackground(java.awt.Color.darkGray);
      
      start = new JButton("ENTER GAME");
      start.setBounds(350, 250, 160, 40);
      start.setBackground(java.awt.Color.cyan);
      con.add(start);
      start.addActionListener(this);
      
      end = new JButton("DISCONNECT");
      end.setBounds(350, 250, 160, 40);
      end.setBackground(java.awt.Color.red);
      con.add(end);
      end.setVisible(false);
      end.addActionListener(this);
      
      ds = new DatagramSocket(3000);   // 3000 is client port to receive x&y co-ordinates from server , this socket is to receive x&y co-ordinates from server 
                                    
    }
    
    public void clientReceive() throws UnknownHostException, IOException
    {
        while(flag)
        {
            
            byte[] buff = new byte[1024];
            dp = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), 4321);
            ds.receive(dp);  // receiving x&y co-ordinates from server 

            String s = new String(buff, 0, dp.getLength());
            String cols[] = s.split(" ");

            int x = Integer.parseInt(cols[0]);
            int y = Integer.parseInt(cols[1]);
  
            b = new JButton();
            con.add(b);
            b.setSize(20, 20);
            b.setLocation(x, y);
            b.setBackground(java.awt.Color.YELLOW);
            b.addActionListener(this);
            
            
        }   
        
        
        
    }
    


    public void sendServerStart(String s) throws SocketException, UnknownHostException, IOException
    {
        DatagramSocket d = null;
        DatagramPacket datap = null;
 
        d = new DatagramSocket();  // this socket is to send "start" message to server
        
        byte[] buffer = s.getBytes();   // just a string "start" to inform server

        datap = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), 5959);
        d.send(datap);

    }
    
    public void actionPerformed(ActionEvent e) 
    {
        String str = "start";
        
        if(e.getActionCommand().equals("DISCONNECT"))
            str = "end";
        
        try 
        {
            sendServerStart(str);   //  requesting server to send next button co-ordinates
        } 
        catch (UnknownHostException ex) 
        {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hits += 1;
        removeButton(e);  
    }
    
    
    public void removeButton(ActionEvent e)   // to remove buttons onClick()
    {
        JButton btn = (JButton)e.getSource();
        Container parent = btn.getParent();
        parent.remove(btn);
        parent.revalidate();
        parent.repaint();
    }
    
    public static void main(String[] args) throws IOException
    { 
       Runnable runnable = new timerClass();
       Thread th = new Thread(runnable);
       th.start();  // to run this game for certain amount of time
       
       client cl = new client();       // to initialise frame and start button
       cl.clientReceive();            //  to receive x-y-co-ordinates from server
        
    }
  
}


class timerClass implements Runnable
{

    public void run()
    {
        try 
        {
            Thread.sleep(10000);   // here we run this game for 10 seconds
        } 
        catch (InterruptedException ex)
        {
            Logger.getLogger(timerClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        client.flag = false;  //  to stop listening to server
        JOptionPane.showMessageDialog(null, "NO OF HITS  " + client.hits + "\nNO OF MISS HITS " + client.missHit);
        client.end.setVisible(true);  // to disconnect with server
        
    }
    
}