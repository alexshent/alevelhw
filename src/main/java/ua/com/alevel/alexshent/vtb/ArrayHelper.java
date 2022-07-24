package ua.com.alevel.alexshent.vtb;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayHelper<T> {

    /**
     * Написать метод, который меняет два элемента массива местами (массив может быть любого
     * ссылочного типа)
     * @param array referenced type array
     * @param firstElementIndex zero based index of the first element
     * @param secondElementIndex zero based index of the second element
     */
    public void swapTwoElements(T[] array, int firstElementIndex, int secondElementIndex) {
        T temp = array[firstElementIndex];
        array[firstElementIndex] = array[secondElementIndex];
        array[secondElementIndex] = temp;
    }

    /**
     * Написать метод, который преобразует массив в ArrayList
     */
    public ArrayList<T> toArrayList(T[] array) {
        return new ArrayList<>(Arrays.asList(array));
    }
}
