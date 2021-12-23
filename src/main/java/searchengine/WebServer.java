package searchengine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import QueryHandler.QueryHandler;

public class WebServer {
  
  static final int PORT = 8080;
  static final int BACKLOG = 0;
  static final Charset CHARSET = StandardCharsets.UTF_8;
  static final int HTTP_OK = 200;
  static final String MIME_TYPE = "application/json";

  HttpServer server;
  private QueryHandler handler;


  WebServer() throws IOException {
    
    handler = new QueryHandler();
    setupServer();    
    consoleMessage();

  }

  /**
   * Prints out the server status. 
   */
  private void consoleMessage() {

    String msg = " WebServer running on http://localhost:" + PORT + " ";
    System.out.println("╭"+"─".repeat(msg.length())+"╮");
    System.out.println("│"+msg+"│");
    System.out.println("╰"+"─".repeat(msg.length())+"╯");
  }

  /**
   * Creates static content for the website and starts the server.
   * 
   * @throws IOException
   */
  private void setupServer() throws IOException {

    server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
    server.createContext("/", io -> respond(io, "text/html", getFile("web/index.html")));
    server.createContext("/search", io -> doSearch(io));
    server.createContext(
        "/favicon.ico", io -> respond(io, "image/x-icon", getFile("web/favicon.ico")));
    server.createContext(
        "/code.js", io -> respond(io, "application/javascript", getFile("web/code.js")));
    server.createContext(
        "/style.css", io -> respond(io, "text/css", getFile("web/style.css")));
    server.start();
  }  
  
  /** 
   * Hook for the frontend, doing the search.
   * 
   * @param io
   */
  public void doSearch(HttpExchange io) {

    String searchTerm = io.getRequestURI().getRawQuery().split("=")[1];
    byte[] bytes = handler.getResult(searchTerm).getBytes(CHARSET);
    respond(io, "application/json", bytes);
  }
  
  /** 
   * Returns the file content.
   * 
   * @param filename
   * @return file content.
   */
  private byte[] getFile(String filename) {
    try {
      return Files.readAllBytes(Paths.get(filename));
    } catch (IOException e) {
      e.printStackTrace();
      return new byte[0];
    }
  }
  
  /** 
   * Sends the response to the browser.
   * 
   * @param io
   * @param code
   * @param response
   */
  void respond(HttpExchange io, String mimeType, byte[] response) {
    try {
      io.getResponseHeaders()
          .set("Content-Type", String.format("%s; charset=%s", mimeType, CHARSET.name()));
      io.sendResponseHeaders(HTTP_OK, response.length);
      io.getResponseBody().write(response);
    } catch (Exception e) {
    } finally {
      io.close();
    }
  }
  
  /** 
   * @param args
   * @throws IOException
   */
  public static void main(final String... args) throws IOException {
    new WebServer();
  }
}