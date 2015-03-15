package com.samenea.commons.component.utils.persian;

/**
 * Utility class to convert numbers into persian digits.
 *
 * @author payam faryadres
 * @date 12-11-2012
 */
public final class NumberUtil {

    private static final char[] ARABIC_DIGITS = {'\u0660', '\u0661', '\u0662',
            '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668',
            '\u0669'};

    private static final char[] PERSIAN_DIGITS = {'\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4', '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9'};
    private static final char[] ENGLISH_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * Convert string with digit  in the specified string to persian digits.
     */
    public static String convertDigits(String str) {
        if (str == null || str.length() == 0)
            return str;

        char[] s = new char[str.length()];
        for (int i = 0; i < s.length; i++)
            s[i] = toDigit(str.charAt(i));

        return new String(s);
    }

    /**
     * Convert single digit in the specified string to persian digit.
     */
    public static char toDigit(char ch) {
        int n = Character.getNumericValue((int) ch);
        return n >= 0 && n < 10 ? PERSIAN_DIGITS[n] : ch;
    }

    public static String convertNumbersToAscii(String str) {
        if (str == null) {
            return null;
        }
        String result = str;
        for (int i = 0; i <= 9; i++) {
            result = result.replace(PERSIAN_DIGITS[i], ENGLISH_DIGITS[i]);
            result = result.replace(ARABIC_DIGITS[i], ENGLISH_DIGITS[i]);
        }

        return result;
    }


    /**
     * Convert an int into persian string.
     */
    public static String toString(int num) {
        return convertDigits(Integer.toString(num));
    }

}
