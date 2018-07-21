import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class analysis {

	private static File novel;
	private static InputStreamReader input;
	private static BufferedReader reader;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		novel = new File("158.txt");
		try {

			System.out.println("Emma by Jane Austen has " + getTotalNumberOfWords() + " words");
			System.out.println("Emma by Jane Austen has " + getTotalUniqueWords() + " unique words");

			System.out.println("20 most frequent words in the novel: ");
			ArrayList<ArrayList<Object>> mostFreq = get20MostFrequentWords(novel);
			for (ArrayList<Object> s : mostFreq) {
				System.out.println(" [\"" + s.get(0) + "\", " + s.get(1) + "]");
			}
			System.out.println();

			System.out.println("20 most frequent interesting words in the novel: ");
			ArrayList<ArrayList<Object>> mostFreq2 = get20MostFrequentInterestingWords(novel);
			for (ArrayList<Object> s : mostFreq2) {
				System.out.println(" [\"" + s.get(0) + "\", " + s.get(1) + "]");
			}
			System.out.println();

			System.out.println("20 least frequent words in the novel: ");
			ArrayList<ArrayList<Object>> leastFreq = get20LeastFrequentWords(novel);
			for (ArrayList<Object> s : leastFreq) {
				System.out.println(" [\"" + s.get(0) + "\", " + s.get(1) + "]");
			}
			System.out.println();

			System.out.println("The number of chapters is: " + countChapters());
		} catch (IOException ioe) {
			ioe.printStackTrace();
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

		// process the file
		input = new InputStreamReader(new FileInputStream(novel));
		reader = new BufferedReader(input);

		String line;

		// initialize counters
		int countWords = 0;

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] wordList = line.split("\\s+");

				countWords += wordList.length;
			}
		}

		return countWords;
	}

	public static int getTotalUniqueWords() throws IOException {

		input = new InputStreamReader(new FileInputStream(novel));
		reader = new BufferedReader(input);

		String line;

		// keep track of visited words
		HashSet<String> visited = new HashSet<String>();

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] wordList = line.split("\\s+");

				// count only visited words
				for (int i = 0; i < wordList.length; i++) {
					if (!visited.contains(wordList[i])) {
						visited.add(wordList[i]);
					}
				}
			}
		}

		return visited.size();
	}

	public static ArrayList<ArrayList<Object>> get20MostFrequentWords(File novel) throws IOException {

		// process the file
		FileInputStream fileStream = new FileInputStream(novel);
		InputStreamReader input = new InputStreamReader(fileStream);
		BufferedReader reader = new BufferedReader(input);

		String line;

		// keep result
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

		// keep track of frequency of each unique word
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] wordList = line.split("\\s+");

				// count visited words
				for (int i = 0; i < wordList.length; i++) {
					if (!map.containsKey(wordList[i])) {
						map.put(wordList[i], 1);
					} else {
						map.replace(wordList[i], map.get(wordList[i]) + 1);
					}
				}
			}
		}

		Collection<Integer> values = map.values();
		ArrayList<Integer> frequencies = new ArrayList<Integer>(values);
		Collections.sort(frequencies, Collections.reverseOrder());

		Set<String> keys = map.keySet();

		for (int i = 0; i < 20; i++) {

			for (String s : keys) {

				if (map.get(s) == frequencies.get(i)) {
					ArrayList<Object> temp = new ArrayList<Object>();
					temp.add(s);
					temp.add(frequencies.get(i));
					result.add(temp);
					break;
				}
			}
		}

		return result;
	}

	public static ArrayList<ArrayList<Object>> get20MostFrequentInterestingWords(File novel) throws IOException {
		// process the file
		input = new InputStreamReader(new FileInputStream(novel));
		reader = new BufferedReader(input);

		String line;

		// keep result
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

		// keep track of frequency of each unique word
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] wordList = line.split("\\s+");

				// count visited words
				for (int i = 0; i < wordList.length; i++) {
					if (!map.containsKey(wordList[i])) {
						map.put(wordList[i], 1);
					} else {
						map.replace(wordList[i], map.get(wordList[i]) + 1);
					}
				}
			}
		}

		Collection<Integer> values = map.values();
		ArrayList<Integer> frequencies = new ArrayList<Integer>(values);
		Collections.sort(frequencies, Collections.reverseOrder());

		Set<String> keys = map.keySet();

		for (int i = 100; i < 120; i++) {

			for (String s : keys) {

				if (map.get(s) == frequencies.get(i)) {
					ArrayList<Object> temp = new ArrayList<Object>();
					temp.add(s);
					temp.add(frequencies.get(i));
					result.add(temp);
					break;
				}
			}
		}

		return result;
	}

	public static ArrayList<ArrayList<Object>> get20LeastFrequentWords(File novel) throws IOException {

		// process the file
		input = new InputStreamReader(new FileInputStream(novel));
		reader = new BufferedReader(input);

		String line;

		// keep result
		ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

		// keep track of frequency of each unique word
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] wordList = line.split("\\s+");

				// count visited words
				for (int i = 0; i < wordList.length; i++) {
					if (!map.containsKey(wordList[i])) {
						map.put(wordList[i], 1);
					} else {
						map.replace(wordList[i], map.get(wordList[i]) + 1);
					}
				}
			}
		}

		Collection<Integer> values = map.values();
		ArrayList<Integer> frequencies = new ArrayList<Integer>(values);
		Collections.sort(frequencies);

		// keep track of visited words in result
		HashSet<String> visited = new HashSet<String>();

		Set<String> keys = map.keySet();

		for (int i = 0; i < 20; i++) {

			for (String s : keys) {

				if (map.get(s) == frequencies.get(i)) {

					if (!visited.contains(s)) {
						visited.add(s);
						ArrayList<Object> temp = new ArrayList<Object>();
						temp.add(s);
						temp.add(frequencies.get(i));
						result.add(temp);
						break;
					}
				}
			}
		}

		return result;
	}

	private static int countChapters() throws IOException {

		// process the file
		input = new InputStreamReader(new FileInputStream(novel));
		reader = new BufferedReader(input);

		String line;

		// result
		int numChapters = 0;

		// read the file by line
		while ((line = reader.readLine()) != null) {

			if (!line.equals("")) {

				// \\s+ is the space delimiter in java
				String[] wordList = line.split("\\s+");

				for (int i = 0; i < wordList.length; i++) {
					if (wordList[i].equals("CHAPTER")) {
						numChapters++;
					}
				}
			}
		}
		return numChapters;
	}

	public static int[] getFrequencyOfWord(String word) throws IOException {
		input = new InputStreamReader(new FileInputStream(novel));
		reader = new BufferedReader(input);
		int[] frequencies = new int[countChapters()];

		return frequencies;

	}

}
