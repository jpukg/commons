package com.samenea.commons.component.utils.persian;

import junit.framework.Assert;
import org.junit.Test;

public class NumberUtilTest {

    @Test
    public void should_be_convert_englishDigit_to_stringPersianDigite_toString() {

        Assert.assertNotNull(NumberUtil.toString(15824));


    }

    @Test
    public void should_be_convert_stringEnglishDigit_to_stringPersianDigite_toDigit() {

        Assert.assertNotNull(NumberUtil.toDigit('1'));


    }

    @Test
    public void convertNumbersToAscii_should_convert_persian_numbers_to_ascii() {
        final String asciiNumber = NumberUtil.convertNumbersToAscii("۱۱۱۱");
        Assert.assertEquals("1111", asciiNumber);
    }

    @Test
    public void convertNumbersToAscii_should_convert_just_numbers() {
        final String asciiNumber = NumberUtil.convertNumbersToAscii("۱۱a۱۱");
        Assert.assertEquals("11a11", asciiNumber);
    }


}
