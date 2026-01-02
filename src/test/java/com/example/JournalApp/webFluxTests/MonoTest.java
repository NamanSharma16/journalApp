package com.example.JournalApp.webFluxTests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class MonoTest {

    Mono<String> m1 = Mono.just("Hello first Mono");
    Mono<String> m2 = Mono.just("Hello second Mono");
    Mono<String> m3 = Mono.just("Hello third Mono");
    @Test
    void testingMonoFunc(){
        Mono<String> errorMono = Mono.error(new RuntimeException("Some error occurred"));

        Mono<String> m1 = Mono
                .just("some subscribed data")
                .map(String::toUpperCase)
                .log()
                .then(errorMono);


        m1.subscribe(System.out::println);
        errorMono.subscribe(System.out::println);

    }

    @Test
    void testingZipMethods(){
        Mono<String> m1 = Mono.just("Hello");
        Mono<String> m2 = Mono.just("World").map(String::toUpperCase);

        Mono<Tuple2<String,String>> res = Mono.zip(m1,m2);
        res.subscribe(
                System.out::println
        );

        Mono<String> res1 = m1.map(String::toUpperCase);

        Mono<Tuple2<String,String>> res2 = m1.zipWith(m2);
        res2.subscribe(data -> {
            System.out.println("First: " + data.getT1());
            System.out.println("Second: " + data.getT2());
        });

        Mono<String[]> resFlatMap = res1.flatMap(value -> Mono.just(value.split(" ")));
    }

    @Test
    void testingMapAndFlatMap() throws InterruptedException{



        Mono<String[]> resultMapMono = m1.flatMap(value -> Mono.just(value.split(" ")));
        resultMapMono.subscribe(data -> {
            for(String s : data) {
                System.out.println(s);
            }
        });

        Flux<String> stringFlux = m1.concatWith(m2)
                .log().delayElements(Duration.ofMillis(2000));

        stringFlux.subscribe(System.out::println);


        Thread.sleep(3000);

    }
}
