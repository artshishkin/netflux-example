package com.artarkatesoft.netfluxexample;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ClosuresEffectiveFinalAndLazyEval {
    @Test
    void lambdaExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        numbers.stream()
                .map(number -> number * 2) //lambda is stateless
                .forEach(System.out::println);
    }

    @Test
    void closureExample() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer multiplier = 2; //lexical scope
        numbers.stream()
                .map(number -> number * multiplier)
                //lambda 'closes over' variable in lexical scope
                //i.e. 'closure'
                .forEach(System.out::println);
    }

    @Test
    void closureUsingFinal() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        final Integer multiplier = 2; //lexical scope

        Stream<Integer> integerStream = numbers.stream()
                .map(number -> number * multiplier);

//        multiplier = 3;//can't change - compilation error

        integerStream.forEach(System.out::println);
    }

    @Test
    void closureEffectivelyFinal() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer multiplier = 2; //lexical scope

        Stream<Integer> integerStream = numbers.stream()
                .map(number -> number * multiplier);// compilation error - must be effectively final

//        multiplier = 3;//can't change

        integerStream.forEach(System.out::println);
    }

    @Test
    void copyingToEffecivelyFinal() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        Integer multiplier = 2; //lexical scope

        Integer finalMultiplier = multiplier;
        Stream<Integer> integerStream = numbers.stream()
                .map(number -> number * finalMultiplier);

        multiplier = 3;//can change

        integerStream.forEach(System.out::println);
    }

    @Test
    void breakingFinal() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        final Integer[] multiplier = {2};

        Stream<Integer> integerStream = numbers.stream()
                .map(number -> number * multiplier[0]);//lazy evaluation

        multiplier[0] = 3;//can change
        //will multiply by 3
        integerStream.forEach(System.out::println);
    }
}
