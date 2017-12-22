package com.example.a18.path.smartanalysis;


import android.graphics.Path;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Chart数据的帮助类，帮助解析服务器返回的数据。
 */
public class ChartDataHelper {


    private int size;

    List<String> dates;

    List<List<Double>> lists;

    List<Integer> risks;

    private double topMost;
    private double bottomEnd;

    public ChartDataHelper(MockSmartAnalysis mockdata) {
        dates = mockdata.getXList();

        lists = new ArrayList<>();

        int costsize = mockdata.getAssetCostIndexList().size();
//        int debetsize = mockdata.getDebetIndexList().size();
//        int valuesize = mockdata.getAssetValueIndexList().size();
        // TODO: 2017/12/22 初始化数据

        double[] assetCostIndex = new double[size];
        double assetCost = mockdata.getAssetCost();
        for (int i = 0; i < size; i++) {
            double v = assetCost * mockdata.getAssetCostIndexList().get(i);
            if (topMost < v){
                topMost = v;
            }
            assetCostIndex[i] = v;
        }

        lists.add(mockdata.getAssetCostIndexList());
        lists.add(mockdata.getDebetIndexList());
        lists.add(mockdata.getAssetValueIndexList());
        risks = mockdata.getRiskGradeList();

    }

    /**
     * 1. 获触摸点对应的日期
     *
     * @return
     */
    public String getDateAtIndex(MotionEvent event, float offset, int width) {
        return dates.get(getCellIndex(event, offset, width));
    }

    /**
     * 2. 创建对应的Path
     */
    public Path getPath() {
        return new Path();
    }

    /**
     * 通过当前的触摸点 获得触摸点与path相交的 y坐标
     * @param event  触摸点
     * @param offset 偏移量，一般为左侧的空隙，leftgap
     * @param type   对应的path
     * @return
     */
    public float getHeightOnPath(MotionEvent event, float offset, int type) {
        // FIXME: 2017/12/22
        return 0f;
    }


    /**
     * 通过触摸点获得
     *
     * @param event  触摸点
     * @param offset 偏移量，一般为左侧的空隙，leftgap
     * @param width  chart图表的坐标系的宽度，一般为控件宽度减去左右两边
     * @return 当前触摸点位于的数据对应的index
     */
    public int getCellIndex(MotionEvent event, float offset, int width) {
        float x = event.getX() - offset;
        float cellwidth = width * 1.0f / size;
        return (int) (x / cellwidth);
    }

    public String getStartDate() {
        return dates.get(0);
    }

    public String getEndDate() {
        return dates.get(dates.size() - 1);
    }
}
