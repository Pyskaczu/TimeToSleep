package com.pyskacz.shutter.utils;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilsTest {

	private static String lotsOfDigits;
	private static final int NUMBER_OF_DIGITS = 100_000_000;

	@BeforeClass
	public static void init() {
		StringBuilder sb = new StringBuilder(NUMBER_OF_DIGITS);
		Random rand = new Random();
		for (int i = 0; i < NUMBER_OF_DIGITS; i++) {
			sb.append(rand.nextInt(10));
		}

		lotsOfDigits = sb.toString();
	}

	@Test
	public void onlyDigits3digits() {
		assertTrue(StringUtils.onlyDigits("123"));
	}

	@Test
	public void onlyDigitsCharacters() {
		assertFalse(StringUtils.onlyDigits("a"));
	}
	@Test
	public void onlyDigitsEmpty() {
		assertFalse(StringUtils.onlyDigits(""));
	}

	@Test
	public void onlyDigitsNull() {
		assertFalse(StringUtils.onlyDigits(null));
	}

	@Test
	public void onlyDigitsLong() {
		long startTime = System.currentTimeMillis();
		assertTrue(StringUtils.onlyDigits(lotsOfDigits));
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("onlyDigits: " + duration);
	}

	@Test
	public void onlyDigitsRegex3digits() {
		assertTrue(StringUtils.onlyDigitsRegex("123"));
	}

	@Test
	public void onlyDigitsRegexCharacters() {
		assertFalse(StringUtils.onlyDigitsRegex("a"));
	}
	@Test
	public void onlyDigitsRegexEmpty() {
		assertFalse(StringUtils.onlyDigitsRegex(""));
	}

	@Test
	public void onlyDigitsRegexNull() {
		assertFalse(StringUtils.onlyDigitsRegex(null));
	}

	@Test
	public void onlyDigitsRegexLong() {
		long startTime = System.currentTimeMillis();
		assertTrue(StringUtils.onlyDigitsRegex(lotsOfDigits));
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("onlyDigitsRegex: " + duration);
	}
}
