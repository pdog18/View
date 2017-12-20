package com.example.a18.path.smartanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by pdog on 2017/12/20.
 */

public class MockChartData {

    int size = 149;
    int highest = 50000;

    public int getSize() {
        return size;
    }

    public int getHighest() {
        return highest;
    }

    List<Integer> top = new ArrayList<>();
    List<Integer> middle = new ArrayList<>();
    List<Integer> bottom = new ArrayList<>();

    public List<Integer> getBottom() {
        return bottom;
    }

    public List<Integer> getMiddle() {
        return middle;
    }

    public List<Integer> getTop() {
        return top;
    }

    {
        Random random = new Random();

        for (int i = 0; i < 150; i++) {
            // 10000  -  100000
            int nextInt = random.nextInt(100000) + 10000;
            changHighest(nextInt);
            top.add(nextInt);

        }

        for (int i = 0; i < 150; i++) {
            int nextInt = random.nextInt(20000) + 10000;
            changHighest(nextInt);
            middle.add(nextInt);
        }

        for (int i = 0; i < 150; i++) {
            int nextInt = random.nextInt(10000) + 10000;
            changHighest(nextInt);
            bottom.add(nextInt);
        }
    }

    private void changHighest(int nextInt) {
        if (nextInt > highest) {
            highest = nextInt;
        }
    }


}
