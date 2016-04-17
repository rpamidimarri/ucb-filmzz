package com.filmzz.tmdb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPattern {
	public static void main(String[] args) {
		String x = "abcd &989bdf (1999)";
		String y = "kjshdkfjsder skjdfksd (2013)";
		String z = "ajdfsdkljf (2000) (TV)";
		String a = "ajdfsdkljf (2000/I) (TV)";
		String b = "'As You Like It' at Shakespeare's Globe Theatre (2010) (V)";
		String c = "'Campeón del barrio' (Su última canción) (1964)";
		
		Pattern pattern = Pattern.compile("(.*)\\((\\d{4})\\).*");
		matcher(pattern.matcher(x));
		matcher(pattern.matcher(y));
		matcher(pattern.matcher(z));
		matcher(pattern.matcher(a));
		matcher(pattern.matcher(b));
		matcher(pattern.matcher(c));
	}
	
	private static void matcher(Matcher m) {
		if (m.matches()) {
			System.out.println("Title:" + m.group(1));
			System.out.println("Year:" + m.group(2));
		}
	}
}
