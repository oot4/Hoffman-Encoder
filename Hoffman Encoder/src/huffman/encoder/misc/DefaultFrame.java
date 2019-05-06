package huffman.encoder.misc;

import javax.swing.JFrame;

/**
 * @author Oge Tasie
 * @ClassDescription This class is used to define the default frame setup, a
 *                   temporary setup in case any changes are made.
 *
 */
public class DefaultFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public DefaultFrame() {
		setSize(350, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
	}

	public void showIt() {
		setVisible(true);
	}

	public void hideIt() {
		setVisible(false);
	}
}
