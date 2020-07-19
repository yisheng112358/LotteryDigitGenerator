package lotterynumbers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BigLotteryNumberGenerator {
	/**
	 * This approach will take a moment to produce the result.
	 */

	private final static int digitsForThePrize = 6;
	private final static int searchTimes = 10000;
	private final static int startNmuber = 1;
	private final static int endNmuber = 42;

	public static Set<SortedSet<Integer>> luckyNumberSetsGenerator(int checkedTicketNumber) {
		Set<SortedSet<Integer>> luckyNumberSets = new HashSet<SortedSet<Integer>>();
		while (luckyNumberSets.size() < checkedTicketNumber) {
			Map<SortedSet<Integer>, Integer> luckyNumberMaps = new HashMap<SortedSet<Integer>, Integer>();
			for (int i = 1; i <= searchTimes; i++) {
				SortedSet<Integer> luckyNumbers = LuckyNumbersGenerator();
				if (luckyNumberMaps.containsKey(luckyNumbers)) {
					int repeatedNumber = luckyNumberMaps.get(luckyNumbers) + 1;
					luckyNumberMaps.put(luckyNumbers, repeatedNumber);
				} else {
					luckyNumberMaps.put(luckyNumbers, 1);
				}
			}

			// choose the most lucky set.
			SortedSet<Integer> theluckyNumberSet = Collections
					.max(luckyNumberMaps.entrySet(), Map.Entry.comparingByValue()).getKey();

//		System.out.println(theluckyNumberMaps + "is repeated " + luckyNumberMaps.get(theluckyNumberMaps) + " times!!!");

			luckyNumberSets.add(theluckyNumberSet);
		}
		return luckyNumberSets;
	}

	private static SortedSet<Integer> LuckyNumbersGenerator() {
		SortedSet<Integer> luckyNumbers = new TreeSet<Integer>();
		while (luckyNumbers.size() < digitsForThePrize) {
			luckyNumbers.add((int) (Math.random() * endNmuber + startNmuber));
		}

		return luckyNumbers;
	}
}
