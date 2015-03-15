package com.samenea.commons.component.config;

import com.samenea.commons.component.config.model.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConfigPlaceHolder extends PropertyPlaceholderConfigurer {
    public static final String DEFAULT_PREFIX = "%{";
    public static final String DEFAULT_SUFFIX = "}";
    public static final String OPEN_BRACKET = "[";
    public static final String CLOSE_BRACKET = "]";
    public static final String DEFAULT_CONFIG_SEPARATOR = ":";
    private String placeholderConfigSeparator = DEFAULT_CONFIG_SEPARATOR;
    private ConfigService configService;
    private String validationRegex;

    public ConfigPlaceHolder(Map<String, String> locations) {
        configService = new ConfigServiceImpl(locations);
        configService.refresh();
    }


    public ConfigPlaceHolder(String... locations) {
        configService = new ConfigServiceImpl(locations);
        configService.refresh();
    }

    public ConfigPlaceHolder(Resource[] locations) {
        System.out.println(locations);
    }

    public ConfigPlaceHolder(ConfigService configService) {
        this.configService = configService;
    }

    {

        placeholderPrefix = DEFAULT_PREFIX;
        placeholderSuffix = DEFAULT_SUFFIX;
        validationRegex = "^.+" + placeholderConfigSeparator + "[^\\[\\]]+(\\[\\d{1,2}\\])?$";
    }


    public void setPlaceholderConfigSeparator(String placeholderConfigSeparator) {
        this.placeholderConfigSeparator = placeholderConfigSeparator;
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver();
        this.doProcessProperties(beanFactory, valueResolver);
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final PropertyPlaceholderHelper helper;

        private final PropertyPlaceholderHelper.PlaceholderResolver resolver;

        public PlaceholderResolvingStringValueResolver() {
            this.helper = new PropertyPlaceholderHelper(
                    placeholderPrefix, placeholderSuffix);
            this.resolver = new PropertyPlaceholderConfigurerResolver();
        }

        public String resolveStringValue(String strVal) throws BeansException {
            String value = this.helper.replacePlaceholders(strVal, this.resolver);
            return (value.equals(nullValue) ? null : value);
        }
    }


    private class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {


        private PropertyPlaceholderConfigurerResolver() {
        }

        public String resolvePlaceholder(String placeholderName) {
            return resolveString(placeholderName);
        }
    }

    /**
     * @param placeholderName
     * @return resovled string
     */
    public String resolveString(String placeholderName) {
        final String defaultString = placeholderName;
        if (!validatePattern(placeholderName)) {
            return defaultString;
        }
        int separatorIndex = placeholderName.indexOf(placeholderConfigSeparator);
        Config byName = configService.getByName(placeholderName.substring(0, separatorIndex));
        if (byName == null) {
            return defaultString;
        }
        String propertyName = placeholderName.substring(separatorIndex + 1, placeholderName.length());
        int index = 0;
        boolean hasIndex = false;
        int indexOfOpenBracket = propertyName.lastIndexOf(OPEN_BRACKET);
        int indexOfCloseBracket = propertyName.lastIndexOf(CLOSE_BRACKET);
        if (!(indexOfCloseBracket == -1 && indexOfOpenBracket == -1)) {
            hasIndex = true;
            index = Integer.parseInt(propertyName.substring(indexOfOpenBracket + 1, indexOfCloseBracket));
            propertyName = propertyName.substring(0, indexOfOpenBracket);
        }
        List<String> valuesByKey = byName.findValuesByKey(propertyName);
        if (valuesByKey == null || valuesByKey.size() <= index) {
            return defaultString;
        }
        if (!hasIndex) {
            return joinAsCommaSeparated(valuesByKey);
        }
        return valuesByKey.get(index);
    }

    private String joinAsCommaSeparated(List<String> valuesByKey) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<String> iterator = valuesByKey.iterator();
        while (iterator.hasNext()) {
            stringBuffer.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuffer.append(",");
            }
        }
        return stringBuffer.toString();
    }


    /**
     * checks if place holder is valid base on convention
     *
     * @param placeholderName
     * @return true if valid
     */
    protected boolean validatePattern(String placeholderName) {
        return placeholderName.matches(validationRegex);
    }

}
