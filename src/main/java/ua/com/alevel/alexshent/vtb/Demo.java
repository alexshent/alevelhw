package ua.com.alevel.alexshent.vtb;

import java.util.List;

public class Demo {
    public void useArrayHelper() {
        Integer[] array = {1, 2, 3, 4, 5};
        int firstElementIndex = 2;
        int secondElementIndex = 4;
        ArrayHelper<Integer> arrayHelper = new ArrayHelper<>();
        arrayHelper.swapTwoElements(array, firstElementIndex, secondElementIndex);
        for (Integer element : array) {
            System.out.println(element);
        }

        List<Integer> arrayList = arrayHelper.toArrayList(array);
        System.out.println(arrayList.getClass().getName());
        for (Integer element : arrayList) {
            System.out.println(element);
        }
    }

    public void useFruitBox() {
        Apple apple1 = new Apple();
        Apple apple2 = new Apple();
        Apple apple3 = new Apple();
        Box<Apple> appleBox1 = new Box<>();
        appleBox1.add(apple1);
        appleBox1.add(apple2);
        appleBox1.add(apple3);

        Orange orange1 = new Orange();
        Orange orange2 = new Orange();
        Box<Orange> orangeBox = new Box<>();
        orangeBox.add(orange1);
        orangeBox.add(orange2);

        System.out.println("apple box weight = " + appleBox1.getWeight());
        System.out.println("orange box weight = " + orangeBox.getWeight());
        System.out.println(appleBox1.compareWeight(orangeBox));

        Apple apple4 = new Apple();
        Box<Apple> appleBox2 = new Box<>();
        appleBox2.add(apple4);

        appleBox1.moveContents(appleBox2);
        System.out.println("apple box1 weight = " + appleBox1.getWeight());
        System.out.println("apple box2 weight = " + appleBox2.getWeight());
    }
}
