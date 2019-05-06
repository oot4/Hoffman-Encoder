/**
 * 
 */
package huffman.encoder.misc;

import java.util.ArrayList;

/**
 * @author Oge Tasie
 * @Description This class represents an encoded character.
 *
 */
public class EChar {
	char uniqueChar;
	int frequency;
	ArrayList<Integer> code;

	public EChar(char unique, ArrayList<Integer> bytes) {
		this.uniqueChar = unique;
		this.code = new ArrayList<Integer>();
		this.setCode(bytes);
	}

	// Getters and Setters
	public char getUniqueChar() {
		return uniqueChar;
	}

	public void setUniqueChar(char uniqueChar) {
		this.uniqueChar = uniqueChar;
	}

	public ArrayList<Integer> getCode() {
		return code;
	}

	public void setCode(ArrayList<Integer> code) {
		this.code = code;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String toString() {
		String s = "";
		s = s + this.uniqueChar + "=";
		for (Integer i : this.code) {
			s = s + i;
		}
		return s + "-$- ";
	}
}
