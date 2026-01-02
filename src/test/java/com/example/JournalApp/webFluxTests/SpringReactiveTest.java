package com.example.JournalApp.webFluxTests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class SpringReactiveTest {

    @Test
    public void test(){
        System.out.println("test started");
        Mono<String> monoPub = Mono.justOrEmpty("testing");
//        monoPub.subscribe(new CoreSubscriber<String>(){
//            @Override
//            public void onSubscribe(Subscription s) {
//                System.out.println("Subscribed");
//                s.request(2);
//            }
//
//            @Override
//            public void onNext(String data) {
//                System.out.println("data: " + data);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println("Error: " + t.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("Completed");
//            }
//        });
        monoPub.subscribe(
                System.out::println,
                err -> System.out.println("Error: " + err.getMessage())
        );




    }
}
