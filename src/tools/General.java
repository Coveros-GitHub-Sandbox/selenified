package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class General {

	GeneralFunctions gen = new GeneralFunctions();

	/**
	 * a method to recursively retrieve all the files in a folder
	 * 
	 * @param folder:
	 *            the folder to check for files
	 * @return ArrayList<String>: an ArrayList with the of multiple files
	 * @throws IOException
	 *             - an IOException
	 */
	public ArrayList<String> listFilesForFolder(File folder) {
		ArrayList<String> files = new ArrayList<String>();
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				files.addAll(listFilesForFolder(fileEntry));
			} else {
				files.add(fileEntry.getPath());
			}
		}
		return files;
	}

	public Object[] removeElements(Object[] input, Object deleteMe) {
		ArrayList<Object> result = new ArrayList<Object>();

		for (Object item : input)
			if (!deleteMe.equals(item))
				result.add(item);

		return result.toArray(input);
	}

	/**
	 * A function to right pad the input value with spaces
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public String padRightSpace(String input, int length) {
		return padRight(input, length, " ");
	}

	/**
	 * A function to right pad the input value with zeros
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public String padRightZeros(long input, int length) {
		return padRight(String.valueOf(input), length, "0");
	}

	/**
	 * A function to right pad the input value with zeros
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public String padRightZeros(int input, int length) {
		return padRight(String.valueOf(input), length, "0");
	}

	/**
	 * A function to right pad the input value with a value
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public String padRight(int input, int length, String value) {
		return padRight(String.valueOf(input), length, value);
	}

	/**
	 * A function to right pad the input value with a value
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the right
	 */
	public String padRight(String input, int length, String value) {
		if (input == null) {
			input = "";
		}
		while (input.length() < length) {
			input = input + value;
		}
		return input;
	}

	/**
	 * A function to left pad the input value with spaces
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public String padLeftSpace(String input, int length) {
		return padLeft(input, length, " ");
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public String padLeftZeros(long input, int length) {
		return padLeft(String.valueOf(input), length, "0");
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public String padLeftZeros(int input, int length) {
		return padLeft(String.valueOf(input), length, "0");
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public String padLeft(int input, int length, String value) {
		return padLeft(String.valueOf(input), length, value);
	}

	/**
	 * A function to left pad the input value with zeros
	 * 
	 * @param input:
	 *            the value to be added
	 * @param length:
	 *            the final desired length
	 * @return string: the input supplied with zeros on the left
	 */
	public String padLeft(String input, int length, String value) {
		if (input == null) {
			input = "";
		}
		while (input.length() < length) {
			input = value + input;
		}
		return input;
	}

	/**
	 * Generates a random character
	 * 
	 * @return random character
	 */
	public String getRandomChar() {
		return getRandomString(1);
	}

	/**
	 * Generates a random string with 32 alpha-numeric characters
	 * 
	 * @return random string of 32 characters
	 */
	public String getRandomString() {
		return getRandomString(32);
	}

	/**
	 * Generates a random string of alpha-numeric characters
	 * 
	 * @param length
	 *            the length of the random string
	 * @return random string of characters
	 */
	public String getRandomString(int length) {
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
	public String getRandomInt() {
		return getRandomInt(9);
	}

	/**
	 * Generates a random string of numbers
	 * 
	 * @param length
	 *            the length of the random string
	 * @return random string of numbers
	 */
	public String getRandomInt(int length) {
		String stringChars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(stringChars.charAt(rnd.nextInt(stringChars.length())));
		}
		return sb.toString();
	}

	/**
	 * Returns a copy of the object, or null if the object cannot be serialized.
	 * 
	 * @param orig
	 *            - the object to copy
	 * @return Object - a copy of the original
	 */
	public Object copy(Object orig) {
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(orig);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return obj;
	}

	public String removeNonWordCharacters(String value) {
		return value.replaceAll("[^a-zA-Z0-9]+", "");
	}

	/**
	 * Copies a directory and all of the contained files
	 * 
	 * @param srcPath
	 *            - the source directory to copy
	 * @param dstPath
	 *            - the destination to copy the folder to
	 * @throws IOException
	 *             - an IOException
	 */
	public void copyDirectory(File srcPath, File dstPath) throws IOException {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdir();
			}

			String files[] = srcPath.list();

			for (int i = 0; i < files.length; i++) {
				copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]));
			}
		} else {
			if (!srcPath.exists()) {
				return;
			} else {
				InputStream in = new FileInputStream(srcPath);
				OutputStream out = new FileOutputStream(dstPath);
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
	}

	public int copyFile(File oldFile, File newFile) {
		if (!oldFile.exists()) {
			return 1;
		} else {
			try {
				InputStream in = new FileInputStream(oldFile);
				OutputStream out = new FileOutputStream(newFile);
				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
				return 0;
			} catch (Exception e) {
				return 1;
			}
		}
	}

	public int deleteFile(String fileName) {
		File thisFile = new File(fileName);
		if (!thisFile.exists()) {
			return 1;
		}
		if (!thisFile.delete()) {
			return 1;
		}
		return 0;
	}

	public boolean doesValueStartWithAnyArrayEntry(String[] array, String value) {
		for (String entry : array) {
			if (value.startsWith(entry)) {
				return true;
			}
		}
		return false;
	}

	public boolean doesValueEndWithAnyArrayEntry(String[] array, String value) {
		for (String entry : array) {
			if (value.endsWith(entry)) {
				return true;
			}
		}
		return false;
	}

	public boolean doesArrayContain(String[] array, String value) {
		return Arrays.asList(array).contains(value);
	}

	public boolean doesArrayContain(Object[] array, Object value) {
		return Arrays.asList(array).contains(value);
	}

	/**
	 * A function to reverse a string
	 * 
	 * @param source
	 *            - the string to reverse
	 * @return - the reversed string
	 */
	public String reverseIt(String source) {
		int i, len = source.length();
		StringBuffer dest = new StringBuffer(len);

		for (i = (len - 1); i >= 0; i--) {
			dest.append(source.charAt(i));
		}
		return dest.toString();
	}

	/**
	 * this method will take in a long string with no spaces and change the
	 * camel case to spaces, including ensuring the first word is capitalized
	 * 
	 * @param s
	 *            our input string
	 * @return our fixed string
	 */
	public String wordToSentence(String s) {
		s = capitalizeFirstLetters(s);
		s = s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
		return s;
	}

	/**
	 * a function to capitalize the first letter of each word in the provided
	 * string
	 * 
	 * @param s
	 *            the string to be capitalized on
	 * @return String: the new string
	 */
	public String capitalizeFirstLetters(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				// Capitalize the first letter of the string.
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
			}
			// Is this character a non-letter or non-digit? If so
			// then this is probably a word boundary so let's capitalize
			// the next character in the sequence.
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)),
							s.substring(i + 2));
				}
			}
		}
		return s;
	}
}