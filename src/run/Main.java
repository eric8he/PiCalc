package run;

import org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.*;

import org.apfloat.*;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		AnsiConsole.systemInstall();
		while(true) {
			System.out.println(new Ansi().fgBrightCyan().bgCyan().a("Digits to compute:"));
			int p = Integer.parseInt(s.nextLine());
			
			System.out.println(calculateChudnovsky(p));
		}
	}
	
	public static Apfloat calculateChudnovsky(long precision) {
		long k = 0;
		long begin = System.currentTimeMillis();
		
		Apfloat a = new Apfloat(545140134,(int)(precision*1.2));
		Apfloat b = new Apfloat("-262537412640768000",(int)(precision*1.2));
		Apfloat c = new Apfloat(12,(int)(precision*1.2));
		Apfloat d = new Apfloat(16,(int)(precision*1.2));
		
		Apfloat sum = new Apfloat(0,(int)(precision*1.2));
		
		Apfloat C = new Apfloat(426880,(int)(precision*1.2)).multiply(ApfloatMath.sqrt(new Apfloat(10005,(int)(precision*1.2))));
		
		Apfloat L = new Apfloat(13591409,(int)(precision*1.2));
		Apfloat X = new Apfloat(1,(int)(precision*1.2));
		Apfloat M = new Apfloat(1,(int)(precision*1.2));
		Apfloat K = new Apfloat(6,(int)(precision*1.2));
		Apfloat H;
		
		
		long end = System.currentTimeMillis();
		
		System.out.println(new Ansi().fgBrightRed().a("Initialization took "+(end-begin)+" ms."));
		
		begin = System.currentTimeMillis();
		
		System.out.print("Iteration: 0");
		
		while(k<Math.max(1L,(long) ((double)precision/32.654450041768516))) {
			//long start = System.currentTimeMillis();
			sum = sum.add(M.multiply(L).divide(X));
			L = L.add(a);
			X = X.multiply(b);
			H = new Apfloat(k+1,(int)(precision*1.2));
			M = M.multiply(K.multiply(K.multiply(K)).subtract(d.multiply(K)).divide(H.multiply(H.multiply(H))));
			K = K.add(c);
			k++;
			if(k%100==0) System.out.print(new Ansi().cursorToColumn(0).a("Iteration: "+k));
			//long finish = System.currentTimeMillis();
			//System.out.println("Iteration "+k+" finished in "+(finish-start)+"ms.");
		}
		
		System.out.println("\n");
		
		end = System.currentTimeMillis();
		System.out.println(new Ansi().fgBrightGreen().a("Done. Summing series took "+(end-begin)+"ms."));
		
		System.out.println(new Ansi().fgBlue().a("Doing final division..."));
		
		begin = System.currentTimeMillis();
		Apfloat pi = new Apfloat(C.divide(sum).toString().substring(0,(int)(precision+2)));
		end = System.currentTimeMillis();
		System.out.println(new Ansi().fgBrightGreen().a("Done. Division took "+(end-begin)+"ms."));
		return pi;
	}
}