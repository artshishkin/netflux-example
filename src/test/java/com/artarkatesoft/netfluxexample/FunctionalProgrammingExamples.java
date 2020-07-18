package com.artarkatesoft.netfluxexample;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class FunctionalProgrammingExamples {

    /* 4 properties of a function
     * 1. name
     * 2. return type
     * 3. parameter list
     * 4. body
     */

    @Test
    void functionWith4Things() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {//method
                System.out.println("In thread t1"); //body
            }
        });
        t1.start();
        System.out.println("In Main thread");
    }

    @Test
    void lambdaExpression() {
        /*
         * lambda expression
         * (parameter list) -> body
         * 1. name - anonymous
         * 2. return type - can be inferred
         * 3. parameter list
         * 4. body
         */
        Thread t1 = new Thread(() -> System.out.println("Silence of Lambdas"));
        // ^ Constructor is a higher order function
        // function is a first class citizen
        t1.start();
        System.out.println("In Main thread");
    }

    @Test
    void listIteratorHighCeremony() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        for (int i = 0; i < greetings.size(); i++) {
            String greeting = greetings.get(i);
            System.out.println(greeting);
        }
        //very complex, requires a lot of knowledge of code
    }

    @Test
    void listIteratorLessCeremonyExternalIter() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        for (String greeting : greetings) {
            System.out.println(greeting);
        }
        //simpler, still using external iterator
    }

    @Test
    void listIteratorInternalIterConsumer() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        greetings.forEach(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                }
        );
    }

    @Test
    void listIteratorInternalIterLamdbaMethod() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        greetings.forEach((String s) -> System.out.println(s));
    }

    @Test
    void listIteratorInternalIterLamdbaMethodTypeInference() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        greetings.forEach(s -> System.out.println(s));  //inferred by compiler
    }

    @Test
    void listIteratorInternalIterMethodReference() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        greetings.forEach(System.out::println);
    }

    @Test
    void countWordsWith2CharactersImperative() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        int count = 0;
        for (String greeting : greetings) {
            if (greeting.length() == 2) count++;
        }
        assertThat(count).isEqualTo(3);
    }

    @Test
    void countWordsWith2CharactersDeclarative1() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        long count = greetings
                .stream()
                .filter(word -> word.length() == 2)
                .count();
        assertThat(count).isEqualTo(3);
    }

    @Test
    void countWordsWith2CharactersDeclarative2() {
        List<String> greetings = Arrays.asList("hi", "my", "name", "is", "art");
        long count = greetings
                .stream()
                .map(String::length)
                .filter(len -> len == 2)
                .count();
        assertThat(count).isEqualTo(3);
    }

}
