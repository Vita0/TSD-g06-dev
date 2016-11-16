// it is work!

package com.vaadin.client;

import java.util.logging.Logger;

import com.vaadin.client.ApplicationConnection;

import elemental.json.JsonArray;

public class MyApplicationConnection2 extends ApplicationConnection {

  private final Logger logger = Logger
          .getLogger(MyApplicationConnection2.class.getName());
  @Override
  protected void makeUidlRequest(final JsonArray reqInvocations,
          final String extraParams) {
  	super.makeUidlRequest(reqInvocations, extraParams);
  	logger.info("lol!!!");
  	logger.info("reqInvocations: " + reqInvocations.toString());
  	logger.info("extraParams: " + extraParams);
  }
  
}
