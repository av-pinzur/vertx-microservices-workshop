package io.vertx.workshop.trader.impl;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.servicediscovery.types.MessageSource;
import io.vertx.workshop.common.MicroServiceVerticle;
import io.vertx.workshop.portfolio.PortfolioEvent;
import io.vertx.workshop.portfolio.PortfolioService;

/**
 * A compulsive trader...
 */
public class JavaCompulsiveTraderVerticle extends MicroServiceVerticle {

  private final String company = TraderUtils.pickACompany();
  private final int numberOfShares = TraderUtils.pickANumber();
  private PortfolioService portfolioService;

  @Override
  public void start(Future<Void> future) {
    super.start();

    //TODO
    //----

    Future<MessageConsumer<JsonObject>> marketFuture = Future.future();
    Future<PortfolioService> portfolioFuture = Future.future();

    MessageSource.getConsumer(discovery, new JsonObject().put("name", "market-data"), marketFuture.completer());
    EventBusService.getProxy(discovery, PortfolioService.class, portfolioFuture.completer());

    CompositeFuture.all(marketFuture, portfolioFuture).setHandler(ar -> {
      if (ar.failed()) {
        future.fail(ar.cause());
      } else {
        MessageConsumer<JsonObject> marketConsumer = marketFuture.result();
        portfolioService = portfolioFuture.result();
        
        marketConsumer.handler(this::handleMarketEvent);
        future.succeeded();
      }
    });

    // ----
  }

  private void handleMarketEvent(Message<JsonObject> message) {
    TraderUtils.dumbTradingLogic(company, numberOfShares, portfolioService, message.body());
  }
}
