package huffman.encoder.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import huffman.encoder.misc.EncoderCommands;
import huffman.encoder.misc.UserString;

/**
 * @author Oge Tasie
 * @ClassDescription This is where the important methods of the application are
 *                   called. Also handles user inputs, system outputs and, file
 *                   reading and editing.
 *
 */
public class ConsolePanel extends JPanel implements KeyListener, EncoderCommands {
	private static final long serialVersionUID = 1L;

	// INITIALIZATION
	private JLabel infoIn;
	private JLabel infoOut;

	private String strUser;
	private String strCursor;

	private UserString userChars;
	private StringBuilder sb;

	public EncoderDriver drv;
	private int lnCnt;

	// Setup the console.
	public ConsolePanel() {
		// Define size and colour scheme.
		super(new BorderLayout());
		setSize(650, 750);
		setBackground(Color.darkGray);
		setFocusable(true);

		// Set components in place.
		this.setInfoOut(new JLabel());
		this.getInfoOut().setForeground(Color.white);
		this.getInfoOut().setFont(new Font("Proggy", Font.BOLD, 12));
		this.infoIn = new JLabel();
		this.infoIn.setForeground(Color.white);
		this.infoIn.setFont(new Font("Proggy", Font.PLAIN, 12));

		// Arrangement.
		add(this.getInfoOut(), "North");
		add(this.infoIn, "South");
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setVisible(true);

		// Initialise variables
		this.strCursor = "~>";
		this.userChars = new UserString();
		this.sb = new StringBuilder();
		this.lnCnt = 0;

		// Load driver.
		this.drv = new EncoderDriver();
	}

	// Initialise important and useful objects and variables.
	public void init() {
		outputParagraph("Welcome to Huffman Encoder.");
		outputParagraph("Type 'help' for available commands and their usage.");
		this.infoIn.setText("<HTML>" + this.getStrCursor());
		addKeyListener(this);
	}

	// KEY EVENT HANDLES
	// Handles user input in the console window.
	public void handleKey(KeyEvent e) throws FileNotFoundException {
		if (((e.getKeyCode() == 13 ? 1 : 0) | (e.getKeyCode() == 10 ? 1 : 0)) != 0) {
			enter();
		} else if (e.getKeyCode() == 8) {
			backspace();
		} else if (e.getKeyChar() != 65535) {
			print(String.valueOf(e.getKeyChar()));
		}
	}

	// Backspace Functionality.
	private void backspace() {
		if (!this.userChars.isEmpty()) {
			this.userChars.remove(this.userChars.size() - 1);
			print();
		}
	}

	// Confirm functionality.
	private void enter() throws FileNotFoundException {
		// If the help command is called.
		switch (strUser.toLowerCase()) {
		case "help":
			if (this.lnCnt > 15) {
				reset();
				output();
			} else {
				output();
			}
			help();
			break;
		case "exit":
			exit();
			break;
		case "load":
			if (this.lnCnt > 15) {
				reset();
				output();
			} else {
				output();
			}
			load(this);
			break;
		case "encode":
			if (this.lnCnt > 15) {
				reset();
				output();
			} else {
				output();
			}
			encode(this);
			break;
		case "show stats":
			if (this.lnCnt > 15) {
				reset();
				output();
			} else {
				output();
			}
			showStats(this);
			break;
		default:
			notRecognized();
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		try {
			handleKey(e);
		} catch (FileNotFoundException e1) {
			System.out.println("File is inaccessabile.");
			e1.printStackTrace();
		}
	}

	// PRINT HANDLES
	// Feedback to the user as they input text.
	@SuppressWarnings("unchecked")
	private void print(String valueOf) {
		if (this.userChars.size() > 50) {
			this.userChars.clear();
		}
		this.userChars.add(valueOf);
		print();
	}

	// DISPLAY
	void outputParagraph(String s) {
		if (this.lnCnt > 15) {
			reset();
			getSb().append("<HTML>" + s + "<br>");
			this.getInfoOut().setText(getSb().toString());
			this.lnCnt++;
		} else {
			getSb().append("<HTML>" + s + "<br>");
			this.getInfoOut().setText(getSb().toString());
			this.lnCnt++;
		}
	}

	// Print to the user input label.
	private void print() {
		this.strUser = this.userChars.toString();
		this.infoIn.setText("<HTML>" + this.getStrCursor() + this.strUser);
		repaint();
	}

	// print to the systems label.
	private void output() {
		getSb().append("<HTML><br>" + "~#" + userChars.toString());
		this.getInfoOut().setText(getSb().toString());
		this.userChars.clear();
		this.infoIn.setText("<HTML>" + this.getStrCursor());
		this.lnCnt++;
	}

	// PROGRAM CONTROL
	void reset() {
		// Reset the string builder and line counter.
		getSb().setLength(0);
		setSb(new StringBuilder());
		lnCnt = 0;
	}

	// On input error
	private void notRecognized() {
		outputParagraph("Command not recognized");
		userChars.clear();
		this.infoIn.setText("<HTML>" + this.getStrCursor());
	}

	// Help information
	public void help() {
		reset();
		outputParagraph("--------HELP--------");
		outputParagraph("'help' ~ Show all available commands.");
		outputParagraph("'load' ~ Load a file.");
		outputParagraph("'encode' ~ Encodes the current file using Hoffmans Encoding.");
		outputParagraph("'show stats' ~ Show file encoding stats of the current file.");
		outputParagraph("'exit' ~ Terminates the program.");
	}

	// ENCODER COMMANDS.
	@Override
	public void load(ConsolePanel panel) throws FileNotFoundException {
		this.drv.load(panel);
	}

	@Override
	public void encode(ConsolePanel panel) {
		this.drv.encode(panel);
	}

	@Override
	public void exit() {
		this.drv.exit();
	}

	@Override
	public void showStats(ConsolePanel panel) {
		this.drv.showStats(panel);
	}

	// Setters and Getters
	public JLabel getInfoOut() {
		return infoOut;
	}

	public void setInfoOut(JLabel infoOut) {
		this.infoOut = infoOut;
	}

	public StringBuilder getSb() {
		return sb;
	}

	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}

	public String getStrCursor() {
		return strCursor;
	}

	public void setStrCursor(String strCursor) {
		this.strCursor = strCursor;
	}
}
