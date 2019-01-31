package controller;

import java.net.URI;
import java.time.Duration;
import java.util.function.Consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import reactor.core.publisher.Flux;
import subscriber.ExampleSubscriber;

@RestController
public class ReactorTestController {
	private WebClient client = WebClient.create("localhost:8080");

	@GetMapping("/test")
	private void testEndPoint() {
		ExampleSubscriber exampleSubscriber = new ExampleSubscriber();
		Flux<String> strings = Flux.just("start");
		for (int i = 0; i < 1000; i++) {
			strings = strings.mergeWith(Flux.just(String.valueOf(i)));
		}
		int toBeSent = 257;
		int cin = 1;
		Flux<String> intervalFlux1= Flux.just("start");
		for (int i = 0; i < 100; i++) {
			Flux<String> flux1 = Flux.just("richiesta" +(i+1),(i+1)+"{1}", (i+1)+"{2}",(i+1)+ "{3}", (i+1)+"{4}");	
			Flux<String> flux2 =	flux1
					.interval(Duration.ofMillis(1000*cin))
					.zipWith(flux1, (t, string) -> string);
			intervalFlux1 = Flux.merge(intervalFlux1,flux2);
			cin++;
		}
		//hotFlux.subscribe(System.out::println);
		// Flux<String> flux = Flux.range(1, toBeSent).map(count -> "{\"content\":" +
		// "\"" + count + "\"}").delayElements(Duration.ofMillis(100));
		//Flux<String> intervalFlux1 = Flux.interval(Duration.ofMillis(1000)).zipWith(flux1, (i, string) -> string);
		final Flux<String> hopeyuhappynau = intervalFlux1.onBackpressureBuffer(10,new Consumer<String>() {

			@Override
			public void accept(String t) {
				System.out.println("####################################################################"+t);
				
			}
		});;
		 ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();
	        client.execute(
	          URI.create("ws://localhost:8080/messages"), 
	          session -> session.send(
	        		  hopeyuhappynau.map(session::textMessage))
	            .thenMany(session.receive()
	              .map(WebSocketMessage::getPayloadAsText)
	              .log())
	            .then())
	            .block(Duration.ofSeconds(10L));
//		for (int i = 0; i < 1; i++) {
//			client.post().uri("http://localhost:8080/examplqe").contentType(MediaType.TEXT_EVENT_STREAM)
//					.body(hotFlux, String.class).exchange().flatMapMany(res -> res.bodyToFlux(String.class))
//					.concatWith(Flux.just(LocalTime.now().toString())).limitRequest(1).log().subscribeOn(Schedulers.elastic())
//					.subscribe(exampleSubscriber);
//		}
		//hotFlux.connect();
		System.out.println("before");
		// test.subscribe();
		System.out.println("after");
	}
}
