package huffman.encoder.components;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import huffman.encoder.misc.EncoderCommands;

/**
 * @author Oge Tasie
 * @Description This class acts as a coordinator for all operations between the
 *              system and the user.
 */
public class EncoderDriver implements EncoderCommands {
	// INITIALIZE
	boolean canEncode = false;
	boolean canShowStats = false;
	public JFileChooser c;

	private File file;
	private FileNameExtensionFilter f;

	private ConsolePanel p;
	private Progressor progressor;

	private String path = "";
	private ArrayList<String> soChars = new ArrayList<String>();

	Random rand = new Random();

	// Setup the driver.
	public EncoderDriver() {
		this.c = new JFileChooser();
		// Set a filter to only allow text files to open.
		this.f = new FileNameExtensionFilter("*.txt", "txt", "text");
		// Filter out all other files.
		this.c.setFileFilter(this.f);

		// Initialise progressor.
		this.progressor = new Progressor();
	}

	/**
	 * We need to load the file and read all the characters in to a string which
	 * we can then encode. This method will handle these requirements.
	 * 
	 * @throws FileNotFoundException
	 */

	// ENCODER COMMANDS
	@Override
	public void load(ConsolePanel panel) throws FileNotFoundException {
		// INITIALIZE
		this.setP(panel);
		int val = c.showOpenDialog(null);

		this.canShowStats = false;
		// Handles the file selected by the user.
		if (val == JFileChooser.APPROVE_OPTION) {
			this.file = c.getSelectedFile();
			this.path = file.getAbsolutePath();

			this.progressor.setPath(this.path, this);
			this.progressor.loadEncoder(2);
			this.canEncode = true;

			// Testing
			// System.out.println(this.getSoChars().size());
		} else {
			this.getP().outputParagraph("<HTML><br>" + getP().getStrCursor() + "Load command cancelled.");
		}
	}

	@Override
	public void encode(ConsolePanel panel) {
		// INITIALISE
		this.setP(panel);
		if (this.canEncode == false) {
			this.getP()
					.outputParagraph("<HTML><br>There is no file to encode. Load in a file using the 'load' command.");
		} else {
			this.canShowStats = true;
			this.getP().outputParagraph("<HTML><br>Encoding the current file...");
			this.progressor.loadEncoder(3);
		}
	}

	// Exits the program.
	@Override
	public void exit() {
		System.exit(0);
		return;
	}

	@Override
	public void showStats(ConsolePanel panel) {
		this.setP(panel);
		if (this.canShowStats == false) {
			this.getP().outputParagraph(
					"<HTML><br>There are no statistics to show. Load in a file using the 'load' command and Encode it using the 'encode' command.");
		} else {
			this.progressor.loadEncoder(4);
		}
	}

	// Getters and Setters
	public ConsolePanel getP() {
		return p;
	}

	public void setP(ConsolePanel p) {
		this.p = p;
	}

	public ArrayList<String> getSoChars() {
		return soChars;
	}

	public void setSoChars(ArrayList<String> soChars) {
		this.soChars = soChars;
	}
}