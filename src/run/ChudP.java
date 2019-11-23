package run;

import org.apfloat.*;


public class ChudP extends Thread{
	Apfloat result;
	long iterations;
	
	public static final Apint A = new Apint(13591409);
	public static final Apint B = new Apint(545140134);
	
	public static final Apint TWO = new Apint(2);
	public static final Apint FIVE = new Apint(5);
	public static final Apint SIX = new Apint(6);
	
	public ChudP(long its) {
		this.iterations = its;
	}
	
	public void run() {
		Apint product = Apint.ONE;
		Apint i = Apint.ZERO;
		Apint its = new Apint(iterations);
		for(; i.compareTo(its) == -1; i = i.add(Apint.ONE)) {
			product = product.multiply(Apint.ZERO.subtract((SIX.multiply(i).subtract(FIVE).multiply(TWO.multiply(i).subtract(Apint.ONE)).multiply(SIX.multiply(i).subtract(Apint.ONE)))));
		}
		result = product;
		System.out.println("p ended, "+result);
	}
}
