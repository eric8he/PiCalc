package run;

import java.util.Properties;

import org.apfloat.*;

public class ChudnovskyBinarySplitElement extends Thread {
	public static int MAX_LVL = 2; // 2^(N+1)-1 threads
	
	public Apint[] result;
	
	private long begin;
	private long end;
	
	private int level;
	
	public static int runningThreads = 0;
	
	public ChudnovskyBinarySplitElement(long a, long b, int level) {
		begin = a;
		end = b;
		this.level = level;
	}
	
	public void run() {		
		runningThreads++;
		result = split(begin, end);
		runningThreads--;
	}
	
	public Apint[] split(long a, long b) {
		Apint C3_OVER_24 = new Apint(10939058860032000L);
		
		Apint TWO = new Apint(2);
		Apint FIVE = new Apint(5);
		Apint SIX = new Apint(6);
		
		Apint Y = new Apint(13591409L);
		Apint Z = new Apint(545140134L);
		
		Apint A = new Apint(a);
		
		Apint Qab;
		Apint Pab;
		Apint Tab;
		
		Apint ONE = new Apint(1);
		
		if(b - a == 1) {
			if(a == 0) {
				Pab = ONE;
				Qab = ONE;
			}
			else {
				Pab = (SIX.multiply(A).subtract(FIVE))
						.multiply(TWO.multiply(A).subtract(ONE))
						.multiply(SIX.multiply(A).subtract(ONE));
				Qab = A.multiply(A.multiply(A)).multiply(C3_OVER_24);
			}
			Tab = Pab.multiply(Y.add(Z.multiply(A)));
			if(a % 2 == 1) Tab = Tab.negate();
		}
		else {
			long m = (a + b) / 2;
			
			Apint[] res1;
			Apint[] res2;
			
			if(level < MAX_LVL) {
				ChudnovskyBinarySplitElement p = new ChudnovskyBinarySplitElement(a,m,level+1);
				ChudnovskyBinarySplitElement q = new ChudnovskyBinarySplitElement(m,b,level+1);
				
				p.start();
				q.start();
				
				while(p.isAlive() || q.isAlive()) { }
				
				res1 = p.result;
				res2 = q.result;
			}
			
			else {
				res1 = split(a,m);
				res2 = split(m,b);
			}
			
			/**     +  0  +  1  +  2
			 * res1 | Pam | Qam | Tam
			 * res2 | Pmb | Qmb | Tmb
			 **/
			Pab = res1[0].multiply(res2[0]);
			Qab = res1[1].multiply(res2[1]);
			Tab = res2[1].multiply(res1[2]).add(res1[0].multiply(res2[2]));
		}
		return new Apint[]{Pab,Qab,Tab};
	}
}
