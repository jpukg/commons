package com.samenea.commons.component.utils.command;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jalal Ashrafi
 */
public class RetryTest {
    private Command<String> command;
    private Long backOffWaitMillis;
    private Integer maxRetry;
    private String commandResult;

    @Before
    public void setup(){
        command = (Command<String>)Mockito.mock(Command.class);
        backOffWaitMillis = 10L;
        maxRetry = 5;
        commandResult = "command execution result";
    }
    @Test
    public void builder_should_create_command_correctly(){
        Retry<String> retry = Retry.on(command).waitForEachRetry(backOffWaitMillis).maxRetry(maxRetry).build();
        Assert.assertNotNull(retry);
        Assert.assertEquals(command, retry.getCommand());
        Assert.assertEquals(backOffWaitMillis, retry.getBackOffTimeMillis());
        Assert.assertEquals(maxRetry, retry.getMaxRetry());
        Assert.assertEquals(0, retry.getCurrentNumberOfTries());
    }


    @Test(expected = IllegalArgumentException.class)
    public void command_builder_should_not_be_created_with_null_command(){
        Retry.on(null);

    }
    @Test(expected = IllegalArgumentException.class)
    public void command_builder_maxRetry_should_not_be_null(){
        Retry.on(command).maxRetry(null);

    }
    @Test(expected = IllegalArgumentException.class)
    public void command_builder_maxRetry_should_not_be_negative(){
        Retry.on(command).maxRetry(-1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void command_builder_backOffTime_should_not_be_negative(){
        Retry.on(command).waitForEachRetry(-1L);
    }
    @Test(expected = IllegalArgumentException.class)
    public void command_builder_backOffTime_should_not_be_null(){
        Retry.on(command).waitForEachRetry(null);
    }
    @Test
    public void command_builder_should_create_an_endless_immediate_retry_command_by_default(){
        Retry<String> retry = Retry.on(command).build();
        assertEndlessImmediateRetryCommand(retry);
    }
    @Test
    public void command_builder_should_set_backOff_time_correctly(){
        Retry<String> retry = Retry.on(command).waitForEachRetry(backOffWaitMillis).build();
        assertEndlessRetryCommand(retry);
    }
    @Test
    public void command_builder_should_set_maxRetry_correctly(){
        Retry<String> retry = Retry.on(command).maxRetry(maxRetry).build();
        assertImmediateRetryCommand(retry);

    }
    @Test
    public void create_endless_retry_command_should_set_maxRetry_and_backOffTime_correctly(){
        Retry<String> retry = Retry.on(command).waitForEachRetry(backOffWaitMillis).build();
        assertEndlessRetryCommand(retry);
    }

    private void assertEndlessRetryCommand(Retry<String> retry) {
        Assert.assertEquals(command, retry.getCommand());
        Assert.assertEquals(backOffWaitMillis, retry.getBackOffTimeMillis());
        Assert.assertEquals(Retry.UNLIMITED, retry.getMaxRetry());
        Assert.assertEquals(0, retry.getCurrentNumberOfTries());
    }

    @Test
    public void create_immediate_retry_command_should_set_maxRetry_and_backOffTime_correctly(){
        Retry<String> retry = Retry.on(command).maxRetry(maxRetry).build();
        assertImmediateRetryCommand(retry);
    }


    @Test
    public void create_endless_immediate_retry_command_should_set_maxRetry_and_backOffTime_correctly(){
        Retry<String> retry = Retry.on(command).build();
        assertEndlessImmediateRetryCommand(retry);
    }

    @Test
    public void should_throw_exception_when_max_retry_reached(){
        when(command.execute()).thenThrow(new RuntimeException(),new RuntimeException(),new RuntimeException());
        int maxRetry = 3;
        try {
            Retry<String> retry = Retry.on(command).maxRetry(maxRetry).build();
            retry.execute();
            Assert.fail("Should not reach here MaxRetryReachedException expected");
        } catch (MaxRetryReachedException e) {
            Assert.assertEquals(maxRetry,e.getMaxRetry());
        }
    }
    @Test
    public void builder_execute_should_work_same_as_execute_on_retry(){
        when(command.execute()).thenThrow(new RuntimeException(),new RuntimeException()).thenReturn(commandResult);
        String commandResult = Retry.on(command).maxRetry(5).execute();
        verify(command,times(3)).execute();
        Assert.assertEquals(this.commandResult,commandResult);
    }
    @Test
    public void command_should_be_reusable_after_execution_complete(){
        when(command.execute()).thenThrow(new RuntimeException(),new RuntimeException(),new RuntimeException()).thenReturn(commandResult);
        Retry<String> retry = Retry.on(command).maxRetry(maxRetry).build();
        final String firstExecutionResult = retry.execute();
        Mockito.reset(command);
        when(command.execute()).thenThrow(new RuntimeException(),new RuntimeException(),new RuntimeException()).thenReturn(commandResult);
        final String secondExecutionResult = retry.execute();
        Assert.assertEquals(commandResult, secondExecutionResult);
        Assert.assertEquals(firstExecutionResult, secondExecutionResult);
    }
    @Test
    public void should_retry_until_get_result_before_trying_max_retry_times(){
        when(command.execute()).thenThrow(new RuntimeException(),new RuntimeException()).thenReturn(commandResult);
        Retry<String> retry = Retry.on(command).maxRetry(5).build();
        String commandResult = retry.execute();
        verify(command,times(3)).execute();
        Assert.assertEquals(this.commandResult,commandResult);
    }

    private void assertImmediateRetryCommand(Retry<String> retry) {
        Assert.assertEquals(command, retry.getCommand());
        Assert.assertEquals(Retry.IMMEDIATE_RETRY, retry.getBackOffTimeMillis());
        Assert.assertEquals(maxRetry, retry.getMaxRetry());
        Assert.assertEquals(0, retry.getCurrentNumberOfTries());
    }

    private void assertEndlessImmediateRetryCommand(Retry<String> retry) {
        Assert.assertEquals(command, retry.getCommand());
        Assert.assertEquals(Retry.IMMEDIATE_RETRY, retry.getBackOffTimeMillis());
        Assert.assertEquals(Retry.UNLIMITED, retry.getMaxRetry());
        Assert.assertEquals(0, retry.getCurrentNumberOfTries());
    }

}
