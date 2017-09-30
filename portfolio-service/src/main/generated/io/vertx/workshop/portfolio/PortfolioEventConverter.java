/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.workshop.portfolio;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link io.vertx.workshop.portfolio.PortfolioEvent}.
 *
 * NOTE: This class has been automatically generated from the {@link io.vertx.workshop.portfolio.PortfolioEvent} original class using Vert.x codegen.
 */
public class PortfolioEventConverter {

  public static void fromJson(JsonObject json, PortfolioEvent obj) {
    if (json.getValue("action") instanceof String) {
      obj.setAction((String)json.getValue("action"));
    }
    if (json.getValue("amount") instanceof Number) {
      obj.setAmount(((Number)json.getValue("amount")).intValue());
    }
    if (json.getValue("date") instanceof Number) {
      obj.setDate(((Number)json.getValue("date")).longValue());
    }
    if (json.getValue("owned") instanceof Number) {
      obj.setOwned(((Number)json.getValue("owned")).intValue());
    }
    if (json.getValue("quote") instanceof JsonObject) {
      obj.setQuote(((JsonObject)json.getValue("quote")).copy());
    }
  }

  public static void toJson(PortfolioEvent obj, JsonObject json) {
    if (obj.getAction() != null) {
      json.put("action", obj.getAction());
    }
    json.put("amount", obj.getAmount());
    json.put("date", obj.getDate());
    json.put("owned", obj.getOwned());
    if (obj.getQuote() != null) {
      json.put("quote", obj.getQuote());
    }
  }
}