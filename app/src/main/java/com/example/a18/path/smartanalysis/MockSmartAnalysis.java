package com.example.a18.path.smartanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockSmartAnalysis {

    public static MockSmartAnalysis createMock() {
        MockSmartAnalysis data = new MockSmartAnalysis();
        int size = 30;

        data.setAssetCost(126000);
        data.setAssetValue(50000);
        data.setDebetGrowth(40000);

        List<String> date = new ArrayList<>();
        List<Double> assetsCostList = new ArrayList<>();
        List<Double> debetIndexList = new ArrayList<>();
        List<Double> assetsValueList = new ArrayList<>();
        List<Integer> riskGradeList = new ArrayList<>();
        List<Integer> tradeStrategyList = new ArrayList<>();
        List<Double> list1 = new ArrayList<>();
        List<Double> list2 = new ArrayList<>();
        List<Double> list3 = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            double doubleValue = random.nextDouble() + 0.5d;
            date.add("2017-12-" + i);
            assetsCostList.add(doubleValue);
            debetIndexList.add(doubleValue);
            assetsValueList.add(doubleValue);

            riskGradeList.add(i / 10);
            tradeStrategyList.add(i / 17);
            list1.add(doubleValue);
            list2.add(doubleValue);
            list3.add(doubleValue);
        }

        data.setXList(date);
        data.setAssetCostIndexList(assetsCostList);
        data.setDebetIndexList(debetIndexList);
        data.setAssetValueIndexList(assetsValueList);
        data.setRiskGradeList(riskGradeList);
        data.setTradeStrategyList(tradeStrategyList);
        data.setAssetValueSlopeList(list1);
        data.setDebetSlopeList(list3);
        data.setAssetCostSlopeList(list2);

        return data;
    }


    private String tradeStrategy;
    private double assetCost;
    private double assetValue;
    private double debetGrowth;
    private String riskGrade;
    private List<String> xList;
    private List<Double> debetIndexList;
    private List<Double> assetValueIndexList;
    private List<Double> assetCostIndexList;
    private List<Integer> tradeStrategyList;
    private List<Integer> riskGradeList;

    private List<Double> assetValueSlopeList;
    private List<Double> debetSlopeList;
    private List<Double> assetCostSlopeList;

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
}
