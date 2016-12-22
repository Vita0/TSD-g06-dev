package com.vaadin.client.communication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.ApplicationConfiguration.ErrorMessage;
import com.vaadin.client.ApplicationConnection.CommunicationErrorHandler;
import com.vaadin.client.ui.VNotification;
import com.vaadin.shared.ui.ui.UIState.PushConfigurationState;

import elemental.json.JsonObject;

public class MyAtmospherePushConnection extends AtmospherePushConnection {
	
	  private final Logger logger = Logger
	          .getLogger(MyAtmospherePushConnection.class.getName());
	
	private void myHandleCommunicationError(final ApplicationConnection connection, String details, int statusCode){
		logger.severe("Communication error: " + details);
		ErrorMessage message = connection.getConfiguration().getCommunicationError();
        VNotification.showError(connection, message.getCaption(),
                message.getMessage(), details, message.getUrl());
	}
	
	@Override
	public void init(final ApplicationConnection connection,
            final PushConfigurationState pushConfiguration,
            CommunicationErrorHandler errorHandler){
		
		logger.info("entering override method MyAtmospherePushConnection");
		
		errorHandler = new CommunicationErrorHandler() {
            @Override
            public boolean onError(String details, int statusCode) {
                myHandleCommunicationError(connection, details, statusCode);
                return true;
            }
        };
		super.init(connection, pushConfiguration, errorHandler);
		
		
//		ArrayList<JsonObject> messageQueue = new ArrayList<JsonObject>();
//		messageQueue.toString();
//		for(int i = 0; i < messageQueue.size(); i++){
//			Cookie["my_cookie_name" + String.valueOf(i)] = messageQueue.get(i).toString();
//		}
	}
	
    @Override
    public void push(JsonObject message) {
    	try{
//	    	Field f = super.getClass().getDeclaredField("state");
//	    	f.setAccessible(true);
//	    	AtmospherePushConnection.State state = f.;
	    	super.push(message);
	    	
	    	
    	}
    	catch (Exception ex){
    		
    	}
    }
}

// TODO
/*
 * “ам есть ArrayList, куда записываютс€ неотправленные сообщени€, и он сам пытаетс€ отправить их каждые 10 секунд.
 * ѕохоже нужно только сделать чтобы они сохран€лись в куки при закрытии окна, и выгружались из них при открытии.
 * Ќо этот ArrayList private, поэтому придетс€ создать свой и параллельно к нему добавл€ть
 * 
 * 
 */

