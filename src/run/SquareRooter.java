package run;

import java.util.Properties;

import org.apfloat.Apfloat;
import org.apfloat.ApfloatContext;
import org.apfloat.ApfloatMath;
import org.apfloat.spi.FilenameGenerator;
import org.fusesource.jansi.Ansi;

public class SquareRooter extends Thread{
	private long digits;
	
	public Apfloat result;
	
	public long time;
	
	public SquareRooter(long digits) {
		
		this.digits = digits;
		
	}
	
	public void run() {
		
		Properties p = (Properties) ApfloatContext.getContext().getProperties().clone();
		
		p.setProperty(ApfloatContext.FILE_SUFFIX, ".sqrt");
		
		ApfloatContext.setThreadContext(new ApfloatContext(p));
		
		long microB = System.currentTimeMillis();
		
		result = ApfloatMath.sqrt(new Apfloat(10005,digits));
		
		time = System.currentTimeMillis()-microB;
		
	}
}
