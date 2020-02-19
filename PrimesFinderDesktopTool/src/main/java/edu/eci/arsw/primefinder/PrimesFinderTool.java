package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool {

    public static Boolean pause = false;
    public static Object monitThread = new Object();
    public static int threads = 4; //Los 4 Threads
    public static AtomicInteger countTr = new AtomicInteger(threads);

	public static void main(String[] args) {
            //PrimeFinder.findPrimes(new BigInteger("1"), new BigInteger("10000"), prs);

            int maxPrim=100;//1000
            
            PrimesResultSet prs=new PrimesResultSet("john");
            PrimeFinderThread primeThreads[] = new PrimeFinderThread[threads];

            int step = maxPrim/threads;
            int end = 0;
            int start = 0;
            for (int i = 0; i < threads; i++) {
                end = start + step;
                primeThreads[i] = new PrimeFinderThread(new BigInteger(String.valueOf(start++)), new BigInteger(String.valueOf(end)), prs);
                primeThreads[i].start();
                start = end;
            }
            while(countTr.get() > 0){
                try {
                    //check every 10ms if the idle status (10 seconds without mouse
                    //activity) was reached. 
                    Thread.sleep(10);
                    if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>10000){
                        System.out.println("Idle CPU ");
                        pause = true;
                    }
                    else{
                        System.out.println("User working again!");
                        pause = false;
                        synchronized (monitThread){
                            monitThread.notifyAll();
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Prime numbers found:");
            System.out.println(prs.getPrimes());
            System.exit(0);

	}
}


