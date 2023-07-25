package de.essagl.tuya.aircal.adapter.model;

import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;

import javax.validation.constraints.NotNull;

public class ControlParameter202 implements ControlParameter {
    private byte[] c1c30;
    private byte[] c31c56;
    private static int getInt(byte[] arr, int off) {
        return arr[off]<<8 &0xFF00 | arr[off+1]&0xFF;
    }


    public ControlParameter202(@NotNull byte[] c1c30,@NotNull byte[] c31c56) {
        if (c1c30.length != 60){
            throw new IllegalArgumentException("byte[] c1c30 must be 60 byte, but is "+c1c30.length);
        }
        if (c31c56.length != 52){
            throw new IllegalArgumentException("byte[] c31c56 must be 52 byte, but is "+c31c56.length);
        }
        this.c1c30 = c1c30;
        this.c31c56 = c31c56;
    }

    @Override
    public DoubleLabelValueUnit getC00() {
        return c00;
    }

    @Override
    public DoubleLabelValueUnit getC01() {
        ControlParameter.super.getC01().setValue(Double.valueOf(getInt(c1c30,0))/10);
        return c01;
    }

    @Override
    public DoubleLabelValueUnit getC02() {
        ControlParameter.super.getC02().setValue(Double.valueOf(getInt(c1c30,2))/10);
        return c02;
    }

    @Override
    public DoubleLabelValueUnit getC03() {
        ControlParameter.super.getC03().setValue(Double.valueOf(getInt(c1c30,4))/10);
        return c03;
    }

    @Override
    public DoubleLabelValueUnit getC04() {
        ControlParameter.super.getC04().setValue(Double.valueOf(getInt(c1c30,6))/10);
        return c04;
    }

    @Override
    public DoubleLabelValueUnit getC05() {
        ControlParameter.super.getC05().setValue(Double.valueOf(getInt(c1c30,8))/10);
        return c05;
    }

    @Override
    public DoubleLabelValueUnit getC06() {
        ControlParameter.super.getC06().setValue(Double.valueOf(getInt(c1c30,10))/10);
        return c06;
    }

    @Override
    public DoubleLabelValueUnit getC07() {
        ControlParameter.super.getC07().setValue(Double.valueOf(getInt(c1c30,12))/10);
        return c07;
    }

    @Override
    public DoubleLabelValueUnit getC08() {
        ControlParameter.super.getC08().setValue(Double.valueOf(getInt(c1c30,14))/10);
        return c08;
    }

    @Override
    public DoubleLabelValueUnit getC09() {
        ControlParameter.super.getC09().setValue(Double.valueOf(getInt(c1c30,16))/10);
        return c09;
    }

    @Override
    public DoubleLabelValueUnit getC10() {
        ControlParameter.super.getC10().setValue(Double.valueOf(getInt(c1c30,18)));
        return c10;
    }

    @Override
    public DoubleLabelValueUnit getC11() {
        ControlParameter.super.getC11().setValue(Double.valueOf(getInt(c1c30,20))/10);
        return c11;
    }

    @Override
    public DoubleLabelValueUnit getC12() {
        ControlParameter.super.getC12().setValue(Double.valueOf(getInt(c1c30,22))/10);
        return c12;
    }

    @Override
    public DoubleLabelValueUnit getC13() {
        ControlParameter.super.getC13().setValue(Double.valueOf(getInt(c1c30,24)));
        return c13;
    }

    @Override
    public DoubleLabelValueUnit getC14() {
        ControlParameter.super.getC14().setValue(Double.valueOf(getInt(c1c30,26)));
        return c14;
    }

    @Override
    public DoubleLabelValueUnit getC15() {
        ControlParameter.super.getC15().setValue(Double.valueOf(getInt(c1c30,28)));
        return c15;
    }

    @Override
    public DoubleLabelValueUnit getC16() {
        ControlParameter.super.getC16().setValue(Double.valueOf(getInt(c1c30,30)));
        return c16;
    }

    @Override
    public DoubleLabelValueUnit getC17() {
        ControlParameter.super.getC17().setValue(Double.valueOf(getInt(c1c30,32)));
        return c17;
    }

    @Override
    public DoubleLabelValueUnit getC18() {
        ControlParameter.super.getC18().setValue(Double.valueOf(getInt(c1c30,34)));
        return c18;
    }

    @Override
    public DoubleLabelValueUnit getC19() {
        ControlParameter.super.getC19().setValue(Double.valueOf(getInt(c1c30,36)));
        return c19;
    }

    @Override
    public DoubleLabelValueUnit getC20() {
        ControlParameter.super.getC20().setValue(Double.valueOf(getInt(c1c30,38)));
        return c20;
    }

    @Override
    public DoubleLabelValueUnit getC21() {
        ControlParameter.super.getC21().setValue(Double.valueOf(getInt(c1c30,40)));
        return c21;
    }

    @Override
    public DoubleLabelValueUnit getC22() {
        ControlParameter.super.getC22().setValue(Double.valueOf(getInt(c1c30,42))/10);
        return c22;
    }

    @Override
    public DoubleLabelValueUnit getC23() {
        ControlParameter.super.getC23().setValue(Double.valueOf(getInt(c1c30,44)));
        return c23;
    }

    @Override
    public DoubleLabelValueUnit getC24() {
        ControlParameter.super.getC24().setValue(Double.valueOf(getInt(c1c30,46)));
        return c24;
    }

    @Override
    public DoubleLabelValueUnit getC25() {
        ControlParameter.super.getC25().setValue(Double.valueOf(getInt(c1c30,48))/10);
        return c25;
    }

    @Override
    public DoubleLabelValueUnit getC26() {
        ControlParameter.super.getC26().setValue(Double.valueOf(getInt(c1c30,50))/10);
        return c26;
    }

    @Override
    public DoubleLabelValueUnit getC27() {
        ControlParameter.super.getC27().setValue(Double.valueOf(getInt(c1c30,52))/10);
        return c27;
    }

    @Override
    public DoubleLabelValueUnit getC28() {
        ControlParameter.super.getC28().setValue(Double.valueOf(getInt(c1c30,54))/10);
        return c28;
    }

    @Override
    public DoubleLabelValueUnit getC29() {
        ControlParameter.super.getC29().setValue(Double.valueOf(getInt(c1c30,56)));
        return c29;
    }

    @Override
    public DoubleLabelValueUnit getC30() {
        ControlParameter.super.getC30().setValue(Double.valueOf(getInt(c1c30,58)));
        return c30;
    }

    @Override
    public DoubleLabelValueUnit getC31() {
        ControlParameter.super.getC31().setValue(Double.valueOf(getInt(c31c56,0)));
        return c31;
    }

    @Override
    public DoubleLabelValueUnit getC32() {
        ControlParameter.super.getC32().setValue(Double.valueOf(getInt(c31c56,2)));
        return c32;
    }

    @Override
    public DoubleLabelValueUnit getC33() {
        ControlParameter.super.getC33().setValue(Double.valueOf(getInt(c31c56,4)));
        return c33;
    }

    @Override
    public DoubleLabelValueUnit getC34() {
        ControlParameter.super.getC34().setValue(Double.valueOf(getInt(c31c56,6)));
        return c34;
    }

    @Override
    public DoubleLabelValueUnit getC35() {
        ControlParameter.super.getC35().setValue(Double.valueOf(getInt(c31c56,8)));
        return c35;
    }

    @Override
    public DoubleLabelValueUnit getC36() {
        ControlParameter.super.getC36().setValue(Double.valueOf(getInt(c31c56,10)));
        return c36;
    }

    @Override
    public DoubleLabelValueUnit getC37() {
        ControlParameter.super.getC37().setValue(Double.valueOf(getInt(c31c56,12)));
        return c37;
    }

    @Override
    public DoubleLabelValueUnit getC38() {
        ControlParameter.super.getC38().setValue(Double.valueOf(getInt(c31c56,14)));
        return c38;
    }

    @Override
    public DoubleLabelValueUnit getC39() {
        ControlParameter.super.getC39().setValue(Double.valueOf(getInt(c31c56,16)));
        return c39;
    }

    @Override
    public DoubleLabelValueUnit getC40() {
        ControlParameter.super.getC40().setValue(Double.valueOf(getInt(c31c56,18)));
        return c40;
    }

    @Override
    public DoubleLabelValueUnit getC41() {
        ControlParameter.super.getC41().setValue(Double.valueOf(getInt(c31c56,20)));
        return c41;
    }

    @Override
    public DoubleLabelValueUnit getC42() {
        ControlParameter.super.getC42().setValue(Double.valueOf(getInt(c31c56,22)));
        return c42;
    }

    @Override
    public DoubleLabelValueUnit getC43() {
        ControlParameter.super.getC43().setValue(Double.valueOf(getInt(c31c56,24)));
        return c43;
    }

    @Override
    public DoubleLabelValueUnit getC44() {
        ControlParameter.super.getC44().setValue(Double.valueOf(getInt(c31c56,26)));
        return c44;
    }

    @Override
    public DoubleLabelValueUnit getC45() {
        ControlParameter.super.getC45().setValue(Double.valueOf(getInt(c31c56,28)));
        return c45;
    }

    @Override
    public DoubleLabelValueUnit getC46() {
        ControlParameter.super.getC46().setValue(Double.valueOf(getInt(c31c56,30)));
        return c46;
    }

    @Override
    public DoubleLabelValueUnit getC47() {
        ControlParameter.super.getC47().setValue(Double.valueOf(getInt(c31c56,32)));
        return c47;
    }

    @Override
    public DoubleLabelValueUnit getC48() {
        ControlParameter.super.getC48().setValue(Double.valueOf(getInt(c31c56,34)));
        return c48;
    }

    @Override
    public DoubleLabelValueUnit getC49() {
        ControlParameter.super.getC49().setValue(Double.valueOf(getInt(c31c56,36)));
        return c49;
    }

    @Override
    public DoubleLabelValueUnit getC50() {
        ControlParameter.super.getC50().setValue(Double.valueOf(getInt(c31c56,38)));
        return c50;
    }

    @Override
    public DoubleLabelValueUnit getC51() {
        ControlParameter.super.getC51().setValue(Double.valueOf(getInt(c31c56,40)));
        return c51;
    }

    @Override
    public DoubleLabelValueUnit getC52() {
        ControlParameter.super.getC52().setValue(Double.valueOf(getInt(c31c56,42)));
        return c52;
    }

    @Override
    public DoubleLabelValueUnit getC53() {
        ControlParameter.super.getC53().setValue(Double.valueOf(getInt(c31c56,44)));
        return c53;
    }

    @Override
    public DoubleLabelValueUnit getC54() {
        ControlParameter.super.getC54().setValue(Double.valueOf(getInt(c31c56,46)));
        return c54;
    }

    @Override
    public DoubleLabelValueUnit getC55() {
        ControlParameter.super.getC55().setValue(Double.valueOf(getInt(c31c56,48)));
        return c55;
    }

    @Override
    public DoubleLabelValueUnit getC56() {
        ControlParameter.super.getC56().setValue(Double.valueOf(getInt(c31c56,50)));
        return c56;
    }
}
