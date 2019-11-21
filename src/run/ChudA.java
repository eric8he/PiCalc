package run;

import java.util.*;
import org.apfloat.*;

/** 
 * Part A of the Chudnovsky algorithm.
 * This is the a(x) function as outlined in Haible et al.
 * @author s-hee
 *
 */
public class ChudA extends Thread {
	Apint result;
	long iterations;
	
	public static final Apint A = new Apint(13591409);
	public static final Apint B = new Apint(545140134);
	
	public ChudA(long its) {
		iterations = its;
	}
	
	public void run() {
		Apint product = Apint.ONE;
		Apint i = Apint.ZERO;
		Apint its = new Apint(iterations);
		for(; i.compareTo(its) == -1; i = i.add(Apint.ONE)) {
			product.multiply(A.add(B.multiply(product)));
		}
		
		result = product;
	}
}
