package run;

import java.util.*;
import org.apfloat.*;
import org.fusesource.jansi.*;
import org.apfloat.internal.*;

public class ChudnovskyCalculator implements Runnable{
	public static final double GAIN = 14.18164746272547765552552167818177086376912528982872695981685433294579740853885;
	
	private static Apfloat wrapper;
	
	public Apfloat result;
	
	public final long digits;
	public final long iterations;
	
	public ChudnovskyCalculator(long precision) {
		
		precision *= 1.01;

		digits = precision;
		iterations = (long)Math.ceil(digits/GAIN);
		wrapper = new Apfloat(1,digits);
		
	}
	
	public void run() {
		ApfloatContext ctx = ApfloatContext.getContext();

        Properties properties = new Properties();

        properties.setProperty(ApfloatContext.BUILDER_FACTORY, "org.apfloat.internal." + (this.digits > 80000000 ? "Long" : "Int") + "BuilderFactory");
		properties.setProperty(ApfloatContext.FILE_PATH, "C:/calc/");
		
		
        ctx.setProperties(properties);
		
		System.out.println(new Ansi().fgBrightMagenta().a("Setting context: " + digits +
				" digits is " + (digits > 220000000 ? "greater" : "less") + " than threshold, using " + 
				(digits > 110000000 ? "LongBuilderFactory" : "IntBuilderFactory")));
		
		result = calculate(iterations);
		
	}
	
	public Apfloat calculate(long its) {
		Apint L = new Apint(426880);
		
		ChudnovskyBinarySplitElement base = new ChudnovskyBinarySplitElement(0,its,0);
		
		SquareRooter r = new SquareRooter(digits);
		
		long begin = System.currentTimeMillis();
		
		
		base.start();
		
		r.start();
		
		int l = 0;
		
		while(base.isAlive() || r.isAlive()) {
			
			if(l % 5000 == 0) {
				
				System.out.print(new Ansi().cursorToColumn(0).fgGreen().a(ChudnovskyBinarySplitElement.runningThreads+" threads running, elapsed time " +(System.currentTimeMillis()-begin)+"ms       "));
			
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
