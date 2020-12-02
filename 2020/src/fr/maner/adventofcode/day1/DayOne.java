package fr.maner.adventofcode.day1;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"DuplicatedCode", "RedundantStringFormatCall"})
public class DayOne extends Day {

	@Override
	public void partOne() throws Exception {
		ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "scanner.txt");

		List<Integer> list = new ArrayList<>();
		while (scan.hasNextInt()) {
			list.add(scan.nextInt());
		}

		scan.close();

		boolean resolve = false;
		int[] res = new int[2];

		for (int i = 0; i < list.size() && !resolve; i++) {
			for (int j = i + 1; j < list.size() && !resolve; j++) {
				if (list.get(i) + list.get(j) == 2020) {
					res[0] = list.get(i);
					res[1] = list.get(j);
					resolve = true;
				}
			}
		}

		System.out.println(String.format("  %d + %d = %d", res[0], res[1], res[0] + res[1]));
		System.out.println(String.format("  %d * %d = %d", res[0], res[1], res[0] * res[1]));
	}

	@Override
	public void partTwo() throws Exception {
		ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "scanner.txt");

		List<Integer> list = new ArrayList<>();
		while (scan.hasNextInt()) {
			list.add(scan.nextInt());
		}

		scan.close();

		boolean resolve = false;
		int[] res = new int[3];

		for (int i = 0; i < list.size() && !resolve; i++) {
			for (int j = i + 1; j < list.size() && !resolve; j++) {
				for (int k = j + 1; k < list.size() && !resolve; k++) {
					if (list.get(i) + list.get(j) + list.get(k) == 2020) {
						res[0] = list.get(i);
						res[1] = list.get(j);
						res[2] = list.get(k);
						resolve = true;
					}
				}
			}
		}

		System.out.println(String.format("  %d + %d + %d = %d", res[0], res[1], res[2], res[0] + res[1] + res[2]));
		System.out.println(String.format("  %d * %d * %d = %d", res[0], res[1], res[2], res[0] * res[1] * res[2]));
	}

	public static void main(String[] args) {
		new DayOne().launch();
	}
}