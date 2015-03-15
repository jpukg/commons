package com.samenea.commons.webmvc.controller;

import com.samenea.commons.webmvc.model.Message;
import com.samenea.commons.webmvc.model.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * extend this class in all of your controllers. then you have common facilities
 * that you can use in your controller.
 *
 * @author soroosh
 * 
 */
public abstract class BaseController {
	protected List<Message> messages;
	protected final Locale locale = new Locale("fa","IR");
	@Autowired
	protected MessageSource messageSource;
	
	public BaseController(){
		this.messages = new ArrayList<Message>();
	}

    /**
     * you can add message with this method.
     * your messages will use in View MessageManagement. also it's possible to use them explicitly in View.
     * @param messageText
     * @param messageType
     */
	public void addMessage(String messageText,MessageType messageType) {
		this.messages.add(new Message(messageText, messageType));
	}
	/**
	 * you can add message with this method.
	 * your messageCode will convert to messageText.
	 * @see BaseController#addMessage(String, MessageType)
	 * @param messageCode the message code that you define in resource files
	 * @param messageType
	 */
	
	public void addMessageFromResource(String messageCode,MessageType messageType){
		final String messageText = messageSource.getMessage(messageCode, null, locale);
		addMessage(messageText,messageType);
	}
	
	
	/**
	 * all added messages will remove.
	 */
	public void clearMessages() {
		this.messages.clear();
	}

	/**
	 * a model attribute for using in View with 'messages' name.
	 * @return
	 */
	@ModelAttribute("messages")
	public List<Message> getMessages() {
		return Collections.unmodifiableList(this.messages);
	}

	

}
