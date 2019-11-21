package run;
import java.util.*;
import org.apfloat.*;

public class ChudnovskyCalculator implements Runnable{
	public static final double GAIN = 14.18164746272547765552552167818177086376912528982872695981685433294579740853885;
	
	
	public final long digits;
	public final long iterations;
	
	
	public ChudnovskyCalculator(long precision) {
		digits = precision;
		iterations = (long)(digits/GAIN);
	}
	
	
	public void run() {
		
	}
}
