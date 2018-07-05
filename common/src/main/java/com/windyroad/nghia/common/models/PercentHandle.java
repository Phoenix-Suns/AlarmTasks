package com.windyroad.nghia.common.models;

/**
 * Created by Nghia-PC on 7/16/2015.
 * Xử lý Phần trăm lấy về phần trăm
 */
public class PercentHandle {
    float minValue;
    float maxValue;

    public PercentHandle(float minValue, float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    // ---- Methods -----

    /**
     * Lấy phần trăm
     * @param value giá trị truyền vào. min < value < max
     */
    public int getPercent(float value){
        return getPercent(maxValue, minValue, value);
    }

    public static int getPercent(float maxValue, float minValue, float value){
        float minimum = value - maxValue;
        float maximum = maxValue - minValue;
        return (int) (minimum * 100 / maximum);
    }
}
