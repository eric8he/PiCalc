package run;

import java.util.*;
import org.apfloat.*;

public class ChudnovskyCalculator implements Runnable{
	public static final double GAIN = 14.18164746272547765552552167818177086376912528982872695981685433294579740853885;
	
	public static Apfloat C23 = new Apint(512384048);
	public static Apfloat TWELVE = new Apint(12);
	
	private static Apfloat wrapper;
	
	public Apfloat result;
	
	public final long digits;
	public final long iterations;
	
	
	public ChudnovskyCalculator(long precision) {
		digits = precision;
		iterations = (long)Math.ceil(digits/GAIN);
		wrapper = new Apfloat(1,digits);
		TWELVE = new Apfloat(12,precision);
	}
	
	
	public void run() {
		ChudA a = new ChudA(iterations);
		ChudP p = new ChudP(iterations);
		ChudQ q = new ChudQ(iterations,digits);
		
		a.start();
		p.start();
		q.start();
		
		while(a.isAlive() || p.isAlive() || q.isAlive()) { }
		
		result = TWELVE.divide(C23).multiply(a.result.multiply(p.result.divide(q.result)));
	}
}
