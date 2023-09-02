import java.io.IOException;

public class MyFirstWebServer {
   public void start() throws IOException {
      System.out.println("Hello!");
      
      WebServer webserver = new WebServer();
      webserver.run();
   }
}
