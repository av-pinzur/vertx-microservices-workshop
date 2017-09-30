package io.vertx.workshop.quote;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

import java.util.*;

/**
 * This verticle exposes a HTTP endpoint to retrieve the current / last values of the maker data (quotes).
 *
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class RestQuoteAPIVerticle extends AbstractVerticle {

  private Map<String, JsonObject> quotes = new HashMap<>();

  @Override
  public void start() throws Exception {
    vertx.eventBus().<JsonObject>consumer(GeneratorConfigVerticle.ADDRESS)
        .handler(message -> {
          // TODO Populate the `quotes` map with the received quote
          // Quotes are json objects you can retrieve from the message body
          // The map is structured as follows: name -> quote

          JsonObject quote = message.body();
          quotes.put(quote.getString("name"), quote);
        });


    vertx.createHttpServer()
        .requestHandler(request -> {
          HttpServerResponse response = request.response();
          response.putHeader("content-type", "application/json");

          // TODO
          // The request handler returns a specific quote if the `name` parameter is set, or the whole map if none.
          // To write the response use: `request.response().end(content)`
          // Responses are returned as JSON, so don't forget the "content-type": "application/json" header.
          // If the symbol is set but not found, you should return 404.
          // Once the request handler is set,

          // response
          //     .end(Json.encodePrettily(quotes));

          // ----

          String name = request.getParam("name");
          try {
            Collection<JsonObject> requestedQuotes = (name != null)
                ? Arrays.asList(getSingleQuote(name))
                : quotes.values();
            response.end(Json.encodePrettily(requestedQuotes));
          } catch (NotFoundException e) {
            response.setStatusCode(404);
            response.setStatusMessage("Not Found: Couldn't locate a quote with the specified name.");
            response.end();
          }

          // ----
        })
        .listen(config().getInteger("http.port"), ar -> {
          if (ar.succeeded()) {
            System.out.println("Server started");
          } else {
            System.out.println("Cannot start the server: " + ar.cause());
          }
        });
  }

  private JsonObject getSingleQuote(String name) throws NotFoundException {
      JsonObject result = quotes.get(name);
      if (result == null) {
          throw new NotFoundException();
      }
      return result;
  }

  private static class NotFoundException extends Exception {}
}
