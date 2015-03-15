package com.samenea.commons.webmvc.controller;


import com.samenea.commons.webmvc.model.MessageType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;



public class BaseControllerTest {

    private final String TEXT ="text";
    @Spy
    BaseController baseController = new BaseController() {
    };


    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void should_addMessage_add_message_to_messages(){
        baseController.addMessage(TEXT, MessageType.INFO);

        Assert.assertEquals(1, baseController.getMessages().size());
    }

    @Test
    public void  clearMessages_should_clear_messages(){
        baseController.addMessage(TEXT,MessageType.INFO);
        baseController.clearMessages();

        Assert.assertEquals(0, baseController.getMessages().size());
    }
}
