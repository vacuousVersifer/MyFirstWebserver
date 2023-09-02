import Handlers.*;
import com.sun.net.httpserver.HttpServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class WebServer {
   public String address = "127.0.0.1";
   public int port = 9000;
   
   private Socket socket;
   private DataInputStream input;
   private DataOutputStream out;
   public void run() throws IOException {
      socket = new Socket(address, port);
      System.out.println("Connected");
      
      // takes input from terminal
      input = new DataInputStream(System.in);
      
      // sends output to the socket
      out = new DataOutputStream(socket.getOutputStream());
      
      // string to read message from input
      String line = "";
      
      // keep reading until "Over" is input
      while (!line.equals("Over")) {
         line = input.readLine();
         out.writeUTF(line);
      }
      
      input.close();
      out.close();
      socket.close();
   }
}
