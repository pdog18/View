package com.example.a18.path.smartanalysis;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockChartData {

    int size = 49;
    int highest = 50000;
    int listSize = 3;

    public int getSize() {
        return size;
    }

    public int getHighest() {
        return highest;
    }

    List<List<Integer>> all = new ArrayList<>();


    {

        Random random = new Random();

        for (int i = 0; i < listSize; i++) {
            List<Integer> top = new ArrayList<>();
            int range;
            if (i == 0) {
                range = 100000;
            } else if (i == 1) {
                range = 20000;
            } else {
                range = 10000;
            }

            for (int j = 0; j <= size; j++) {
                // 10000  -  100000
                int nextInt = random.nextInt(range) + 10000;
                changHighest(nextInt);
                top.add(nextInt);
            }

            all.add(top);

        }

    }

    public List<List<Integer>> getAll() {
        return all;
    }

    int topColor = Color.parseColor("#97ed4c");
    int middleColor = Color.parseColor("#47f8d0");
    int bottomColor = Color.parseColor("#ffffff");
    int[] colors = {topColor, middleColor, bottomColor};

    public int getColor(int index) {
        return colors[index];
    }

    private void changHighest(int nextInt) {
        if (nextInt > highest) {
            highest = nextInt;
        }
    }


}
