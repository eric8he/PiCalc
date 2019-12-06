package optimizer;

import run.*;

public class Main {
	public static void main(String[] args) {
		long benchmarkDigits = 10000000;
		int it_times = 5;
		
		long LVL0 = avgTime(0, benchmarkDigits, it_times);
		long LVL1 = avgTime(1, benchmarkDigits, it_times);
		long LVL2 = avgTime(2, benchmarkDigits, it_times);
		long LVL3 = avgTime(3, benchmarkDigits, it_times);
		long LVL4 = avgTime(4, benchmarkDigits, it_times);
		long LVL5 = avgTime(5, benchmarkDigits, it_times);
		
		System.out.println("1 THREAD average: " + LVL0 + "ms \n"
				+ "3 THREAD average: " + LVL1 + "ms \n"
				+ "7 THREAD average: " + LVL2 + "ms \n"
				+ "15 THREAD average: " + LVL3 + "ms \n"
				+ "31 THREAD average: " + LVL4 + "ms \n"
				+ "63 THREAD average: " + LVL5 + "ms \n");
	}
	
	public static long avgTime(int threads, long digits, int times) {
		ChudnovskyBinarySplitElement.MAX_LVL = threads;
		
		long total = 0;
		for(int i=0; i<times; i++) {
			BareBonesCalc c = new BareBonesCalc(digits);
			c.run();
			total += c.time;
			System.out.print(i);
			System.gc();
		}
		System.out.println();
		return total/times;
	}
}