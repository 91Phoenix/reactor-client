package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import controller.ReactorTestController;

@SpringBootApplication
@ComponentScan(basePackageClasses = ReactorTestController.class)
public class Client {

	public static void main(String[] args) {
		System.setProperty("reactor.netty.ioWorkerCount", "100");
		SpringApplication.run(Client.class, args);
	}
}