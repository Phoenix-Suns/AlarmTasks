package com.windyroad.nghia.common.models;

/**
 * Created by Nghia-PC on 7/14/2015.
 * Kiểu đơn giản, chứa Giá trị và Hiển thị
 */
public class SimpleType {
    public String DisplayMember;
    public String ValueMember;

    public SimpleType() {
        this.ValueMember = "";
        this.DisplayMember = "";
    }
    public SimpleType(String _valueMember, String _displayMember) {
        this.ValueMember = _valueMember;
        this.DisplayMember = _displayMember;
    }


    public String getDisplayMember() {
        return DisplayMember;
    }
    public String getValueMember() {
        return ValueMember;
    }
    public void setDisplayMember(String displayMember) {
        DisplayMember = displayMember;
    }
    public void setValueMember(String valueMember) {
        ValueMember = valueMember;
    }

    @Override
    public String toString() {
        return this.getDisplayMember();
    }
}
