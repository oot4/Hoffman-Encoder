
package huffman.encoder.misc;

import java.util.Vector;

/**
 * @author Oge Tasie
 * @Description Stores a set of user input characters. For Input purposes only
 * 
 */
@SuppressWarnings("rawtypes")
public class UserString extends Vector {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void fromString(String[] p) {
		for (int i = 0; i < p.length; i++) {
			add(p[i]);
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			s.append(get(i));
		}
		return s.toString();
	}
}
