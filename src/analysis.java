import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class analysis {

	private static File novel;
	private static ArrayList<String> wordList;
	private static HashMap<String, Integer> frequencies;
	private static PriorityQueue<String> heap;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		novel = new File("data/158.txt");
		try {
			loadFrequencies();
			System.out.println("Emma by Jane Austen has " + getTotalNumberOfWords() + " words");
			System.out.println("Emma by Jane Austen has " + getTotalUniqueWords() + " unique words");

			System.out.println("20 most frequent words in the novel: ");
			ArrayList<ArrayList<Object>> mostFreq = get20MostFrequentWords();
			for (ArrayList<Object> s : mostFreq) {
				System.out.println(" [\"" + s.get(0) + "\", " + s.get(1) + "]");
			}
			System.out.println();

			System.out.println("20 most frequent interesting words in the novel: ");
			ArrayList<ArrayList<Object>> mostFreq2 = get20MostFrequentInterestingWords();
			for (ArrayList<Object> s : mostFreq2) {
				System.out.println(" [\"" + s.get(0) + "\", " + s.get(1) + "]");
			}
			System.out.println();

			System.out.println("20 least frequent words in the novel: ");
			ArrayList<ArrayList<Object>> leastFreq = get20LeastFrequentWords();
			for (ArrayList<Object> s : leastFreq) {
				System.out.println(" [\"" + s.get(0) + "\", " + s.get(1) + "]");
			}
			System.out.println();

			System.out.println("The number of chapters is: " + countChapters());

			int[] chapters = new int[55];
			String word = "Knightley";
			chapters = getFrequencyOfWord(word);
			System.out.println("Frequency of the word\"" + word + "\" across chapters is:");
			for (int c : chapters) {
				System.out.print(c + ", ");
			}
			System.out.println();
			System.out.println();

			System.out.println("The quote \"She was the youngest of the two daughters\" appears in chapter "
					+ getChapterQuoteAppears("She was the youngest of the two daughters"));

			System.out.println();

			System.out.println(
					"The quote \"The charming Augusta Hawkins, in addition to all the usual advantages of perfect beauty and merit\" appears in chapter "
							+ getChapterQuoteAppears(
									"in addition to all the usual advantages of perfect beauty and merit"));

			System.out.println();
			System.out.println("A sentence that mimics the author's style: " + generateSentence());

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Load data
	 * 
	 * @throws IOException
	 */
	private static void loadData() throws IOException {

		// process the file
		InputStreamReader input = new InputStreamReader(new FileInputStream(novel));
		BufferedReader reader = new BufferedReader(input);
		String line;

		// initialize counters
		wordList = new ArrayList<String>();

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] words = line.split("\\s+");

				// add the words to word list
				for (int i = 0; i < words.length; i++) {
					String updated = "";
					for (int j = 0; j < words[i].length(); j++) {

						// eliminate non digit or letter characters
						if (Character.isLetterOrDigit(words[i].charAt(j))) {
							updated += words[i].charAt(j);
						}
					}
					wordList.add(updated);
				}
			}
		}
	}

	/**
	 * Calculate frequencies of each word
	 * 
	 * @throws IOException
	 */
	private static void loadFrequencies() throws IOException {
		loadData();
		frequencies = new HashMap<String, Integer>();
		for (int i = 0; i < wordList.size(); i++) {
			if (!frequencies.containsKey(wordList.get(i))) {
				frequencies.put(wordList.get(i), 1);
			} else {
				frequencies.replace(wordList.get(i), frequencies.get(wordList.get(i)) + 1);
			}
		}
	}

	/**
	 * Get total number of words of a novel
	 * 
	 * @param novel
	 *            the novel
	 * @return the number of words
	 * @throws IOException
	 */
	public static int getTotalNumberOfWords() throws IOException {

		// load data
		loadData();

		// return
		return wordList.size();
	}

	/**
	 * Get total unique words
	 * 
	 * @return
	 * @throws IOException
	 */
	public static int getTotalUniqueWords() throws IOException {

		// return unique words
		return frequencies.keySet().size();
	}

	/**
	 * Set up a max heap
	 * 
	 * @param order
	 */
	private static void setUpHeap(int order) {
		heap = new PriorityQueue<String>(frequencies.keySet().size(),
				(word1, word2) -> (order * (frequencies.get(word1).compareTo(frequencies.get(word2)))));

		// loop through the word list
		for (int i = 0; i < wordList.size(); i++) {
			heap.add(wordList.get(i));
		}
	}

	/**
	 * Get 20 frequent words with specified order
	 * 
	 * @param order
	 * @return
	 * @throws IOException
	 */
	private static ArrayList<ArrayList<Object>> get20FrequentWords(int order) throws IOException {

		// load data
		loadData();

		// set up heap
		setUpHeap(order);

		// keep result
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

		HashSet<String> visited = new HashSet<String>();

		int i = 0;
		while (i < 20) {
			String word = heap.poll();
			if (!visited.contains(word)) {
				visited.add(word);
				ArrayList<Object> temp = new ArrayList<Object>();
				temp.add(word);
				temp.add(frequencies.get(word));
				result.add(temp);
				i++;
			}
		}
		return result;
	}

	/**
	 * Get 20 most frequent words (with a max heap)
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<Object>> get20MostFrequentWords() throws IOException {
		return get20FrequentWords(-1);
	}

	/**
	 * Get 20 least frequent words (with a min heap)
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<Object>> get20LeastFrequentWords() throws IOException {
		return get20FrequentWords(1);
	}

	/**
	 * Get 20 most interesting words
	 * 
	 * @return
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<Object>> get20MostFrequentInterestingWords() throws IOException {

		// load data
		loadData();

		// set up heap
		setUpHeap(-1);

		// keep result
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

		// keep track of visited words in word list
		HashSet<String> visited = new HashSet<String>();

		// poll the first 100 words
		int j = 0;
		while (j < 1000) {
			String temp = heap.poll();
			if (!visited.contains(temp)) {
				visited.add(temp);
				j++;
			}
		}

		// poll the next 20 words
		int i = 0;
		while (i < 20) {
			String word = heap.poll();
			if (!visited.contains(word)) {
				visited.add(word);
				ArrayList<Object> temp = new ArrayList<Object>();
				temp.add(word);
				temp.add(frequencies.get(word));
				result.add(temp);
				i++;
			}
		}
		return result;
	}

	/**
	 * Count chapters
	 * 
	 * @return
	 * @throws IOException
	 */
	private static int countChapters() throws IOException {

		// load data
		loadData();

		// result
		int numChapters = 0;

		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).equals("CHAPTER")) {
				numChapters++;
			}
		}
		return numChapters;
	}

	/**
	 * Get frequency of a given word across chapters
	 * 
	 * @param word
	 * @return
	 * @throws IOException
	 */
	public static int[] getFrequencyOfWord(String word) throws IOException {

		// load data
		loadData();

		// keep result
		int[] result = new int[countChapters()];

		// loop through the chapters
		int i = 0;
		int chapterNum = -1;
		while (i < wordList.size()) {
			if (wordList.get(i).equals("CHAPTER")) {

				chapterNum++;

				// in a chapter
				int frequency = 0;
				int j = i + 1;
				while (j < wordList.size() && !wordList.get(j).equals("CHAPTER")) {
					if (wordList.get(j).equals(word)) {
						frequency++;
					}
					j++;
				}
				result[chapterNum] = frequency;
				i = j;
			} else {
				i++;
			}
		}
		return result;

	}

	public static int getChapterQuoteAppears(String quote) throws IOException {

		// load data
		loadData();

		// keep result
		int result = 0;

		// keep the quote in an array of string
		String[] words = quote.split("\\s+");

		// loop through the chapters
		int chapterNum = -1;
		int i = 0;
		while (i < wordList.size()) {
			if (wordList.get(i).equals("CHAPTER")) {
				chapterNum++;

				// in a chapter
				int j = i + 1;
				ArrayList<String> chapter = new ArrayList<String>();
				while (j < wordList.size() && !wordList.get(j).equals("CHAPTER")) {
					chapter.add(wordList.get(j));
					j++;
				}

				// update pointer
				i = j;

				// use kmp
				int[] lps = computeTemporaryArray(chapter, words);
				int index = 0;
				int count = 0;
				while (index < chapter.size() && count < words.length) {
					if (chapter.get(index).equals(words[count])) {
						index++;
						count++;
					} else {
						if (count != 0) {
							count = lps[count - 1];
						} else {
							index++;
						}
					}
				}
				if (count == words.length) {
					return chapterNum + 1;
				}
			} else {
				i++;
			}
		}
		return -1;
	}

	/**
	 * Compute temporary array to maintain size of suffix which is same as prefix
	 * 
	 * @param chapter
	 * @param quote
	 * @return
	 */
	private static int[] computeTemporaryArray(ArrayList<String> chapter, String[] quote) {

		int[] lps = new int[quote.length];
		int index = 0;
		for (int i = 1; i < quote.length; i++) {
			if (chapter.get(i) == quote[index]) {
				lps[i] = index + 1;
				i++;
				index++;
			} else {
				if (index != 0) {
					index = lps[index - 1];
				} else {
					index = 0;
				}
			}
		}

		return lps;
	}

	/**
	 * Generate a sentence in the author's style
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String generateSentence() throws IOException {

		// load data
		loadData();

		// keep the sentence
		String sentence = "The ";

		// current word
		String current = "The";

		// process 20 times to get 20 words
		for (int j = 0; j < 20; j++) {

			// store the list of word after the current
			ArrayList<String> potentials = new ArrayList<String>();

			// loop through the list to get potential words
			int i = 0;
			while (i < wordList.size()) {
				if (wordList.get(i).equals(current)) {
					if (i + 1 < wordList.size()) {
						potentials.add(wordList.get(i + 1));
					}
				}
				i++;
			}

			// choose the word with the highest frequency in the potential list
			int maxFreq = Integer.MIN_VALUE;
			for (String s : potentials) {
				if (frequencies.get(s) > maxFreq) {
					current = s;
				}
			}
			sentence += current + " ";
		}

		return sentence;

	}

	// public static ArrayList<ArrayList<Object>> get20MostFrequentWordHashMap(File
	// novel) throws IOException {
	//
	// // keep result
	// ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
	//
	// // keep track of frequency of each unique word
	// HashMap<String, Integer> map = new HashMap<String, Integer>();
	//
	// // count visited words
	// for (int i = 0; i < wordList.size(); i++) {
	// if (!map.containsKey(wordList.get(i))) {
	// map.put(wordList.get(i), 1);
	// } else {
	// map.replace(wordList.get(i), map.get(wordList.get(i)) + 1);
	// }
	//
	// }
	//
	// Collection<Integer> values = map.values();
	// ArrayList<Integer> frequencies = new ArrayList<Integer>(values);
	// Collections.sort(frequencies, Collections.reverseOrder());
	//
	// Set<String> keys = map.keySet();
	//
	// for (int i = 0; i < 20; i++) {
	//
	// for (String s : keys) {
	//
	// if (map.get(s) == frequencies.get(i)) {
	// ArrayList<Object> temp = new ArrayList<Object>();
	// temp.add(s);
	// temp.add(frequencies.get(i));
	// result.add(temp);
	// break;
	// }
	// }
	// }
	//
	// return result;
	// }

}
