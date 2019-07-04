package com.pyskacz.shutter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static boolean onlyDigits(String value) {
		if(null == value || value.isEmpty()) return false;

		for(int i = 0; i < value.length(); i++) {
			if (!Character.isDigit(value.codePointAt(i))) return false;
		}
		return true;
	}

	public static boolean onlyDigitsRegex(String value) {
		if(null == value) return false;
		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
