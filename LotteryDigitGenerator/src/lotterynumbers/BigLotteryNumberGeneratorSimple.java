package lotterynumbers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BigLotteryNumberGeneratorSimple {
	private final static int digitsForThePrize = 6;
	private final static int searchTimes = 100000;
	private final static int startNmuber = 1;
	private final static int endNmuber = 42;

	public static Set<SortedSet<Integer>> LuckyNumberSetGenerator(int checkedTicketNumber) {
		Set<SortedSet<Integer>> luckyNumberSets = new HashSet<SortedSet<Integer>>();
		while (luckyNumberSets.size() < checkedTicketNumber) {
			SortedSet<Integer> luckyNumberSet = new TreeSet<Integer>();
			while (luckyNumberSet.size() < digitsForThePrize) {
				luckyNumberSet.add(LuckyNumberGenerator(luckyNumberSet));
			}

			luckyNumberSets.add(luckyNumberSet);
		}

		return luckyNumberSets;
	}

	public static int LuckyNumberGenerator(SortedSet<Integer> skipNumbers) {
		Map<Integer, Integer> aBunchOfRandomNumbers = new HashMap<Integer, Integer>();

		for (int i = 1; i <= searchTimes; i++) {
			int aRandomNumber;
			do {
				aRandomNumber = (int) (Math.random() * endNmuber + startNmuber);
			} while (skipNumbers.contains(aRandomNumber));

			if (aBunchOfRandomNumbers.containsKey(aRandomNumber)) {
				int repeatedNumber = aBunchOfRandomNumbers.get(aRandomNumber) + 1;
				aBunchOfRandomNumbers.put(aRandomNumber, repeatedNumber);
			} else {
				aBunchOfRandomNumbers.put(aRandomNumber, 1);
			}
		}

		// choose the most lucky set.
		int theLuckyNumber = Collections.max(aBunchOfRandomNumbers.entrySet(), Map.Entry.comparingByValue()).getKey();

//		System.out.println(theLuckyNumber + " is repeated " + aBunchOfRandomNumbers.get(theLuckyNumber) + " times!!!");

		return theLuckyNumber;
	}
}
