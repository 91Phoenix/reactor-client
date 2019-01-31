package subscriber;

import java.net.URI;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import reactor.core.publisher.Mono;

public class RiskStatusEmitter implements Subscriber<String> {

	private Subscription s;

	private ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();
	private static final URI uri = URI.create("ws://localhost:8080/messages");

	@Override
	public void onSubscribe(Subscription s) {
		this.s = s;
		s.request(1);
	}

	@Override
	public void onNext(String t) {
	        client.execute(
	          uri, 
	          session -> session.send(
	            Mono.just(session.textMessage(t)))
	            .then()).block();
	        s.request(1);
	}

	@Override
	public void onError(Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub

	}

}
