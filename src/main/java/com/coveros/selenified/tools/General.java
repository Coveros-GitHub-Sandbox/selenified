/*
 * Copyright 2017 Coveros, Inc.
 * 
 * This file is part of Selenified.
 * 
 * Selenified is licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy 
 * of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on 
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

package com.coveros.selenified.tools;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class General {
	
	private static final int MAXFILENAMELENGTH = 200;

	private General() {
	}

	/**
	 * a method to recursively retrieve all the files in a folder
	 * 
	 * @param folder
	 *            - the folder to check for files
	 * @return ArrayList<String>: an ArrayList with the of multiple files
	 * @throws IOException
	 *             - an IOException
	 */
	public static List<String> listFilesForFolder(File folder) {
		List<String> files = new ArrayList<>();
		if (folder != null && folder.exists()) {
			for (final File fileEntry : folder.listFiles()) {
				if (fileEntry.isDirectory()) {
					files.addAll(listFilesForFolder(fileEntry));
				} else if (!".DS_Store".equals(fileEntry.getName())) {
					files.add(fileEntry.getPath());
				}
			}
		}
		return files;
	}

	/**
	 * A function to right pad the input value with spaces
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public static String padRightSpace(String input, int length) {
		return padRight(input, length, " ");
	}

	/**
	 * A function to right pad the input value with zeros
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public static String padRightZeros(long input, int length) {
		return padRight(String.valueOf(input), length, "0");
	}

	/**
	 * A function to right pad the input value with a value
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public static String padRight(int input, int length, String value) {
		return padRight(String.valueOf(input), length, value);
	}

	/**
	 * A function to right pad the input value with a value
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public static String padRight(String input, int length, String value) {
		StringBuilder output = new StringBuilder(length);
		if (input != null) {
			output.append(input);
		}
		while (output.toString().length() < length) {
			output.append(value);
		}
		return output.toString();
	}

	/**
	 * A function to left pad the input value with spaces
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public static String padLeftSpace(String input, int length) {
		return padLeft(input, length, " ");
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public static String padLeftZeros(long input, int length) {
		return padLeft(String.valueOf(input), length, "0");
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public static String padLeft(int input, int length, String value) {
		return padLeft(String.valueOf(input), length, value);
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input
	 *            - the value to be added
	 * @param length
	 *            - the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public static String padLeft(String input, int length, String value) {
		StringBuilder output = new StringBuilder(length);
		if (input != null) {
			output.append(input);
		}
		while (output.toString().length() < length) {
			output.insert(0, value);
		}
		return output.toString();
	}

	/**
	 * Generates a random character
	 * 
	 * @return random character
	 */
	public static String getRandomChar() {
		return getRandomString(1);
	}

	/**
	 * Generates a random string with 32 alpha-numeric characters
	 * 
	 * @return random string of 32 characters
	 */
	public static String getRandomString() {
		return getRandomString(32);
	}

	/**
	 * Generates a random string of alpha-numeric characters
	 * 
	 * @param length
	 *            the length of the random string
	 * @return random string of characters
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		String stringChars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(stringChars.charAt(rnd.nextInt(stringChars.length())));
		}
		return sb.toString();
	}

	/**
	 * Generates a random string of 9 numbers
	 * 
	 * @return random string of 9 numbers
	 */
	public static String getRandomInt() {
		return getRandomInt(9);
	}

	/**
	 * Generates a random string of numbers
	 * 
	 * @param length
	 *            the length of the random string
	 * @return random string of numbers
	 */
	public static String getRandomInt(int length) {
		if (length <= 0) {
			return "";
		}
		String stringChars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(stringChars.charAt(rnd.nextInt(stringChars.length())));
		}
		return sb.toString();
	}

	public static String removeNonWordCharacters(String value) {
		if (value == null) {
			return value;
		}
		return value.replaceAll("[^a-zA-Z0-9]+", "");
	}

	/**
	 * A function to determine if a value is present in an array
	 * 
	 * @param array
	 *            - the array to check for values
	 * @param value
	 *            - value to check for in the array
	 * @return boolean
	 */
	public static boolean doesArrayContain(String[] array, String value) {
		return Arrays.asList(array).contains(value);
	}

	/**
	 * A function to determine if a value is present in an array
	 * 
	 * @param array
	 *            - the array to check for values
	 * @param value
	 *            - value to check for in the array
	 * @return boolean
	 */
	public static boolean doesArrayContain(Object[] array, Object value) {
		return Arrays.asList(array).contains(value);
	}

	/**
	 * A function to reverse a string
	 * 
	 * @param source
	 *            - the string to reverse
	 * @return - the reversed string
	 */
	public static String reverseIt(String source) {
		if (source == null) {
			return source;
		}
		return new StringBuilder(source).reverse().toString();
	}

	/**
	 * this method will take in a long string with no spaces and change the
	 * camel case to spaces, including ensuring the first word is capitalized
	 * 
	 * @param word
	 *            - the input string
	 * @return the fixed string
	 */
	public static String wordToSentence(String word) {
		if (word == null) {
			return word;
		}
		String out = capitalizeFirstLetters(word);
		out = out.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
		out = out.replaceAll("[ ]{2,}", " ");
		return out;
	}

	/**
	 * a function to capitalize the first letter of each word in the provided
	 * string
	 * 
	 * @param word
	 *            - the string to be capitalized on
	 * @return String: the new string
	 */
	public static String capitalizeFirstLetters(String word) {
		if (word == null) {
			return word;
		}
		String out = "";
		for (int i = 0; i < word.length(); i++) {
			if (i == 0) {
				// Capitalize the first letter of the string.
				out = String.format("%s%s", Character.toUpperCase(word.charAt(0)), word.substring(1));
			}
			// Is this character a non-letter? If so
			// then this is probably a word boundary so let's capitalize
			// the next character in the sequence.
			if (!Character.isLetter(out.charAt(i)) && (i + 1) < out.length()) {
				out = String.format("%s%s%s", out.subSequence(0, i + 1), Character.toUpperCase(out.charAt(i + 1)),
						out.substring(i + 2));
			}
		}
		return out;
	}

	/**
	 * this method determines the unique test name, based on the parameters
	 * passed in
	 * 
	 * @param method
	 *            - the method under test to extract the name from
	 * @param dataProvider
	 *            - an array of objects being passed to the test as data
	 *            providers
	 * @return String: a unique name
	 */
	public static String getTestName(Method method, Object... dataProvider) {
		String className = method.getDeclaringClass().toString().substring(6);
		return getTestName(method.getName(), className, dataProvider);
	}

	/**
	 * this method determines the unique test name, based on the parameters
	 * passed in
	 * 
	 * @param methodName
	 *            - the name of the test method as a string
	 * @param className
	 *            - the class name of the test method as a string
	 * @param dataProvider
	 *            - an array of objects being passed to the test as data
	 *            providers
	 * @return String: a unique name
	 */
	public static String getTestName(String methodName, String className, Object... dataProvider) {
		StringBuilder testName = new StringBuilder(methodName + "_" + className);
		String currentName = testName.toString();
		if (dataProvider != null && dataProvider.length > 0 && dataProvider[0] != null
				&& !dataProvider[0].toString().startsWith("public")) {
			testName.append("WithOption");
			for (Object data : dataProvider) {
				if (data == null || data.toString().startsWith("public")) {
					break;
				}
				testName.append(General.capitalizeFirstLetters(General.removeNonWordCharacters(data.toString())));
			}
			currentName = testName.toString();
			if( currentName.length() > MAXFILENAMELENGTH ) {
				currentName = methodName + "_" + className + dataProvider.toString().split(";")[1];
			}
		}
		return currentName;
	}

	/**
	 * a function that breaks up a string, and places it into a map
	 * 
	 * @param input
	 *            - a string, with key and values separated by '=' and pairs
	 *            separated by '&'
	 * @return Map: a map with values
	 */
	public static Map<String, String> parseMap(final String input) {
		final Map<String, String> map = new HashMap<>();
		for (String pair : input.split("&")) {
			String[] kv = pair.split("=");
			map.put(kv[0], kv[1]);
		}
		return map;
	}
}