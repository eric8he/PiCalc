package run;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.fusesource.jansi.Ansi;

public class SquareRooter extends Thread{
	private long digits;
	
	public Apfloat result;
	
	public long time;
	
	public SquareRooter(long digits) {
		this.digits = digits;
	}
	
	public void run() {
		long microB = System.currentTimeMillis();
		result = ApfloatMath.sqrt(new Apfloat(10005,digits));
		time = System.currentTimeMillis()-microB;
	}
}
