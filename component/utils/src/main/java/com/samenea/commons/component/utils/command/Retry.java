package com.samenea.commons.component.utils.command;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import static com.samenea.commons.component.utils.log.LoggerFactory.LoggerType.*;


/**
 * A reusable class which can be used to retry a {@link Command}. it implements the {@link Command} also and
 * thus itself can be used as it (Composite and Command design patterns)
 * parameter such as {@link #maxRetry} and {@link #backOffTimeMillis} can be configured
 * <p>To create a command use {@link #on(Command)}.Two example for using this class are : the following will retry
 * until gets a web service reference <p/>
 * <pre class="code"><code class="java">
        private SmsWebservice tryToCreateWebservice() {
            Command<SmsWebservice> createWebService = new Command<SmsWebservice>() {
                @Override
                public SmsWebservice execute() {
                        return new WebserviceImpl(webServiceAddress);
                }
            };
            return Retry.on(createWebService).waitForEachRetry(backOffTimeMillis).execute();
        }
 * </code></pre>
 * <p>Or if we need to access Retry after or while executing we can use :</p>
 * <pre class="code"><code class="java">
            Retry<WebService> webServiceCreateRetry Retry.on(createWebService).waitForEachRetry(backOffTimeMillis).build();
            webServiceCreateRetry.execute();
            And in another thread which monitors the Retry for example
            webServiceCreateRetry.getCurrentNumberOfTries();

 * </code></pre>
 * <p>The class is designed to be thread safe. Currently the execute is synchronized and thus only 1 thread can be
 * active executing it at a time so consider this and keep an eye on deadlock or waiting time of concurrent threads
 * </p>
 * <p>Todo: Currently this will retry on every exception, in future it should be customizable</p>
 * <p>Class is Immutable [It changes some fields but it is internal]. future changes should consider this</p>
 * @author Jalal Ashrafi
 * @see #on(Command) for creating a command
 * @see #execute()
 * @see Command
 */
public final class Retry<T> implements Command<T> {
    /**
     * Retry until getting answer
     */
    public static final Integer UNLIMITED = 0;
    /**
     * Value of {@link #getBackOffTimeMillis()} No wait by Default Between retry
     */
    public static final Long IMMEDIATE_RETRY = 0L;
    private final Command<T> command;
    /**
     * How much waits between subsequent retries
     */
    private final Long backOffTimeMillis;
    private final Integer maxRetry;
    private int currentNumberOfTries;
    Logger logger = LoggerFactory.getLogger(Retry.class);
    Logger exceptionLogger = LoggerFactory.getLogger(Retry.class, EXCEPTION);
    private final Object SLEEP_LOCK;

    /**
     * Construct the command
     * @param command command to be executed
     * @param maxRetry if null means for ever
     * @param backOffTimeMillis if null or 0 means no delay
     */
    private Retry(Command<T> command, Integer maxRetry, Long backOffTimeMillis) {
        validate(command);
        validateMaxRetry(maxRetry);
        validateBackOffTime(backOffTimeMillis);
        this.command = command;
        this.backOffTimeMillis = backOffTimeMillis;
        this.maxRetry = maxRetry;
        this.currentNumberOfTries = 0;
        this.SLEEP_LOCK = new Object();
    }


    /**
     * By default and without setting other parameters it will create a endless Retry (until getting result)
     * with no wait between retries, other params can be configured if needed
     * @param command command to retry
     * @param <T> return type of command
     * @return builder for command
     * @see com.samenea.commons.component.utils.command.Retry.Builder#maxRetry(Integer)
     * @see com.samenea.commons.component.utils.command.Retry.Builder#waitForEachRetry(Long)
     */
    public static <T> Builder<T> on(Command<T> command) {
        return new Builder<T>(command);
    }

    public static class Builder<T> {
        private Command<T> command;
        private Integer maxRetry;
        private Long backOffTimeMillis;

        private Builder(Command<T> command) {
            validate(command);
            this.command = command;
            this.maxRetry = UNLIMITED;
            this.backOffTimeMillis = IMMEDIATE_RETRY;
        }

        /**
         *
         * @param maxRetry maximum times of retry
         * @return retry builder builder
         * @see com.samenea.commons.component.utils.command.Retry#getMaxRetry()
         */
        public Builder<T> maxRetry(Integer maxRetry) {
            validateMaxRetry(maxRetry);
            this.maxRetry = maxRetry;
            return this;
        }

        /**
         *
         * @param backOffTimeMillis set the backOffTime between each Retry
         * @return retry builder builder
         */
        public Builder<T> waitForEachRetry(Long backOffTimeMillis) {
            validateBackOffTime(backOffTimeMillis);
            this.backOffTimeMillis = backOffTimeMillis;
            return this;
        }

        /**
         * @return retry builder builder
         */
        public Retry<T> build() {
            return new Retry<T>(this.command, this.maxRetry, this.backOffTimeMillis);
        }

        /**
         * Just a shorthand for {@link #build()}.{@link com.samenea.commons.component.utils.command.Retry#execute()}
         * <p>If It is needed to examine the Retry or use it for example passing it around or checking
         * {@link #getCurrentNumberOfTries()}, {@link #build()} should be used first
         * <p/>
         * @return the result of retrying command
         * @throws MaxRetryReachedException
         */
        public synchronized final T execute() throws MaxRetryReachedException {
            return build().execute();
        }
    }


    public Command<T> getCommand() {
        return command;
    }

    /**
     *
     * @return {@link #IMMEDIATE_RETRY} there no wait between retries
     */
    public Long getBackOffTimeMillis() {
        return backOffTimeMillis;
    }

    /**
     *
     * @return {@link #UNLIMITED} there endless retry
     */
    public Integer getMaxRetry() {
        return maxRetry;
    }

    /**
     * Every time an exception occurs this method will run the execute after {@link #backOffTimeMillis} sleeping
     * @return result of {@link #command#execute} execution
     * @throws MaxRetryReachedException if {@link #command#execute} can not be executed without exception after maxRetry
     * number of tries
     */
    @Override
    public synchronized final T execute() throws MaxRetryReachedException {
        currentNumberOfTries = 0;
        //forever if maxRetry is null
        logger.debug("started retrying, retry properties are: {}",toString());
        while (UNLIMITED.equals(maxRetry) || currentNumberOfTries < maxRetry){
            currentNumberOfTries ++;
            try {
                return command.execute();
            } catch (Exception e) {
                logger.info("command failed: {} exception message is {} try number {}", command.toString(), e.getMessage(),currentNumberOfTries);
                exceptionLogger.debug(String.format("command failed: %s try number %s",command.toString(),currentNumberOfTries),e);
                tryToSleep();
            }
        }
        throw new MaxRetryReachedException(String.format("Maximum Retry reached for command: %s. number of retries is : %s"
                , command.toString(), maxRetry),maxRetry);
    }

    //todo there is no test available for this section it can be tested using environment sleep method but ...
    //it shows a design flaw maybe there should be a better way instead of calling sleep
    private void tryToSleep() {
        if(backOffTimeMillis.equals(IMMEDIATE_RETRY)){
            logger.debug("backOffTimeMillis is IMMEDIATE_RETRY thus retry immediately");
            return;
        }
        logger.debug("Try to sleep {} millis",backOffTimeMillis);
        try {
            synchronized (SLEEP_LOCK){
                Thread.sleep(backOffTimeMillis);
            }
        } catch (InterruptedException e) {
            logger.warn("Can not sleep thus retry will be run immediately. Perhaps there is a bug because this class is thread safe ");
            exceptionLogger.warn(String.format("Can not sleep thus retry will be run immediately. Perhaps there is a bug because this class is thread safe "), e);
        }
    }

    public int getCurrentNumberOfTries() {
        return currentNumberOfTries;
    }

    private static void validateBackOffTime(Long backOffTimeMillis) {
        Assert.notNull(backOffTimeMillis,"backOffTime can not be null");
        Assert.isTrue(IMMEDIATE_RETRY.equals(backOffTimeMillis) || backOffTimeMillis >= 0, "backOffTimeMillis should be >= 0 if is not IMMEDIATE_RETRY");
    }

    private static void validateMaxRetry(Integer maxRetry) {
        Assert.notNull(maxRetry,"maxRetry can not be null");
        Assert.isTrue(UNLIMITED.equals(maxRetry) || maxRetry > 0, "maxRetry should be > 0 if is not UNLIMITED");
    }

    private static void validate(Command command) {
        Assert.notNull(command, "Command can not be null");
    }

    @Override
    public String toString() {
        final String maxRetryString = UNLIMITED.equals(maxRetry) ? "Forevery" : String.valueOf(maxRetry);
        final String backOffTimeString = IMMEDIATE_RETRY.equals(backOffTimeMillis) ? "No Wait" : String.valueOf(backOffTimeMillis) + " Milliseconds";
        return "Retry{" +
                "backOffTimeMillis=" + backOffTimeString +
                ", maxRetry=" + maxRetryString +
                ", currentNumberOfTries=" + currentNumberOfTries +
                ", command=" + command.toString() +
                '}';
    }
}
