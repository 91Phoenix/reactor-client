package subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ExampleSubscriber implements Subscriber<String> {

	private Subscription subscription;

	private Runtime rt = Runtime.getRuntime();

	@Override
	public void onSubscribe(Subscription s) {
		this.subscription = s;
		s.request(1);
	}

	@Override
	public void onNext(String t) {
		synchronized (this) {
			
			System.out.println("end time "+ t);
			if (memorySpier(true) > 0.5) {
				System.out.println("requesting one more" + t);
				subscription.request(1);
			} else {
				System.out.println("stop processing at request with " + t);
				while(memorySpier(false) <0.5) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				memorySpier(true);
			}
		}
	}

	private double memorySpier(boolean shouldLog) {
		double freeMemory = (double) rt.freeMemory();
		double totalMemory = (double) rt.totalMemory();
		double memoryPercentage = freeMemory / totalMemory;
		if(shouldLog) System.out.println(freeMemory + " " + totalMemory + " " + memoryPercentage);
		return memoryPercentage;
	}

	@Override
	public void onError(Throwable t) {
		t.printStackTrace();
	}

	@Override
	public void onComplete() {
	}

}
