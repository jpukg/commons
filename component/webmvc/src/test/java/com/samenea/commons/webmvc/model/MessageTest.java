package com.samenea.commons.webmvc.model;

import org.junit.Test;

public class MessageTest {

    @Test(expected = IllegalArgumentException.class)
    public void should_throws_exception_when_message_does_not_have_type_or_text(){
        Message message = new Message(null,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throws_exception_when_message_text_is_empty(){
        Message message = new Message("",MessageType.ERROR);

    }
}
