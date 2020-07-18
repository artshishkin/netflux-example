package com.artarkatesoft.netfluxexample.codeexamples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;


public class Java8StreamExamples {

    private List<String> greetings;

    @BeforeEach
    void setUp() {
        greetings = Arrays.asList("hi", "my", "name", "is", "art");
    }

    @Test
    void simpleStreamExample() {
        greetings.stream()
                .forEach(System.out::println);
    }

    @Test
    void parallelStreamExample() {
        greetings.parallelStream()
                .forEach(System.out::println);
    }

    @Test
    void filterAndSortStreamWithCollectorExample() {
        String greeting = greetings.stream()
                .filter(s -> s.length() <= 3)
                .sorted()
                .limit(2)
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));
        System.out.println(greeting);
    }

    @Test
    void testStat() {
        IntSummaryStatistics intSummaryStatistics = greetings
                .stream()
                .mapToInt(String::length)
                .summaryStatistics();
        System.out.println(intSummaryStatistics);
    }

    @Test
    void testSum() {
        int sum = greetings
                .stream()
                .mapToInt(String::length)
                .sum();
        System.out.println(sum);
    }

    @Test
    void testSumCollect() {
        int sum = greetings
                .stream()
                .collect(Collectors.summingInt(String::length));
        System.out.println(sum);
    }

    @Test
    void testGroupingBy() {
        Map<Integer, Set<String>> groupedMap = greetings
                .stream()
                .collect(Collectors.groupingBy(String::length, Collectors.toSet()));
        System.out.println(groupedMap);
    }

    @Test
    void testFlatMap() {
        List<List<Integer>> listOfList = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));

        List<Integer> numbers = listOfList
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println(numbers);
    }

    @Test
    void testReduction() {
        String greeting = greetings.stream()
                .reduce((a, b) -> a + " - " + b)
                .get();
        System.out.println(greeting);
    }
}
