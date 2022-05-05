import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	private int order;
	private HashMap<String, int[]> map;
	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		map = new HashMap<>();
		for (int i = 0; i < text.length() - order; i++) {
			String substring = (text.substring(i, i + order));
			int character = text.charAt(i + order);
			if (map.get(substring) == null) {
				map.put(substring, new int[255]);
				int[] arr = map.get(substring);
				for (int j = 0; j < arr.length; j++) {
					arr[j] = 0;
				}
				arr[character] += 1;
			} else {
				int[] arr = map.get(substring);
				arr[character] += 1;
			}
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		if (map.get(kgram) == null) {
			return 0;
		} else {
			int[] arr = map.get(kgram);
			int count = 0;
			for (int i = 0; i < arr.length; i++) {
				count += arr[i];
			}
			return count;
		}
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		if (map.get(kgram) == null) {
			return  0;
		} else {
			int[] arr = map.get(kgram);
			return arr[c];
		}
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (map.get(kgram) == null) {
			return NOCHARACTER;
		} else {
			int[] arr = map.get(kgram);
			int count = 0;
			for (int i = 0; i < arr.length; i++) {
				count += arr[i];
			}
			int generated = generator.nextInt(count);
			for (int j = 0; j < arr.length; j++) {
				generated -= arr[j];
				if (generated < 0) {
					return (char) j;
				}
			}
			return NOCHARACTER;
		}
	}
}
