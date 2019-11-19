package run;

import org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.*;

import org.apfloat.*;

public class Main {

	public static void main(String[] args) {
		System.out.println(calculateChudnovsky(100000).toString());
	}
	
	public static Apfloat calculateChudnovsky(long precision) {
		long k = 0;
		long begin = System.currentTimeMillis();
		
		Apfloat a = new Apfloat(545140134,precision);
		Apfloat b = new Apfloat("-262537412640768000",precision);
		Apfloat c = new Apfloat(12, precision);
		Apfloat d = new Apfloat(6, precision);
		
		Apfloat sum = new Apfloat(13591409,precision);
		
		Apfloat C = new Apfloat(426880,precision).multiply(ApfloatMath.sqrt(new Apfloat(10005,precision)));
		
		Apfloat L = new Apfloat(13591409,precision);
		Apfloat X = new Apfloat(1,precision);
		Apfloat M = new Apfloat(1,precision);
		Apfloat K = new Apfloat(6,precision);
		Apfloat H;
		
		Apfloat prev = new Apfloat(-1,precision);
		
		long end = System.currentTimeMillis();
		
		System.out.println(new Ansi().fgBrightRed().a("Initialization took "+(end-begin)+" ms."));
		
		begin = System.currentTimeMillis();
		while(k<precision) {
			long start = System.currentTimeMillis();
			sum = sum.add(M.multiply(L).divide(X));
			L = L.add(a);
			X = X.multiply(b);
			H = new Apfloat(k+1,precision);
			M = M.multiply(K.multiply(K.multiply(K)).subtract(d.multiply(K)).divide(H.multiply(H.multiply(H))));
			K = K.add(c);
			k++;
			long finish = System.currentTimeMillis();
			System.out.println("Iteration "+k+" finished in "+(finish-start)+"ms.");
		}
		end = System.currentTimeMillis();
		System.out.println(new Ansi().fgBrightGreen().a("Done. Summing series took "+(end-begin)+"ms."));
		
		System.out.println(new Ansi().fgBlue().a("Doing final division..."));
		
		begin = System.currentTimeMillis();
		Apfloat pi = C.divide(sum).multiply(new Apfloat(2));
		end = System.currentTimeMillis();
		System.out.println(new Ansi().fgBrightGreen().a("Done. Division took "+(end-begin)+"ms."));
		return pi;
	}
}