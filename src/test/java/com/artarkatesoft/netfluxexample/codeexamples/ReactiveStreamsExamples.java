package com.artarkatesoft.netfluxexample.codeexamples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class ReactiveStreamsExamples {

    private Flux<String> greetings;

    @BeforeEach
    void setUp() {
        greetings = Flux.just("hi", "my", "name", "is", "art");
    }

    @Test
    void simpleStreamExample1() {
        greetings.toStream()
                .forEach(System.out::println);
    }

    @Test
    void simpleStreamExample2() {
        greetings.subscribe(System.out::println);
    }

    @Test
    void simpleStreamExample3() {
        //no output <- because no subscriber
        greetings.doOnEach(greeting -> System.out.println(greeting.get()));
    }

    @Test
    void simpleStreamExample4() {
        //trigger subscription
        greetings.doOnEach(greeting -> System.out.println(greeting.get())).subscribe();
    }

    @Test
    void simpleStreamExample5() {
        //trigger subscription
        greetings
                .doOnEach(greeting -> System.out.println("From Signal:" + greeting.toString()))
                .subscribe(System.out::println);
    }

    @Test
    void simpleStreamExampleWithSubscriber() {
        greetings.subscribe(System.out::println, null, () -> System.out.println("All DONE"));
    }

    @Test
    void simpleStreamExampleWithSubscriberConsumers() {
        Consumer<String> mainConsumer = System.out::println;
        Consumer<Throwable> errorConsumer = Throwable::printStackTrace;
        Runnable completeConsumer = () -> System.out.println("All DONE");

        greetings.subscribe(mainConsumer, errorConsumer, completeConsumer);
    }

    @Test
    void mapStreamExample() {
        greetings
                .map(String::length)
                .subscribe(System.out::println);
    }

    @Test
    void filterLimitSortStreamExample() {
        greetings
                .filter(s -> s.length() <= 3)
                .skip(1)//skip some elements
                .take(2)//limit
                .sort()//sort
                .subscribe(System.out::println);
    }

    @Test
    void filterLimitSortWithCollectorExample() {
        greetings
                .filter(s -> s.length() <= 3)
                .skip(1)//skip some elements
                .take(2)//limit
                .sort()//sort
                .collect(Collectors.joining(", ")) //converts to Mono<String>
                .subscribe(System.out::println);
    }

    @Test
    void testFlatMap1() {
        Flux<List<Integer>> listFlux = Flux.just(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
        listFlux
                .flatMap(Flux::fromIterable)
                .subscribe(System.out::println);
    }

    @Test
    void testFlatMap2() {
        Flux<List<List<Integer>>> listFlux = Flux.just(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));
        listFlux
                .flatMap(Flux::fromIterable)
                .subscribe(System.out::println);
    }

    @Test
    void testFlatMap3() {
        Flux<List<List<Integer>>> listFlux = Flux.just(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));
        listFlux
                .flatMap(Flux::fromIterable)
                .flatMap(Flux::fromIterable)
                .subscribe(System.out::println);
    }

    @Test
    void testFlatMap4() {
        Flux<List<List<Integer>>> listFlux = Flux.just(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)));
        listFlux
                .flatMap(lists -> Flux.fromStream(
                        lists.stream().flatMap(Collection::stream)
                ))
                .subscribe(System.out::println);
    }

    @Test
    void testReduce() {
        greetings
                .reduce((a, b) -> a + " - " + b)
                .subscribe(System.out::println);
    }

    @Test
    @DisplayName("prints NOTHING")
    void testDelay() {
        greetings
                .delayElements(Duration.ofMillis(200))
                .subscribe(System.out::println);
    }

    @Test
    @DisplayName("prints everything using CountDownLatch")
    void testDelay_countDownLatch() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        greetings
                .delayElements(Duration.ofMillis(200))
                .subscribe(System.out::println, null, latch::countDown);
        latch.await();
    }
    @Test
    @DisplayName("prints everything using StepVerifier")
    void testDelay_stepVerifier() {

        StepVerifier.create(greetings.delayElements(Duration.ofMillis(200)))
                .expectSubscription()
                .thenConsumeWhile(
                        mess->true,
                        System.out::println
                )
                .verifyComplete();
    }
}
