import java.io.*;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpsServer;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import com.sun.net.httpserver.*;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsExchange;

public class WebServer {
   public void run() {
      System.out.println("Starting up WebServer");
      try {
         // set up the socket address
         InetSocketAddress Inet_Address = new InetSocketAddress(9000);
         
         //initialize the HTTPS server
         HttpsServer HTTPS_Server = HttpsServer.create(Inet_Address, 0);
         SSLContext SSL_Context = SSLContext.getInstance("TLS");
         
         // initialise the keystore
         char[] Password = "password".toCharArray();
         KeyStore Key_Store = KeyStore.getInstance("JKS");
         FileInputStream Input_Stream = new FileInputStream("src/testkey.jks");
         Key_Store.load(Input_Stream, Password);
         
         // set up the key manager factory
         KeyManagerFactory Key_Manager = KeyManagerFactory.getInstance("SunX509");
         Key_Manager.init(Key_Store, Password);
         
         // set up the trust manager factory
         TrustManagerFactory Trust_Manager = TrustManagerFactory.getInstance("SunX509");
         Trust_Manager.init(Key_Store);
         
         // set up the HTTPS context and parameters
         SSL_Context.init(Key_Manager.getKeyManagers(), Trust_Manager.getTrustManagers(), null);
         HTTPS_Server.setHttpsConfigurator(new HttpsConfigurator(SSL_Context) {
            public void configure(HttpsParameters params) {
               try {
                  // initialise the SSL context
                  SSLContext SSL_Context = getSSLContext();
                  SSLEngine SSL_Engine = SSL_Context.createSSLEngine();
                  params.setNeedClientAuth(false);
                  params.setCipherSuites(SSL_Engine.getEnabledCipherSuites());
                  params.setProtocols(SSL_Engine.getEnabledProtocols());
                  
                  // Set the SSL parameters
                  SSLParameters SSL_Parameters = SSL_Context.getSupportedSSLParameters();
                  params.setSSLParameters(SSL_Parameters);
                  System.out.println("The HTTPS server is connected");
                  
               } catch (Exception ex) {
                  System.out.println("Failed to create the HTTPS port");
               }
            }
         });
         HTTPS_Server.createContext("/", new RootHandler());
         HTTPS_Server.setExecutor(null); // creates a default executor
         HTTPS_Server.start();
         
      } catch (Exception exception) {
         System.out.println("Failed to create HTTPS server on port " + 9000 + " of localhost");
         exception.printStackTrace();
         
      }
   }
   
}