package com.samenea.commons.webmvc.model;

public class Message {
	public Message(String text,MessageType messageType){
		if (text == null || text == "") {
			throw new IllegalArgumentException(
					"Message text cannot be null or empty.");
		}
		if (messageType == null) {
			throw new IllegalArgumentException("Message type cannot be null.");
		}
		this.text=text;
		this.messageType = messageType;
	}
	private String text;
	private MessageType messageType;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
}
