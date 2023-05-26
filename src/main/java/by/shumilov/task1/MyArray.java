package by.shumilov.task1;

import java.util.Arrays;
import java.util.Comparator;

public class MyArray<T> {
    private T[] elementData;
    private int size;
    private int capacity;

    /**
     * Default constructor without any parameters.
     * In this case, myArray object receives default parameters:
     * capacity and array of objects as elementData.
     */
    public MyArray() {
        this.capacity = 10;
        elementData = (T[]) new Object[capacity];
    }

    /**
     * Constructor with parameter capacity creates
     * field elementData with given capacity.
     *
     * @param capacity - array capacity.
     */
    public MyArray(int capacity) {
        this.capacity = capacity;
        elementData = (T[]) new Object[capacity];
    }

    /**
     * Constructor, which receives array of objects and
     * saves this given array in field elementData.
     *
     * @param elementData - array of objects
     */
    public MyArray(Object[] elementData) {
        this.elementData = (T[]) elementData;
        this.size = elementData.length;
        this.capacity = elementData.length;
    }

    /**
     * Method saves any parametrized object to array elementData.
     *
     * @param object - given parametrized object.
     */
    public void add(T object) {
        ensureCapacity(size + 1);
        elementData[size++] = object;
    }

    /**
     * Method saves any parametrized object to array elementData
     * to certain position. All objects in elementData array after
     * index position are rearranged to one position right.
     *
     * @param index  - position to save given object.
     * @param object - given object to save.
     */
    public void add(int index, T object) {
        if (index < size) {
            ensureCapacity(size + 1);
            for (int i = size; i > index; i--) {
                elementData[i] = elementData[i - 1];
            }
            elementData[index] = object;
            size++;
        } else add(object);

    }

    /**
     * Method return object, witch is saved in position with given index
     *
     * @param index - position of needed object
     * @return - object with index position.
     */
    public T get(int index) {
        return elementData[index];
    }

    /**
     * Method removes object in array with given position.
     *
     * @param index - position for object to remove it.
     */
    public void delete(int index) {
        if (index < size) {
            for (int i = index; i < size - 1; i++) {
                elementData[i] = elementData[i + 1];
            }
            elementData[size - 1] = null;
            size--;
        }
    }

    /**
     * Method clear link to elementData and creates default array.
     */
    public void clearAll() {
        this.capacity = 10;
        elementData = (T[]) new Object[capacity];
        size = 0;
    }

    /**
     * Method returns current count of objects in elementData array.
     *
     * @return - integer counter of objects in array.
     */
    public int size() {
        return size;
    }

    /**
     * Method sorts array with given comparator.
     * In case, if you use Integer, String, etc elements, you can use
     * Comparator.naturalOrder() or Comparator.reverseOrder().
     * In another cases you should send your custom comparator in this method.
     *
     * @param comparator - comparator, which says how to sort your array.
     */
    public void quickSortMyArray(Comparator<T> comparator) {
        elementData = quickSort(elementData, comparator);
    }

    /**
     * Private method, which implements quick sort from book "Grokking Algorithms"
     * with recursion.
     *
     * @param objects - array of paramethrized objects
     * @param comparator - how to compare given objects
     * @return - sorted array of given objects.
     */
    private T[] quickSort(T[] objects, Comparator<T> comparator) {
        objects = trimArray(objects);
        if (objects.length < 2) {
            // base case, arrays with 0 or 1 element are already "sorted"
            return objects;
        } else {
            // recursive case
            T pivot = objects[0];
            T[] less = (T[]) new Object[objects.length];
            int lessCounter = 0;
            T[] greater = (T[]) new Object[objects.length];
            int greaterCounter = 0;
            for (int i = 1; i < objects.length; i++) {
                // sub-array of all the elements less than the pivot
                if (comparator.compare(objects[i], pivot) <= 0) {
                    less[lessCounter] = objects[i];
                    lessCounter++;
                } else {
                    // sub-array of all the elements greater than the pivot
                    greater[greaterCounter] = objects[i];
                    greaterCounter++;
                }
            }
            less = trimArray(less);
            greater = trimArray(greater);
            return concatArrays(concatArrays(
                            quickSort(less, comparator),
                            (T[]) new Object[]{pivot}),
                    quickSort(greater, comparator));
        }
    }

    /**
     * Private method, which checks is array able to save one object.
     * If it is not possible, method increases array capacity.
     *
     * @param size - is array able to save given number of objects.
     */
    private void ensureCapacity(int size) {
        if (size > capacity) {
            capacity = capacity * 3 / 2 + 1;
            T[] newArray = (T[]) new Object[capacity];
            System.arraycopy(elementData, 0, newArray, 0, elementData.length);
            elementData = newArray;
        }
    }

    /**
     * Private util method to delete null objects from end of given array
     *
     * @param array - given array
     * @return - trimmed array without null objects in the end.
     */
    private T[] trimArray(Object[] array) {
        int i = array.length - 1;
        while (i > 0) {
            if (array[i] == null) {
                i--;
            } else break;
        }
        T[] newArray = (T[]) new Object[i + 1];
        for (int j = 0; j < newArray.length; j++) {
            newArray[j] = (T) array[j];
        }
        return newArray;
    }

    /**
     * Private method to concat two arrays with the same type objects.
     *
     * @param arrayFirst  - first array
     * @param arraySecond - second array.
     * @return - new array with elements of first and second array.
     */
    private T[] concatArrays(T[] arrayFirst, T[] arraySecond) {
        if (arrayFirst.length == 0 || arrayFirst[0] == null) {
            return arraySecond;
        }
        if (arraySecond.length == 0 || arraySecond[0] == null) {
            return arrayFirst;
        }
        T[] resultArray = (T[]) new Object[arrayFirst.length + arraySecond.length];
        for (int i = 0; i < resultArray.length; i++) {
            if (i < arrayFirst.length) {
                resultArray[i] = arrayFirst[i];
            } else {
                resultArray[i] = arraySecond[i - arrayFirst.length];
            }
        }
        return resultArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyArray<?> myArray = (MyArray<?>) o;

        if (size != myArray.size) return false;
        return Arrays.equals(elementData, myArray.elementData);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(elementData);
        result = 31 * result + size;
        return result;
    }
}
