package huffman.encoder.misc;

import java.io.FileNotFoundException;

import huffman.encoder.components.ConsolePanel;

/**
 * @author Oge Tasie
 * @Description This interface provides the application with standard commands
 *              for triggering operations.
 */
public interface EncoderCommands {
	/*
	 * Loads in a text file to perform actions on it. It should only accept text
	 * files. All characters from the text file should be stored in the system.
	 */
	void load(ConsolePanel panel) throws FileNotFoundException;

	/*
	 * This method should encode the currently loaded file using Hoffman
	 * Encoding and store statistics based on the file operations. The user
	 * should be able to see the statistics by calling the showStats method.
	 */
	void encode(ConsolePanel panel);

	/*
	 * This method shows relevant stats based on the operations carried out on
	 * the currently loaded file. The results should also be display. to the
	 * user.
	 */
	void showStats(ConsolePanel panel);

	/*
	 * Exits the program when called.
	 */
	void exit();
}
