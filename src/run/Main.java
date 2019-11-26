package run;

import org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.*;

import org.apfloat.*;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Scanner s = new Scanner(System.in);
		AnsiConsole.systemInstall();
		
		String path = "c:/temp/test.txt";
		
		while(true) {
			
			System.out.println(new Ansi().fgBrightCyan().bgCyan().a("Command or digits to compute:"));
			System.out.print(new Ansi());
			String input = s.nextLine();
			
			if(input.startsWith("-T")) ChudnovskyBinarySplitElement.MAX_LVL = Integer.parseInt(input.substring(2));
			else if(input.startsWith("-F")) path = input.substring(2);
			else if(input.equalsIgnoreCase("q")) break;
			else {
				FileWriter fileWriter = new FileWriter(path);
				int p;
				try {
					p = Integer.parseInt(input);
					if(p <= 0) {
						System.out.println(new Ansi().fgBrightRed().a("Invalid number of digits - must be greater than zero"));
						continue;
					}
				} catch (NumberFormatException e){
					System.out.println(new Ansi().fgBrightRed().a("Invalid expression!"));
					System.out.println();
					System.out.println(new Ansi().fgGreen().a("Valid expressions: \n"
							+ "-T32             | sets maximum number of thread layers (number of threads will be 2^(n-1)-1)\n"
							+ "-FC:\\temp\\pi.txt | sets output file path to p\n"
							+ "300000           | calculates digits of pi (including the 3)"));
					
					System.out.println();
					System.out.println();

					continue;
				}
				
				ChudnovskyCalculator c = new ChudnovskyCalculator(p);
				try {
					c.run();
					while(c.result==null){}
				} catch (Exception e) {
					System.out.println(new Ansi().fgBrightRed().a("Could not allocate swap!"));
				}
				
				System.out.print(new Ansi().fg(Color.WHITE));
				long x = System.currentTimeMillis();
				System.out.println(c.result.toString().length());
				System.out.println("Writing result to file...");
				fileWriter.append(c.result.toString());
				System.out.println("Writing took " + (System.currentTimeMillis()-x) + "ms. Done.");
				fileWriter.close();
				System.out.println();
				System.out.println();
			}
		}
	}
	
	public static Apfloat calculateChudnovsky(long precision) {
		long k = 0;
		long begin = System.currentTimeMillis();
		
		Apint a = new Apint(545140134);
		Apint b = new Apint("-262537412640768000");
		Apint c = new Apint(12);
		Apint d = new Apint(16);
		
		Apfloat sum = new Apfloat(0,(int)(precision*1.2));
		
		Apfloat C = new Apfloat(426880,(int)(precision*1.2)).multiply(ApfloatMath.sqrt(new Apfloat(10005,(int)(precision*1.2))));
		
		Apint L = new Apint(13591409);
		Apint X = new Apint(1);
		Apfloat M = new Apfloat(1,(int)(precision*1.2));
		Apint K = new Apint(6);
		Apint H = new Apint(1);
		
		
		long end = System.currentTimeMillis();
		
		System.out.println(new Ansi().fgBrightRed().a("Initialization took "+(end-begin)+" ms."));
		
		begin = System.currentTimeMillis();
		
		System.out.print("Iteration: 0");
		
		//long msum = 0;
		//long xsum = 0;
		
		
		while(k<Math.max(100L,(long) ((double)precision/32.654450041768516))) {
			//long start = System.currentTimeMillis();
			
			//long xstart = System.currentTimeMillis();
			sum = sum.add(M.multiply(L).divide(X));
			//xsum += System.currentTimeMillis()-xstart;
			
			L = L.add(a);
			X = X.multiply(b);
			
			//long mstart = System.currentTimeMillis();
			M = M.multiply(K.multiply(K.multiply(K)).subtract(d.multiply(K))).divide(H.multiply(H.multiply(H)));
			//msum += System.currentTimeMillis()-mstart;
			
			K = K.add(c);
			H = H.add(Apfloat.ONE);
			k++;
			if(k%100==0) System.out.print(new Ansi().cursorToColumn(0).a("Iteration: "+k));
			//long finish = System.currentTimeMillis();
			//System.out.println("Iteration "+k+" finished in "+(finish-start)+"ms.");
		}
		
		System.out.println("\n");
		
		end = System.currentTimeMillis();
		System.out.println(new Ansi().fgBrightGreen().a("Done. Summing series took "+(end-begin)+"ms. ("+(end-begin)/k+"ms/it)"));
		//System.out.println(new Ansi().fgBrightGreen().a("Spent "+xsum+"ms on calculation of X ("+(100*xsum/(end-begin))+"%)"));
		//System.out.println(new Ansi().fgBrightGreen().a("Spent "+msum+"ms on calculation of M ("+(100*msum/(end-begin))+"%)"));
		
		System.out.println(new Ansi().fgBlue().a("Doing final division..."));
		
		begin = System.currentTimeMillis();
		Apfloat pi = new Apfloat(C.divide(sum).toString().substring(0,(int)(precision+2)));
		end = System.currentTimeMillis();
		System.out.println(new Ansi().fgBrightGreen().a("Done. Division took "+(end-begin)+"ms."));
		return pi;
	}
}