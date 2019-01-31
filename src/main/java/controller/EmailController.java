package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import reactor.core.publisher.BaseSubscriber;

@Controller
@EnableScheduling
public class EmailController {

//	   @Autowired
//	   private SimpMessagingTemplate template;
//
//	   @MessageMapping("/email")
//	   public void email(final String message) {
//		   System.out.println("ciao");
//	   }
//
////	   @Scheduled(fixedRate = 10000)
////	   public void updateClients() {
////	      repository.findLastOnes(lastExecution).subscribe(new EmailSubscriber<>());
////	   }
//
//	   private class EmailSubscriber extends BaseSubscriber {
//	      @Override
//	      protected void hookOnComplete() {
//	      }
//
//	      @Override
//	      protected void hookOnError(Throwable throwable) {
//	         template.convertAndSend("/topic/email/errors", throwable.getMessage());
//	      }
//
//	      @Override
//	      protected void hookOnNext(T value) {
//	         final Email email = (Email) value;
//	         email.setContent(HtmlUtils.htmlEscape(email.getContent()));
//	         template.convertAndSend("/topic/email/updates", email);
//	      }
//	   }
//	}
}
