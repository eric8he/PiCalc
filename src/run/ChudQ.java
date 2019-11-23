package run;

import org.apfloat.*;
import java.util.*;

public class ChudQ extends Thread {
	Apfloat result;
	long iterations;
	
	public static final Apint C3 = new Apint(262537412640768000L);
	
	public static Apfloat TWENTY4 = new Apfloat(24);
	
	public ChudQ(long its, long prec) {
		this.iterations = its;
		this.TWENTY4 = new Apfloat(24,prec);
	}
	
	public void run() {
		Apfloat product = Apfloat.ONE;
		Apint i = Apint.ONE;
		Apint its = new Apint(iterations-1);
		for(; i.compareTo(its) == -1; i = i.add(Apint.ONE)) {
			product = product.add(C3.multiply(i.multiply(i.multiply(i)).divide(TWENTY4)));
		}
		result = product;
		System.out.println("q ended, "+result);
	}
}
