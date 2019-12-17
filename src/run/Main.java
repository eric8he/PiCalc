package run;

import org.fusesource.jansi.Ansi.*;
import org.fusesource.jansi.*;

//import org.apfloat.*;

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
				//try {
					c.run();
					while(c.result==null){}
				//} catch (Exception e) {
				//	System.out.println(new Ansi().fgBrightRed().a("Could not allocate swap!"));
				//}
				
				System.out.print(new Ansi().fg(Color.WHITE));
				long x = System.currentTimeMillis();
				System.out.println(c.result.toString().length());
				System.out.println("Writing result to file...");
				String out = c.result.toString();
				fileWriter.append(out);
				System.out.println("Writing took " + (System.currentTimeMillis()-x) + "ms. Done.");
				fileWriter.close();
				System.out.println();
				System.out.println();
			}
		}
		
		s.close();
	}
}