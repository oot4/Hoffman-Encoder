package huffman.encoder.misc;

import javax.swing.JComponent;

import huffman.encoder.components.ConsolePanel;

/**
 * @author Oge Tasie
 * @ClassDescription This is the frame that encloses the applications panel. The
 *                   panel is where general processes are carried out.
 *
 */
public class ConsoleFrame extends DefaultFrame {
	private static final long serialVersionUID = 1L;
	// INITIALIZATION
	private ConsolePanel consolePanel = new ConsolePanel();

	// Setup the Console frame.
	public ConsoleFrame() {
		setTitle("Huffman Encoder OOT4");
		add(this.consolePanel);
		setDefaultCloseOperation(2);
	}

	// DISPLAY
	public void display() {

		JComponent newContentPane = this.consolePanel;
		newContentPane.setOpaque(true);
		setContentPane(newContentPane);

		pack();
		setSize(650, 750);
		setLocationRelativeTo(null);
		setVisible(true);

		// Initialise the console panel.
		this.consolePanel.init();
	}
}
