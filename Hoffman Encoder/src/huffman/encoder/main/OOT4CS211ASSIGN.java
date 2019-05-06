package huffman.encoder.main;

import huffman.encoder.components.Progressor;

/**
 * @author Oge Tasie
 * @description A Huffman encoding software that decodes and encodes text
 *              messages. This program has been created to demonstrate the
 *              efficiency of the Huffman Encoding Algorithm.
 * 
 *              The objectives are taken from the assignment sheet provided:
 * 
 * 
 *              "The objective is to implement the Huffman encoding scheme (see
 *              CS10720 lecture notes, pages 178ff) for encoding a message in a
 *              sequence of characters/symbols with the shortest possible
 *              average number of bits per character/symbol and thus minimum
 *              number of bits for the total message.
 * 
 *              (i) Determining the frequency of the occurrence of each symbol
 *              inside the message.
 * 
 *              (ii) Ranking the symbols in order of increasing frequency.
 * 
 *              (iii) Combining the two lowest frequency symbols into a single
 *              composite symbol for each step. The frequency of this new symbol
 *              is therefore the sum of the two original ones and the process is
 *              then repeated until a single composite symbol remains.
 * 
 *              (iv) Starting at the final symbol, it breaks-up the composite
 *              symbols, assigns 0 to the left branch and 1 to the right branch
 *              and then creates a Huffman binary tree.
 * 
 *              (v) The binary string from the root node to the leaf node will
 *              be used to encode the symbol at the leaf node."
 * 
 * 
 *              (Taken from CS211020 Assignment Document.
 * 
 *              Authors@ Yonghuai Liu, Christine Zarges, and Neil Mac Parthalain
 * 
 *              Date@ 10th November 2016)
 * 
 * @email OOT4@aber.ac.uk
 * @date 10th november 2016
 *
 */
public class OOT4CS211ASSIGN {

	/**
	 * @ClassDescription The main method is called from here to start the
	 *                   application.
	 */
	public static void main(String[] args) {
		// Initialise application.
		Progressor progressBar = new Progressor();
		progressBar.loadEncoder(1);
	}
}
