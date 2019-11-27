package run;

import java.util.*;
import org.apfloat.*;
import org.fusesource.jansi.*;

public class ChudnovskyCalculator implements Runnable{
	public static final double GAIN = 14.18164746272547765552552167818177086376912528982872695981685433294579740853885;
	
	public static Apfloat C23 = new Apint(512384048);
	public static Apfloat TWELVE = new Apint(12);
	public static Apint C = new Apint(640320);
	public static Apint C3_OVER_24 = new Apint(10939058860032000L);
	public static Apint L = new Apint(426880);
	
	public static Apint TWO = new Apint(2);
	public static Apint FIVE = new Apint(5);
	public static Apint SIX = new Apint(6);
	
	public static Apint Y = new Apint(13591409L);
	public static Apint Z = new Apint(545140134L);
	
	private static Apfloat wrapper;
	
	public Apfloat result;
	
	public final long digits;
	public final long iterations;
	
	public ChudnovskyCalculator(long precision) {
		
		precision *= 1.01;

		digits = precision;
		iterations = (long)Math.ceil(digits/GAIN);
		wrapper = new Apfloat(1,digits);
		TWELVE = new Apfloat(12,precision);
		
	}
	
	public void run() {
		
		result = calculate(iterations);
		
	}
	
	public Apfloat calculate(long its) {
		
		ChudnovskyBinarySplitElement base = new ChudnovskyBinarySplitElement(0,its,0);
		
		SquareRooter r = new SquareRooter(digits);
		
		long begin = System.currentTimeMillis();
		
		
		base.start();
		
		r.start();
		
		int l = 0;
		
		while(base.isAlive() || r.isAlive()) {
			
			if(l % 5000 == 0) {
				
				//System.out.print(new Ansi().cursorToColumn(0).fgGreen().a(ChudnovskyBinarySplitElement.runningThreads+" threads running, elapsed time " +(System.currentTimeMillis()-begin)+"ms       "));
			
			}
			l++;
		}
		
		System.out.println();
		
		Apint[] k = base.result;
	
		
		long microB = System.currentTimeMillis();
		
		System.out.println(new Ansi().fgYellow().a("final operations (square root took "+r.time+"ms)"));
		
		Apfloat t = k[1].multiply(L).multiply(r.result).divide(k[2].add(new Apfloat(0,digits)));
		
		System.out.println(new Ansi().fgYellow().a("final operations took "+  + (System.currentTimeMillis()-microB)+"ms"));
		
		
		System.out.println(new Ansi().fgCyan().a("finished in "  + (System.currentTimeMillis()-begin)+"ms"));
		
		return new Apfloat(t.toString().substring(0,(int)(digits/1.01 + 2)),(long)(digits/1.01)+2);
		
	}
}
