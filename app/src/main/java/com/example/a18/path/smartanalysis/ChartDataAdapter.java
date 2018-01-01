package com.example.a18.path.smartanalysis;


import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Chart数据的帮助类，帮助解析服务器返回的数据。
 */
public class ChartDataAdapter {

    private int size;

    private String[] dates;

    private List<double[]> lists;
    private List<PointF[]> pointFList;       //在坐标上的值
    private List<Range> riskGradeMap;
    private List<Range> policyMap;

    private int[] riskGradeArray;
    private int[] tradeStrategyArray;

    private double topMost;
    private double bottomEnd;

    private List<Arrow> arrows;


    public ChartDataAdapter(MockSmartAnalysis mockdata) {

        size = checkDataSize(mockdata);

        lists = new ArrayList<>();

        //顺序添加三组数据
        lists.add(getValueByRate(mockdata.getAssetCost(), mockdata.getAssetValueIndexList()));
        lists.add(getValueByRate(mockdata.getDebetGrowth(), mockdata.getDebetIndexList()));
        lists.add(getValueByRate(mockdata.getAssetValue(), mockdata.getAssetValueIndexList()));


        //模拟 曲线有交叉点
        for (int i = 0; i < 10; i++) {
            lists.get(2)[10 + i] *= 4;
        }

        //初始化评级分区和策略分区
        riskGradeMap = makeRange(mockdata.getRiskGradeList());
        policyMap = makeRange(mockdata.getTradeStrategyList());
        riskGradeArray = list2Array(mockdata.getRiskGradeList());
        tradeStrategyArray = list2Array(mockdata.getTradeStrategyList());

        dates = mapDate(mockdata.getXList());
        pointFList = new ArrayList<>();
    }

    private int[] list2Array(List<Integer> list) {
        int[] result = new int[size];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private String[] mapDate(List<String> xList) {
        return xList.toArray(new String[size]);
    }

    /**
     * 这个方法会创建一个 Range的集合 ，集合中顺序存储评级/策略有改变的时候的索引
     *
     * @param list
     */
    private List<Range> makeRange(List<Integer> list) {
        List<Range> ranges = new ArrayList<>();

        int last = -1;
        for (int i = 0; i < size; i++) {
            int curr = list.get(i);

            if (last != curr) {
                if (!ranges.isEmpty()) {    //调整上一个Range的结束点
                    ranges.get(ranges.size() - 1).endIndex = i;
                }
                Range range = new Range();
                range.startIndex = i;
                range.grade = curr;
                Timber.d("makeRange:不相等，也不是第一个,向集合中添加 %s", curr);
                ranges.add(range);
            }
            if (i == size - 1) {        //确定最后一个的Range的结束点
                ranges.get(ranges.size() - 1).endIndex = i;
            }
            last = curr;
        }

        return ranges;
    }

    //通过返回的比率获得对应坐标的具体数值
    private double[] getValueByRate(double benchmark, List<Double> list) {
        double[] result = new double[size];

        for (int i = 0; i < size; i++) {
            double v = benchmark * list.get(i);
            updateTopMostBottomEnd(v);
            result[i] = v;
        }
        return result;
    }


    /**
     * 检查服务器返回的数据的长度是否相等
     *
     * @param mockdata 服务器返回的bean
     */
    private int checkDataSize(MockSmartAnalysis mockdata) {
        int assetCostSize = mockdata.getAssetCostIndexList().size();
        int debetIndexSize = mockdata.getDebetIndexList().size();
        int assetValueSize = mockdata.getAssetValueIndexList().size();
        int xListSize = mockdata.getXList().size();
        int riskGradeSize = mockdata.getRiskGradeList().size();
        int tradeStrategySize = mockdata.getTradeStrategyList().size();

        if (assetCostSize > 2
                && assetCostSize == debetIndexSize
                && assetCostSize == assetValueSize
                && assetCostSize == xListSize
                && assetCostSize == riskGradeSize
                && assetCostSize == tradeStrategySize) {
            return assetCostSize;
        }

        throw new ArrayIndexOutOfBoundsException("返回数据size，小于2或者不相等");
    }

    private void updateTopMostBottomEnd(double v) {
        if (topMost < v) {
            topMost = v;
        } else if (bottomEnd > v) {
            bottomEnd = v;
        }
    }

    /**
     * @return 触摸点对应的日期
     */
    public String getDateAtIndex(float touchX, float cellWidth) {
        return dates[getCellIndex(touchX, cellWidth)];
    }

    /**
     * 2. 记录映射到图表上的所有的坐标，创建对应的Path
     */
    public Path buildPath(double[] list, float cellWidth, float chartRegionHeight) {

        topmostAfterMapping = valueMap(getTopmost(), chartRegionHeight);

        PointF[] points = new PointF[size];
        Path path = new Path();

        points[0] = new PointF(0, valueMap(list[0], chartRegionHeight));
        path.moveTo(points[0].x, points[0].y);

        for (int i = 1; i < size; i++) {
            PointF f = new PointF(cellWidth * i, valueMap(list[i], chartRegionHeight));
            points[i] = f;
            path.lineTo(f.x, f.y);
        }
        pointFList.add(points);

        return path;
    }

    private int valueMap(double before, float chartRegionHeight) {
        double topmost = getTopmost();
        return (int) (-before * (chartRegionHeight * 1.0f / topmost));
    }

    /**
     * 和#valueMap ,不同的是，这里使用的在图表中的像素高度
     *
     * @return
     */
    private int valueMapAtChart(double before, float chartRegionHeight) {
        double topmost = getTopmostAfterMapping();
        return (int) (-before * (chartRegionHeight * 1.0f / topmost));
    }


    /**
     * 3. 通过当前的触摸点 获得触摸点与path相交的 y坐标
     *
     * @param touchX 触摸点
     * @param type   对应的path
     * @return
     */
    public float getHeightOnPath(float touchX, float cellWidth, int type, float chartRegionHeight) {
        PointF[] points = getPointFList(type);

        // 如果处在最右边，那么返回最后一个坐标的高度
        if (getCellIndex(touchX, cellWidth) == size - 1) {
            return points[size - 1].y;
        } else {
            int index = getCellIndex(touchX, cellWidth);
            PointF leftPoint = points[index];
            PointF rightPoint = points[index + 1];

            float leftY = leftPoint.y;
            float rightY = rightPoint.y;

            float leftX = leftPoint.x;

            float offsetY = rightY - leftY;

            float radio = (touchX - leftX) / cellWidth;
            return valueMapAtChart((offsetY * radio + leftY), chartRegionHeight);
        }
    }

    public PointF[] getPointFList(int type) {
        return pointFList.get(type);
    }

    /**
     * 4. 根据当前的触摸点，获得触摸点处的[评级]
     *
     * @param touchX
     * @return
     */
    public int getGradeByTouch(float touchX, float width) {
        int index = getCellIndex(touchX, width);
        return riskGradeArray[index];
    }


    /**
     * 5. 根据当前的触摸点，获得触摸点处的[策略]
     *
     * @param touchX
     * @return
     */
    public float getPolicyByTouch(float touchX, int cellWidth) {
        int index = getCellIndex(touchX, cellWidth);
        return tradeStrategyArray[index];
    }

    /**
     * 6. 根据当前的触摸点，获得触摸点处的[突破]
     *
     * @return
     */
    public List<Arrow> getBreakArrow() {
        if (this.arrows != null) {
            return this.arrows;
        }

        PointF[] pointTop = pointFList.get(0);
        PointF[] pointMid = pointFList.get(1);
        PointF[] pointBtm = pointFList.get(2);

        this.arrows = new ArrayList<>();
        List<Arrow> arrows = this.arrows;

        findArrows(pointTop, pointMid, arrows);// 1 和 2 的交叉点
        findArrows(pointTop, pointBtm, arrows);// 1 和 3 的交叉点
        findArrows(pointMid, pointBtm, arrows);// 2 和 3 的交叉点

        return arrows;
    }

    private void findArrows(PointF[] pointTop, PointF[] pointMid, List<Arrow> arrows) {
        for (int i = 0; i < size - 1; i++) {
            PointF firstLeft = pointTop[i];
            PointF firstRight = pointTop[i + 1];
            PointF secondLeft = pointMid[i];
            PointF secondRight = pointMid[i + 1];

            if (isAcrossed(firstLeft, firstRight, secondLeft, secondRight)) {
                Arrow arrow = createArrow(firstLeft, firstRight, secondLeft, secondRight);
                arrows.add(arrow);
            }
        }
    }

    private Arrow createArrow(PointF firstLeft, PointF firstRight, PointF secondLeft, PointF secondRight) {
        //两点确定一条直线，已知四个点确定的两条直线，求这两条直线的交点
        //http://blog.csdn.net/risemypassion/article/details/4646664
        float x0 = firstLeft.x;
        float y0 = firstLeft.y;
        float x1 = firstRight.x;
        float y1 = firstRight.y;

        float x3 = secondLeft.x;
        float y3 = secondLeft.y;
        float x2 = secondRight.x;
        float y2 = secondRight.y;

        float y = ((y0 - y1) * (y3 - y2) * x0 + (y3 - y2) * (x1 - x0) * y0 + (y1 - y0) * (y3 - y2)
                * x2 + (x2 - x3) * (y1 - y0) * y2) / ((x1 - x0) * (y3 - y2) + (y0 - y1) * (x3 - x2));

        float x = x2 + (x3 - x2) * (y - y2) / (y3 - y2);

//        float y = ((firstLeft.y - firstRight.y) * (secondLeft.y - secondRight.y) * firstLeft.x
//                + (secondLeft.y - secondRight.y) * (firstRight.x - firstLeft.x) * firstLeft.y
//                + (firstRight.y - firstLeft.y) * (secondLeft.y - secondRight.y) * secondRight.x
//                + (secondRight.x - secondLeft.x) * (firstRight.y - firstLeft.y) * secondRight.y)
//                / ((firstRight.x - firstLeft.x) * (secondLeft.y - secondRight.y)
//                + (firstLeft.y - firstRight.y) * (secondLeft.x - secondRight.x));
//
//        float x = secondRight.x + (secondLeft.x-secondRight.x)*(y-secondRight.y) / (secondLeft.y-secondRight.y);
        // FIXME: 2017/12/24  这个degree需要根据业务进行判断，需要对传入的point数组顺序有要求
        int degree = firstLeft.x > secondLeft.x ? 90 : 270;
        return new Arrow(x, y, degree);
    }

    private boolean isAcrossed(PointF firstLeft, PointF firstRight,
                               PointF secondLeft, PointF secondRight) {
        //因为这里的y都是负数，所以是相反的
        return (firstLeft.y < secondLeft.y && firstRight.y > secondRight.y)
                || (firstLeft.y > secondLeft.y && firstRight.y < secondRight.y);

    }


    /**
     * 通过触摸点获得区间
     *
     * @param touchX    触摸点
     * @param cellWidth
     * @return 当前触摸点位于的数据对应的index
     */
    public int getCellIndex(float touchX, float cellWidth) {
        return (int) (touchX / cellWidth);
    }

    public String getStartDate() {
        return dates[0];
    }

    public String getEndDate() {
        return dates[dates.length - 1];
    }

    public int getSize() {
        return size;
    }

    public List<Range> getRiskGradeMap() {
        return riskGradeMap;
    }

    public List<Range> getPolicyMap() {
        return policyMap;
    }

    public double[] getLists(int i) {
        return lists.get(i);
    }

    public double getTopmost() {
        return topMost;
    }

    public double getTopmostAfterMapping() {
        return topmostAfterMapping;
    }

    private double topmostAfterMapping;  //映射过后的最高坐标


    /**
     * 评级和策略用的区域的封装
     */
    static class Range {
        int startIndex;
        int endIndex;
        int grade;

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }
    }

    /**
     * 突破使用的箭头，记录了坐标和角度
     */
    static class Arrow {
        float x;
        float y;
        int degree;

        public Arrow(float x, float y, int degree) {
            this.x = x;
            this.y = y;
            this.degree = degree;
        }
    }
}
