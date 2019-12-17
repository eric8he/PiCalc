package run;

import java.util.Properties;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatContext;
import org.apfloat.Apint;

public class BareBonesCalc {
public static final double GAIN = 14.18164746272547765552552167818177086376912528982872695981685433294579740853885;
	
	public Apfloat result;
	
	public final long digits;
	public final long iterations;
	
	public long time;
	
	public BareBonesCalc(long precision) {
		
		precision *= 1.01;
		digits = precision;
		iterations = (long)Math.ceil(digits/GAIN);
		
	}
	
	public void run() {
		ApfloatContext ctx = ApfloatContext.getContext();

        Properties properties = new Properties();

        properties.setProperty(ApfloatContext.BUILDER_FACTORY, "org.apfloat.internal." + (this.digits > 80000000 ? "Long" : "Int") + "BuilderFactory");
		properties.setProperty(ApfloatContext.FILE_PATH, "C:/calc/");
		
		
        ctx.setProperties(properties);
		result = calculate(iterations);
	}
	
	public Apfloat calculate(long its) {
		Apint L = new Apint(426880);
		
		ChudnovskyBinarySplitElement base = new ChudnovskyBinarySplitElement(0,its,0);
		
		SquareRooter r = new SquareRooter(digits);
		
		long begin = System.currentTimeMillis();
		
		base.start();
		r.start();
		
		while(base.isAlive() || r.isAlive()) { }
		
		Apint[] k = base.result;
		Apfloat t = k[1].multiply(L).multiply(r.result).divide(k[2].add(new Apfloat(0,digits)));
		
		this.time = (System.currentTimeMillis()-begin);
		
		return new Apfloat(t.toString().substring(0,(int)(digits/1.01 + 2)),(long)(digits/1.01)+2);
		
	}
}
