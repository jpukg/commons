package com.samenea.commons.component.utils.persian;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible to convert a number into persian written form.
 * 
 * @author darbandsari
 *	
 */
public class NumberToWritten {
	private static final String SEPARATOR = " و ";
	private static final int BILION = 1000000000;
	
	private static Map<Integer, String> writtenForm;
	private static Map<Integer, String> bigNumbers;
	
	static {
		writtenForm = new HashMap<Integer, String>();
		
		writtenForm.put(0, "صفر");
		writtenForm.put(1, "یک");
		writtenForm.put(2, "دو");
		writtenForm.put(3, "سه");
		writtenForm.put(4, "چهار");
		writtenForm.put(5, "پنج");
		writtenForm.put(6, "شش");
		writtenForm.put(7, "هفت");
		writtenForm.put(8, "هشت");
		writtenForm.put(9, "نه");
		writtenForm.put(10, "ده");
		writtenForm.put(11, "یازده");
		writtenForm.put(12, "دوازده");
		writtenForm.put(13, "سیزده");
		writtenForm.put(14, "چهارده");
		writtenForm.put(15, "پانزده");
		writtenForm.put(16, "شانزده");
		writtenForm.put(17, "هفده");
		writtenForm.put(18, "هجده");
		writtenForm.put(19, "نوزده");
		writtenForm.put(20, "بیست");
		writtenForm.put(30, "سی");
		writtenForm.put(40, "چهل");
		writtenForm.put(50, "پنجاه");
		writtenForm.put(60, "شصت");
		writtenForm.put(70, "هفتاد");
		writtenForm.put(80, "هشتاد");
		writtenForm.put(90, "نود");
		writtenForm.put(100, "صد");
		writtenForm.put(200, "دویست");
		writtenForm.put(300, "سیصد");
		writtenForm.put(400, "چهارصد");
		writtenForm.put(500, "پانصد");
		writtenForm.put(600, "ششصد");
		writtenForm.put(700, "هفتصد");
		writtenForm.put(800, "هشتصد");
		writtenForm.put(900, "نهصد");
		
		bigNumbers = new HashMap<Integer, String>();
		bigNumbers.put(1000, "هزار");
		bigNumbers.put(1000000, "میلیون");
		bigNumbers.put(1000000000, "میلیارد");
	}

	public static String convert(int number) {
		if (number == 0) return writtenForm.get(0);

		return convertToWrittenForm(number, BILION, 1000);
	}
	
	/**
	 * This method use recursive algorithm to convert a number into its persian written form.
	 * When step is 1000 it means that we are going to split number into separate sections that contains
	 * only three digits, then we add proper postfix according to its place value. When the step is 10
	 * it means that we have a three digit number. For three digits numbers
	 * the initialScope value is 100.
	 */
	private static String convertToWrittenForm(int number, int initialScope, int step) {
		StringBuffer result = new StringBuffer();
		
		for (int scope = initialScope; scope != 0; scope /= step) {
			int quotient = number / scope;
			int remain = number % scope;
		
			if (quotient != 0) {
				if (writtenForm.containsKey(quotient * scope)) {
					/* This section will called when we convert a number with three digits in it */
					result.append(writtenForm.get(quotient* scope));
				}
				else {
					/* This number contains three digit, convert it to written form */
					result.append(convertToWrittenForm(quotient, 100, 10));
				}
				
				if (scope >= 1000) {
					result.append(" ");
					result.append(bigNumbers.get(scope));
				}
			}
		
			if (remain != 0) {
				if (quotient != 0) result.append(SEPARATOR);
			}

			number = remain;

			if (number > 0 && number < 20) {
				result.append(writtenForm.get(number));
				break;
			}
		}
		
		return result.toString();
	}
}
