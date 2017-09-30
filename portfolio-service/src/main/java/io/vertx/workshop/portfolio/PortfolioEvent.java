package io.vertx.workshop.portfolio;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

@DataObject(generateConverter = true)
public class PortfolioEvent {

    private String action;
    private JsonObject quote;
    private long date;
    private int amount;
    private int owned;

    public PortfolioEvent() {}

    public PortfolioEvent(PortfolioEvent other) {
        this.action = other.action;
        this.quote = other.quote.copy();
        this.date = other.date;
        this.amount = other.amount;
        this.owned = other.owned;
    }

    public PortfolioEvent(JsonObject json) {
        PortfolioEventConverter.fromJson(json, this);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        PortfolioEventConverter.toJson(this, json);
        return json;
    }

    public String getAction() {
        return action;
    }

    public PortfolioEvent setAction(String action) {
        this.action = action;
        return this;
    }

    public JsonObject getQuote() {
        return quote;
    }

    public PortfolioEvent setQuote(JsonObject quote) {
        this.quote = quote;
        return this;
    }

    public long getDate() {
        return date;
    }

    public PortfolioEvent setDate(long date) {
        this.date = date;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public PortfolioEvent setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public int getOwned() {
        return owned;
    }

    public PortfolioEvent setOwned(int owned) {
        this.owned = owned;
        return this;
    }
}
