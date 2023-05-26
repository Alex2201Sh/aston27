package by.shumilov.task1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class MyArrayTest {

    private MyArray testArray;

    @BeforeEach
    void setUp() {
        testArray = new MyArray<Integer>(new Integer[]{600, 400, 900, 100, 500, 300, 800, 700, 200});
    }

    @Test
    void addOneObject() {
        for (int i = 0; i < 10; i++) {
            Integer testObject = new Random().nextInt();
            testArray.add(testObject);
            assertThat(testArray.get(testArray.size() - 1)).isEqualTo(testObject);
        }

    }

    @Test
    void testCapacityEnsuring() throws NoSuchFieldException, IllegalAccessException {
        Field capacity1 = testArray.getClass().getDeclaredField("capacity");
        capacity1.setAccessible(true);
        int initialCapacity = (int) capacity1.get(testArray);
        Integer testObject1 = 10000;
        Integer testObject2 = 20000;
        testArray.add(testObject1);
        testArray.add(testObject2);
        Field capacity2 = testArray.getClass().getDeclaredField("capacity");
        capacity2.setAccessible(true);
        int actualCapacity = (int) capacity2.get(testArray);
        assertThat(actualCapacity).isGreaterThan(initialCapacity);
    }

    @Test
    void addObjectByIndex() {
        for (int i = 0; i < 10; i++) {
            Integer testObject = new Random().nextInt();
            testArray.add(i, testObject);
            assertThat(testArray.get(i)).isEqualTo(testObject);
        }

    }

    @Test
    void setObject() {
        for (int i = 0; i < testArray.size(); i++) {
            Integer testObject = new Random().nextInt();
            Object objectFromArray = testArray.get(i);
            testArray.set(i, testObject);
            Object actualObject = testArray.get(i);
            assertThat(actualObject).isEqualTo(testObject);
            assertThat(actualObject).isNotEqualTo(objectFromArray);
        }

    }

    @Test
    void get() {
        for (int i = 0; i < testArray.size(); i++) {
            assertThat(testArray.get(i)).isNotNull();
        }
    }

    @Test
    void delete() {
        Integer testObject = 123456;
        testArray.add(0, testObject);
        testArray.delete(0);
        assertThat(testArray.get(0)).isNotEqualTo(testObject);
    }

    @Test
    void clearAll() {
        testArray.clearAll();
        assertThat(testArray.size()).isZero();
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void sortMyArray(MyArray giverArray, Comparator comparator, MyArray sortedArray) {
        giverArray.quickSortMyArray(comparator);
        assertThat(giverArray).isEqualTo(sortedArray);
    }

    static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of(new MyArray<String>(new String[]{"first", "third", "fourth", "second"}),
                        Comparator.naturalOrder(),
                        new MyArray<String>(new String[]{"first", "fourth", "second", "third"})),
                Arguments.of(new MyArray<String>(new String[]{"first", "third", "fourth", "second"}),
                        Comparator.reverseOrder(),
                        new MyArray<String>(new String[]{"third", "second", "fourth", "first"})),
                Arguments.of(new MyArray<String>(new Integer[]{9, 5, 6, 7, 1, 2, 7}),
                        Comparator.naturalOrder(),
                        new MyArray<String>(new Integer[]{1, 2, 5, 6, 7, 7, 9}))
        );
    }


}