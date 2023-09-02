import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsExchange;

import java.io.IOException;
import java.io.OutputStream;

public class RootHandler implements HttpHandler {
   @Override
   public void handle(HttpExchange x) throws IOException {
      String Response = "This is the response from delftstack";
      HttpsExchange HTTPS_Exchange = (HttpsExchange) x;
      x.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
      x.sendResponseHeaders(200, Response.getBytes().length);
      OutputStream Output_Stream = x.getResponseBody();
      Output_Stream.write(Response.getBytes());
      Output_Stream.close();
   }
}
