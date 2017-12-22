package com.example.a18.path.smartanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import timber.log.Timber;

public class MockSmartAnalysis {

    private double topmost;

    public static MockSmartAnalysis getInstance() {
        MockSmartAnalysis mock = new MockSmartAnalysis();
        mock.setAssetCost(126000);
        mock.setDebetGrowth(12000);
        mock.setAssetValue(4000);

        int size = 50;
        double topmost = 1;

        ArrayList<Double> assetCostIndexList = new ArrayList<>();
        ArrayList<Double> assetValueIndexList = new ArrayList<>();
        ArrayList<Double> debetIndexList = new ArrayList<>();
        ArrayList<Integer> riskGradeList = new ArrayList<>();
        ArrayList<String> xList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            assetCostIndexList.add((random.nextDouble() + 1) * mock.getAssetCost());
            assetValueIndexList.add((random.nextDouble() + 1) * mock.getAssetValue());
            debetIndexList.add((random.nextDouble() + 1) * mock.getDebetGrowth());
            riskGradeList.add(i / 10);
            xList.add(String.format("2017-12-%s", i));
        }

        for (int i = 0; i < size; i++) {
            if (assetCostIndexList.get(i) > topmost) {
                topmost = assetCostIndexList.get(i);
            }
            if (assetValueIndexList.get(i) > topmost) {
                topmost = assetValueIndexList.get(i);
            }
            if (debetIndexList.get(i) > topmost) {
                topmost = debetIndexList.get(i);
            }
        }
        Timber.d("topmost = %s", topmost);


        mock.setAssetCostIndexList(assetCostIndexList);
        mock.setAssetValueIndexList(assetValueIndexList);
        mock.setDebetIndexList(debetIndexList);
        mock.setRiskGradeList(riskGradeList);
        mock.setXList(xList);
        mock.setTopmost(topmost);

        List<GradeRange> gradeRange = new ArrayList<>();

        int size1 = riskGradeList.size();
        for (int i = 0; i < size1; i++) {
            if (i == 0) {
                //第一个评级
                GradeRange range = new GradeRange();
                range.startIndex = i;
                range.risk = getGradeByIndex(i);
                gradeRange.add(range);
            } else {
//                如果后面的评级与前面一个评级不相同，那么就添加一个，如果相同就修改前一个的endIndex
                Integer last = riskGradeList.get(i - 1);
                Integer curr = riskGradeList.get(i);
                if (!curr.equals(last)) {
                    GradeRange range = new GradeRange();
                    range.startIndex = i;
                    range.risk = getGradeByIndex(i);
                    gradeRange.add(range);
                }else {
                    GradeRange range = gradeRange.get(gradeRange.size() - 1);
                    range.endIndex = i;
                }
            }

            if (i == size1 - 1) {
                gradeRange.get(gradeRange.size() - 1).endIndex = i;
            }
        }

        mock.setGradeRange(gradeRange);

        return mock;
    }

    public static String getGradeByIndex(Integer integer) {
        switch (integer) {
            case 0:
                return "A+";
            case 10:
                return "A";
            case 20:
                return "B";
            case 30:
                return "C";
            default:
                return "D";
        }
    }

    public String findRiskFormX(float cellWidth ,int x) {
        List<GradeRange> gradeRange = getGradeRange();
        return gradeRange.get(getIndex(cellWidth, x, gradeRange)).risk;
    }


    public String findLastRiskFormX(float cellWidth, int x) {
        int index = getIndex(cellWidth, x, gradeRange);
        if (index == 0) {
            return null;
        }else {
            return gradeRange.get(index).risk;
        }
    }

    public int getIndex(float cellWidth, int x, List<GradeRange> gradeRange) {
        for (int i = 0; i < gradeRange.size(); i++) {
            GradeRange range = gradeRange.get(i);
            //减去两个range的start和end之间的区域
            float start = range.startIndex * cellWidth - cellWidth;
            float end = range.endIndex * cellWidth;
            if (start <= x && x < end) {
                return i;
            }
        }
        return gradeRange.size() -1;
    }


    /**
     * assetValueSlopeList : [null,0,0]
     * debetIndexList : [1.9357,1.9357,1.9357]
     * xList : ["2017-12-15","2017-12-16","2017-12-18"]
     * tradeStrategy :
     * assetValueIndexList : [0,0,0]
     * debetSlopeList : [null,0,0]
     * tradeStrategyList : [5,5,5]
     * assetCostSlopeList : [null,8516.06,8516.06]
     * riskGrade : D
     * assetCostIndexList : [1,1.0008,1.0023]
     * riskGradeList : [5,5,5]
     */

    private double assetCost;
    private double assetValue;
    private String tradeStrategy;
    private double debetGrowth;
    private String riskGrade;
    private List<Double> assetValueSlopeList;
    private List<Double> debetIndexList;
    private List<String> xList;
    private List<Double> assetValueIndexList;
    private List<Double> debetSlopeList;
    private List<Integer> tradeStrategyList;
    private List<Double> assetCostSlopeList;
    private List<Double> assetCostIndexList;
    private List<Integer> riskGradeList;

    public double getAssetCost() {
        return assetCost;
    }

    public void setAssetCost(double assetCost) {
        this.assetCost = assetCost;
    }

    public double getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(double assetValue) {
        this.assetValue = assetValue;
    }

    public String getTradeStrategy() {
        return tradeStrategy;
    }

    public void setTradeStrategy(String tradeStrategy) {
        this.tradeStrategy = tradeStrategy;
    }

    public double getDebetGrowth() {
        return debetGrowth;
    }

    public void setDebetGrowth(double debetGrowth) {
        this.debetGrowth = debetGrowth;
    }

    public String getRiskGrade() {
        return riskGrade;
    }

    public void setRiskGrade(String riskGrade) {
        this.riskGrade = riskGrade;
    }

    public List<Double> getAssetValueSlopeList() {
        return assetValueSlopeList;
    }

    public void setAssetValueSlopeList(List<Double> assetValueSlopeList) {
        this.assetValueSlopeList = assetValueSlopeList;
    }

    public List<Double> getDebetIndexList() {
        return debetIndexList;
    }

    public void setDebetIndexList(List<Double> debetIndexList) {
        this.debetIndexList = debetIndexList;
    }

    public List<String> getXList() {
        return xList;
    }

    public void setXList(List<String> xList) {
        this.xList = xList;
    }

    public List<Double> getAssetValueIndexList() {
        return assetValueIndexList;
    }

    public void setAssetValueIndexList(List<Double> assetValueIndexList) {
        this.assetValueIndexList = assetValueIndexList;
    }

    public List<Double> getDebetSlopeList() {
        return debetSlopeList;
    }

    public void setDebetSlopeList(List<Double> debetSlopeList) {
        this.debetSlopeList = debetSlopeList;
    }

    public List<Integer> getTradeStrategyList() {
        return tradeStrategyList;
    }

    public void setTradeStrategyList(List<Integer> tradeStrategyList) {
        this.tradeStrategyList = tradeStrategyList;
    }

    public List<Double> getAssetCostSlopeList() {
        return assetCostSlopeList;
    }

    public void setAssetCostSlopeList(List<Double> assetCostSlopeList) {
        this.assetCostSlopeList = assetCostSlopeList;
    }

    public List<Double> getAssetCostIndexList() {
        return assetCostIndexList;
    }

    public void setAssetCostIndexList(List<Double> assetCostIndexList) {
        this.assetCostIndexList = assetCostIndexList;
    }

    public List<Integer> getRiskGradeList() {
        return riskGradeList;
    }

    public void setRiskGradeList(List<Integer> riskGradeList) {
        this.riskGradeList = riskGradeList;
    }

    public void setTopmost(double topmost) {
        this.topmost = topmost;
    }

    public double getTopmost() {
        return topmost;
    }

    List<GradeRange> gradeRange;

    public List<GradeRange> getGradeRange() {
        return gradeRange;
    }

    public void setGradeRange(List<GradeRange> gradeRange) {
        this.gradeRange = gradeRange;
    }

    static class GradeRange {
        int startIndex;
        int endIndex;
        String risk;
    }
}
