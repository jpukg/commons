package com.samenea.commons.component.utils.persian;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class NumberToWrittenTest {
	@Test
	public void convert_0_to_written() {
		assertEquals("صفر", NumberToWritten.convert(0));
	}
	
	@Test
	public void convert_1_to_written() {
		assertEquals("یک", NumberToWritten.convert(1));
	}

	@Test
	public void convert_2_to_written() {
		assertEquals("دو", NumberToWritten.convert(2));
	}

	@Test
	public void convert_3_to_written() {
		assertEquals("سه", NumberToWritten.convert(3));
	}

	@Test
	public void convert_4_to_written() {
		assertEquals("چهار", NumberToWritten.convert(4));
	}

	@Test
	public void convert_5_to_written() {
		assertEquals("پنج", NumberToWritten.convert(5));
	}

	@Test
	public void convert_6_to_written() {
		assertEquals("شش", NumberToWritten.convert(6));
	}

	@Test
	public void convert_7_to_written() {
		assertEquals("هفت", NumberToWritten.convert(7));
	}

	@Test
	public void convert_8_to_written() {
		assertEquals("هشت", NumberToWritten.convert(8));
	}

	@Test
	public void convert_9_to_written() {
		assertEquals("نه", NumberToWritten.convert(9));
	}
	
	@Test
	public void convert_10_to_written() {
		assertEquals("ده", NumberToWritten.convert(10));
	}

	@Test
	public void convert_11_to_written() {
		assertEquals("یازده", NumberToWritten.convert(11));
	}

	@Test
	public void convert_12_to_written() {
		assertEquals("دوازده", NumberToWritten.convert(12));
	}
	
	@Test
	public void convert_13_to_written() {
		assertEquals("سیزده", NumberToWritten.convert(13));
	}
	
	@Test
	public void convert_14_to_written() {
		assertEquals("چهارده", NumberToWritten.convert(14));
	}

	@Test
	public void convert_15_to_written() {
		assertEquals("پانزده", NumberToWritten.convert(15));
	}

	@Test
	public void convert_16_to_written() {
		assertEquals("شانزده", NumberToWritten.convert(16));
	}

	@Test
	public void convert_17_to_written() {
		assertEquals("هفده", NumberToWritten.convert(17));
	}

	@Test
	public void convert_18_to_written() {
		assertEquals("هجده", NumberToWritten.convert(18));
	}

	@Test
	public void convert_19_to_written() {
		assertEquals("نوزده", NumberToWritten.convert(19));
	}
	
	@Test
	public void convert_20_to_written() {
		assertEquals("بیست", NumberToWritten.convert(20));
	}
	
	@Test
	public void convert_21_to_written() {
		assertEquals("بیست و یک", NumberToWritten.convert(21));
	}

	@Test
	public void convert_32_to_written() {
		assertEquals("سی و دو", NumberToWritten.convert(32));
	}

	@Test
	public void convert_43_to_written() {
		assertEquals("چهل و سه", NumberToWritten.convert(43));
	}

	@Test
	public void convert_54_to_written() {
		assertEquals("پنجاه و چهار", NumberToWritten.convert(54));
	}

	@Test
	public void convert_66_to_written() {
		assertEquals("شصت و شش", NumberToWritten.convert(66));
	}

	@Test
	public void convert_75_to_written() {
		assertEquals("هفتاد و پنج", NumberToWritten.convert(75));
	}

	@Test
	public void convert_87_to_written() {
		assertEquals("هشتاد و هفت", NumberToWritten.convert(87));
	}

	@Test
	public void convert_99_to_written() {
		assertEquals("نود و نه", NumberToWritten.convert(99));
	}
	
	@Test
	public void convert_100_to_written() {
		assertEquals("صد", NumberToWritten.convert(100));
	}
	
	@Test
	public void convert_215_to_written() {
		assertEquals("دویست و پانزده", NumberToWritten.convert(215));
	}

	@Test
	public void convert_307_to_written() {
		assertEquals("سیصد و هفت", NumberToWritten.convert(307));
	}

	@Test
	public void convert_438_to_written() {
		assertEquals("چهارصد و سی و هشت", NumberToWritten.convert(438));
	}

	@Test
	public void convert_591_to_written() {
		assertEquals("پانصد و نود و یک", NumberToWritten.convert(591));
	}

	@Test
	public void convert_620_to_written() {
		assertEquals("ششصد و بیست", NumberToWritten.convert(620));
	}

	@Test
	public void convert_710_to_written() {
		assertEquals("هفتصد و ده", NumberToWritten.convert(710));
	}

	@Test
	public void convert_800_to_written() {
		assertEquals("هشتصد", NumberToWritten.convert(800));
	}

	@Test
	public void convert_999_to_written() {
		assertEquals("نهصد و نود و نه", NumberToWritten.convert(999));
	}

	@Test
	public void convert_1000_to_written() {
		assertEquals("یک هزار", NumberToWritten.convert(1000));
	}

	@Test
	public void convert_1005_to_written() {
		assertEquals("یک هزار و پنج", NumberToWritten.convert(1005));
	}
	
	@Test
	public void convert_1015_to_written() {
		assertEquals("یک هزار و پانزده", NumberToWritten.convert(1015));
	}

	@Test
	public void convert_1029_to_written() {
		assertEquals("یک هزار و بیست و نه", NumberToWritten.convert(1029));
	}

	@Test
	public void convert_1359_to_written() {
		assertEquals("یک هزار و سیصد و پنجاه و نه", NumberToWritten.convert(1359));
	}
	
	@Test
	public void convert_9080_to_written() {
		assertEquals("نه هزار و هشتاد", NumberToWritten.convert(9080));
	}
	
	@Test
	public void convert_1000000_to_written() {
		assertEquals("یک میلیون", NumberToWritten.convert(1000000));
	}
	
	@Test
	public void convert_1000001_to_written() {
		assertEquals("یک میلیون و یک", NumberToWritten.convert(1000001));
	}

	@Test
	public void convert_124345000_to_written() {
		assertEquals("صد و بیست و چهار میلیون و سیصد و چهل و پنج هزار", NumberToWritten.convert(124345000));
	}

	@Test
	public void convert_2134765_to_written() {
		assertEquals("دو میلیون و صد و سی و چهار هزار و هفتصد و شصت و پنج", NumberToWritten.convert(2134765));
	}
	
	@Test
	public void convert_1000000000_to_written() {
		assertEquals("یک میلیارد", NumberToWritten.convert(1000000000));
	}

	@Test
	public void convert_1000000009_to_written() {
		assertEquals("یک میلیارد و نه", NumberToWritten.convert(1000000009));
	}

	@Test
	public void convert_1000000210_to_written() {
		assertEquals("یک میلیارد و دویست و ده", NumberToWritten.convert(1000000210));
	}

	@Test
	public void convert_1002312654_to_written() {
		assertEquals("یک میلیارد و دو میلیون و سیصد و دوازده هزار و ششصد و پنجاه و چهار", NumberToWritten.convert(1002312654));
	}
}
