package huffman.encoder.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import huffman.encoder.misc.ConsoleFrame;
import huffman.encoder.misc.EChar;
import huffman.encoder.misc.Node;

public class Progressor extends JPanel implements PropertyChangeListener {
	/**
	 * @author Oge Tasie
	 * @Description This class handles all the underlying and difficult
	 *              processes. It is invoked by user input through the console
	 *              which is passed into the encoder driver. The encoder driver
	 *              ensures the right information is passed through before
	 *              allowing any processes to be called. Progressor contains
	 *              swing workers and attempts to show some form of visual
	 *              output back to the user. Arguments are passed through the
	 *              constructor of this class to decide what Swing worker is
	 *              invoked.
	 */
	private static final long serialVersionUID = 1L;

	// INITIALIZATION
	private static JFrame frame;
	private JProgressBar encoderPro;

	private String path;

	private Task1 launchEncoder;
	private Task2 loadFile;
	private Task3 encodeFile;
	private Task4 showStats;

	public ArrayList<Character> list;
	public ArrayList<Integer> freq;
	public ArrayList<Node> nodes;

	public PriorityQueue<Node> pq;

	private FileReader file;
	private BufferedReader reader;

	private ConsoleFrame eFrame;
	public EncoderDriver encoderDrv;

	private double uncompressedSize;
	private double compressedSize;

	// Task handlers.
	// Handles the initial run of the program.
	class Task1 extends SwingWorker<Void, Void> {
		Task1() {
		}

		protected Void doInBackground() throws Exception {
			Random rand = new Random();
			int progress = 0;
			setProgress(0);

			Thread.sleep(500 + rand.nextInt(1000));
			while (progress < 100) {
				Thread.sleep(rand.nextInt(50));
				progress += rand.nextInt(10);
				setProgress(Math.min(progress, 100));
			}
			return null;
		}

		// Load the encoder GUI
		public void done() {
			Progressor.this.setCursor(null);
			Progressor.frame.dispose();
			Progressor.this.eFrame = new ConsoleFrame();
			Progressor.this.eFrame.display();
		}
	}

	// Handles loading in a file.
	class Task2 extends SwingWorker<Void, Void> {

		private double count;
		private int y;
		private char temp;

		Task2() {
		}

		protected Void doInBackground() throws Exception {
			double increment = 0;
			count = 0;
			y = 0;

			setProgress(0);
			// Load in each line into an array list and get each unique
			// character.
			for (String next = reader.readLine(); next != null; next = reader.readLine()) {
				count++;
				for (int i = 0; i < next.length(); i++) {
					temp = next.charAt(i);
					if (!list.contains(temp)) {
						list.add(temp);
						freq.add(0);
					}
				}
			}
			reader.close();
			file.close();

			// Reinitialise the reader and file. This is where the character
			// frequencies are counted.
			Progressor.this.setPath(Progressor.this.path, Progressor.this.encoderDrv);
			// Get the frequency of the characters.
			for (String nextF = reader.readLine(); nextF != null; nextF = reader.readLine()) {
				for (int i = 0; i < nextF.length(); i++) {
					temp = nextF.charAt(i);
					for (int j = 0; j < list.size(); j++) {
						if (list.get(j).charValue() == temp) {
							y = freq.get(j).intValue();
							y++;
							freq.set(j, y);
						}
					}
				}
				increment++;
				Progressor.this.encoderPro.setString(NumberFormat.getPercentInstance().format((increment / count)));
			}
			reader.close();
			file.close();
			return null;
		}

		public void done() {
			// System output including some statistics on the files being
			// read. The list of nodes for creating the encoding book is
			Toolkit.getDefaultToolkit().beep();
			Progressor.this.setCursor(null);
			Progressor.frame.dispose();
			for (int i = 0; i < list.size(); i++) {
				Node node = new Node(null, null);
				node.addToList(list.get(i));
				node.setTotal(freq.get(i));
				nodes.add(node);
			}
			pq.addAll(nodes);
			// Testing the sort.
			// for (int i = 0; i < nodes.size(); i++) {
			// System.out.println(nodes.get(i).getTotal() + " " +
			// nodes.get(i).getList().toString());
			// System.out.println(pq.poll().getTotal());
			// }

			Progressor.this.encoderDrv.getP().reset();
			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML><br>~>Loaded " + Progressor.this.path + "<br>" + (int) count + " line(s).");
			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML><br>" + list.size() + " Character(s):<br>" + list.toString());
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML><br>Character Frequency:<br>" + freq.toString());
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML><br>Commands: load, encode, help, exit..");

			if (count == 0)
				Progressor.this.encoderDrv.getP()
						.outputParagraph("<HTML>~>You may not have permisson to read this file.");
		}
	}

	// Handles encoding the file
	class Task3 extends SwingWorker<Void, Void> {
		ArrayList<EChar> echars;
		String ecode;

		Task3() {
			echars = new ArrayList<EChar>();
		}

		public void createBook() {
			Node leftChild;
			Node rightChild;
			Node searchThis;
			int frequency = 0;
			ArrayList<Integer> bytes;

			// Binary value assignment.
			/**
			 * We want to now assign a binary value to each unique character.
			 * This is the second step we must take to fully encode our book.
			 */
			for (Character c : list) {
				// Search through the tree.
				// and assign the bit values.
				bytes = new ArrayList<Integer>();
				searchThis = nodes.get(nodes.size() - 1);
				while (searchThis != null) {
					leftChild = searchThis.getLeft();
					rightChild = searchThis.getRight();
					if (leftChild == null && rightChild == null) {
						frequency = searchThis.getTotal();
						searchThis = null;
					} else {
						if (leftChild.getList().contains(c)) {
							searchThis = leftChild;
							// 0 for left.
							bytes.add(0);
						} else if (rightChild.getList().contains(c)) {
							searchThis = rightChild;
							// 1 for right side.
							bytes.add(1);
						}
					}
				}
				EChar echar = new EChar(c, bytes);
				echar.setFrequency(frequency);
				echars.add(echar);
			}
			// Calculate the compressed size.
			compressedSize = 0;
			for (int i = 0; i < echars.size(); i++) {
				compressedSize = compressedSize + (echars.get(i).getCode().size() * echars.get(i).getFrequency());
			}
		}

		protected Void doInBackground() {
			Node leftChild;
			Node rightChild;

			setProgress(0);
			/**
			 * The first step of creating our encoding book is to arrange our
			 * characters from the lowest frequency of appearance. After which,
			 * we then combine characters together, always starting with the
			 * characters that appear the least.
			 */
			// This constructs our tree
			while (pq.size() != 1) {
				rightChild = pq.poll();
				leftChild = pq.poll();
				Node node = new Node(leftChild, rightChild);
				rightChild.setParent(node);
				leftChild.setParent(node);
				nodes.add(node);
				pq.add(node);
				// System.out.println("right " + rightParent.getTotal() + " left
				// " + leftParent.getTotal());
			}
			// As a precaution.
			Collections.sort(nodes);
			// create the Book;
			createBook();
			setProgress(100);
			return null;
		}

		public void done() {
			// Output the encoding book to the console.
			String book = "";
			Progressor.this.setCursor(null);
			Progressor.frame.dispose();
			for (int i = 0; i < this.echars.size(); i++) {
				book = book + this.echars.get(i).toString();
			}

			Progressor.this.encoderDrv.getP().reset();
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML><br>Code Book:");
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML><br>" + book);
			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML><br>Commands: load, encode, help, show stats, exit..");
		}
	}

	// Handles display statistics of the tree used to create the encoding book.
	class Task4 extends SwingWorker<Void, Void> {
		int numOfNodes = 0;
		int height = 1;
		double depth = 0;

		@Override
		protected Void doInBackground() throws Exception {
			Node searchThis;

			// Get the height of the tree
			searchThis = nodes.get(nodes.size() - 1);
			while (searchThis != null) {
				if (searchThis.getLeft() != null) {
					searchThis = searchThis.getLeft();
					height++;
				} else if (searchThis.getRight() != null) {
					searchThis = searchThis.getRight();
					height++;
				} else {
					searchThis = null;
				}
			}

			// to help get the compression ratio.
			uncompressedSize = 0;
			for (int i = 0; i < freq.size(); i++) {
				uncompressedSize += freq.get(i).intValue();
			}
			uncompressedSize = uncompressedSize * 8;
			numOfNodes = nodes.size();

			// Get the average depth of the tree.
			for (Node n : nodes) {
				int nodeDepth = 0;
				searchThis = n;
				while (searchThis != null) {
					if (searchThis.getParent() == null) {
						searchThis = null;
					} else {
						searchThis = searchThis.getParent();
						nodeDepth++;
					}
				}
				depth = depth + (double) nodeDepth;
			}
			depth = depth / (double) nodes.size();
			return null;
		}

		public void done() {
			double ratio;
			Progressor.this.setCursor(null);
			Progressor.frame.dispose();

			ratio = (compressedSize * 100.0) / uncompressedSize;

			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML><br>Average file size :- " + Math.round(uncompressedSize));
			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML>Average compression size:- " + Math.round(compressedSize));
			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML><br>Average compression :- " + Math.round(ratio) + "%");
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML><br>Number of Nodes :- " + numOfNodes);
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML>Node Tree Height :- " + height);
			Progressor.this.encoderDrv.getP().outputParagraph("<HTML>Average Node Depth :- " + (int) depth);
			Progressor.this.encoderDrv.getP()
					.outputParagraph("<HTML><br>Commands: load, encode, help, show stats, exit..");
		}

	}

	// CONSTRUCTOR
	public Progressor() {
		super(new BorderLayout());
		setBackground(Color.darkGray);

		this.encoderPro = new JProgressBar(0, 100);
		this.encoderPro.setValue(0);
		this.encoderPro.setBackground(Color.white);
		this.encoderPro.setStringPainted(true);

		JPanel panel = new JPanel();
		panel.setBackground(Color.darkGray);
		panel.add(this.encoderPro);

		add(panel, "First");
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		this.list = new ArrayList<Character>();
		this.freq = new ArrayList<Integer>();
		this.nodes = new ArrayList<Node>();

		pq = new PriorityQueue<Node>(new Comparator<Node>() {

			@Override
			public int compare(Node n1, Node n2) {
				return n1.compareTo(n2);
			}
		});
	}

	// A switch statement to switch between processes depending on the command
	// typed in
	public void load(int i) {
		switch (i) {
		case 1:
			this.encoderPro.setIndeterminate(true);
			setCursor(Cursor.getPredefinedCursor(3));
			this.launchEncoder = new Task1();
			this.launchEncoder.addPropertyChangeListener(this);
			this.launchEncoder.execute();
			break;
		case 2:
			this.encoderPro.setIndeterminate(true);
			list.clear();
			freq.clear();
			nodes.clear();
			pq.clear();
			setCursor(Cursor.getPredefinedCursor(3));
			this.loadFile = new Task2();
			this.loadFile.addPropertyChangeListener(this);
			this.loadFile.execute();
			break;
		case 3:
			this.encoderPro.setIndeterminate(true);
			setCursor(Cursor.getPredefinedCursor(3));
			this.encodeFile = new Task3();
			this.encodeFile.addPropertyChangeListener(this);
			this.encodeFile.execute();
			break;
		case 4:
			this.encoderPro.setIndeterminate(true);
			setCursor(Cursor.getPredefinedCursor(3));
			this.showStats = new Task4();
			this.showStats.addPropertyChangeListener(this);
			this.showStats.execute();
		}
	}

	// Setters and Getters
	public String getPath() {
		return path;
	}

	public void setPath(String path, EncoderDriver drv) throws FileNotFoundException {
		this.path = path;
		this.file = new FileReader(path);
		this.reader = new BufferedReader(file);
		this.encoderDrv = drv;
	}

	// Progress bar processes.
	// Changes the properties of the progress bar.
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = ((Integer) evt.getNewValue()).intValue();
			this.encoderPro.setIndeterminate(false);
			this.encoderPro.setValue(progress);
		}
	}

	// DISPLAY
	public void display() {
		frame = new JFrame("Loading...");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(3);

		JComponent newContentPane = this;
		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void loadEncoder(int i) {
		load(i);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Progressor.this.display();
			}
		});
	}
}
