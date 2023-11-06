package de.essagl.tuya.aircal.adapter.model;

import de.essagl.tuya.aircal.adapter.ability.model.DoubleLabelValueUnit;

public interface ControlParameter {
    DoubleLabelValueUnit c00 = new DoubleLabelValueUnit("c00","Coil temp",null,"°C");
     DoubleLabelValueUnit c01 = new DoubleLabelValueUnit("c01","Discharge temp",null,"°C");
     DoubleLabelValueUnit c02 = new DoubleLabelValueUnit("c02","Ambient temp",null,"°C");
     DoubleLabelValueUnit c03 = new DoubleLabelValueUnit("c03","Suction temp",null,"°C");
     DoubleLabelValueUnit c04 = new DoubleLabelValueUnit("c04","EVI Inlet temp",null,"°C");
     DoubleLabelValueUnit c05 = new DoubleLabelValueUnit("c05","EVI Outlet temp",null,"°C");
     DoubleLabelValueUnit c06 = new DoubleLabelValueUnit("c06","Refrigerant liquid temp",null,"°C");
     DoubleLabelValueUnit c07 = new DoubleLabelValueUnit("c07","Water inlet temp",null,"°C");
     DoubleLabelValueUnit c08 = new DoubleLabelValueUnit("c08","Water outlet temp",null,"°C");
     DoubleLabelValueUnit c09 = new DoubleLabelValueUnit("c09","DHW tank temperature",null,"°C");
     DoubleLabelValueUnit c10 = new DoubleLabelValueUnit("c10","Water flow",null,"l/min");
     DoubleLabelValueUnit c11 = new DoubleLabelValueUnit("c11","Main circulation temperature differential",null,"-30~97 °C");
     DoubleLabelValueUnit c12 = new DoubleLabelValueUnit("c12","EVI circulation temperature differential",null,"-30~97 °C");
     DoubleLabelValueUnit c13 = new DoubleLabelValueUnit("c13","High pressure",null,"MPa");
     DoubleLabelValueUnit c14 = new DoubleLabelValueUnit("c14","Low pressure",null,"MPa");
     DoubleLabelValueUnit c15 = new DoubleLabelValueUnit("c15","Compressor running frequency",null,"0~120 Hz");
     DoubleLabelValueUnit c16 = new DoubleLabelValueUnit("c16","Fan motor 1",null,"0-1500 RPM");
     DoubleLabelValueUnit c17 = new DoubleLabelValueUnit("c17","Fan motor 2",null,"0-1500 RPM");
     DoubleLabelValueUnit c18 = new DoubleLabelValueUnit("c18","EEV steps",null,"0-500");
     DoubleLabelValueUnit c19 = new DoubleLabelValueUnit("c19","EVI EEV steps",null,"0-500");
     DoubleLabelValueUnit c20 = new DoubleLabelValueUnit("c20","Compressor target frequency",null,"0-100 HZ");
     DoubleLabelValueUnit c21 = new DoubleLabelValueUnit("c21","Compressor input current",null,"0-50 A");
     DoubleLabelValueUnit c22 = new DoubleLabelValueUnit("c22","IPM temp",null,"°C");
     DoubleLabelValueUnit c23 = new DoubleLabelValueUnit("c23","AC power voltage",null,"0-500 V");
     DoubleLabelValueUnit c24 = new DoubleLabelValueUnit("c24","DC power voltage",null,"0-1000 V");
     DoubleLabelValueUnit c25 = new DoubleLabelValueUnit("c25","T6",null,"°C");
     DoubleLabelValueUnit c26 = new DoubleLabelValueUnit("c26","Room temp (T2)",null,"°C");
     DoubleLabelValueUnit c27 = new DoubleLabelValueUnit("c27","Evaporator temp",null,"°C");
     DoubleLabelValueUnit c28 = new DoubleLabelValueUnit("c28","Condenser temp",null,"°C");
     DoubleLabelValueUnit c29 = new DoubleLabelValueUnit("c29","Cooling switch",null,"ON/OFF");
     DoubleLabelValueUnit c30 = new DoubleLabelValueUnit("c30","Heating switch",null,"ON/OFF");
     DoubleLabelValueUnit c31 = new DoubleLabelValueUnit("c31","Sterilization status",null,"ON/OFF");
     DoubleLabelValueUnit c32 = new DoubleLabelValueUnit("c32","Compressor over-current switch status",null,"ON/OFF");
     DoubleLabelValueUnit c33 = new DoubleLabelValueUnit("c33","Defrost status",null,"ON/OFF");
     DoubleLabelValueUnit c34 = new DoubleLabelValueUnit("c34","AC antifreeze status",null,"ON/OFF");
     DoubleLabelValueUnit c35 = new DoubleLabelValueUnit("c35","DWH antifreeze status",null,"ON/OFF");
     DoubleLabelValueUnit c36 = new DoubleLabelValueUnit("c36","Compressor heater status",null,"ON/OFF");
     DoubleLabelValueUnit c37 = new DoubleLabelValueUnit("c37","4-way valve status",null,"ON/OFF");
     DoubleLabelValueUnit c38 = new DoubleLabelValueUnit("c38","G1 3-Way valve",null,"ON/OFF");
     DoubleLabelValueUnit c39 = new DoubleLabelValueUnit("c39","G2 3-Way valve",null,"ON/OFF");
     DoubleLabelValueUnit c40 = new DoubleLabelValueUnit("c40","E1 heater",null,"ON/OFF");
     DoubleLabelValueUnit c41 = new DoubleLabelValueUnit("c41","E2 heater",null,"ON/OFF");
     DoubleLabelValueUnit c42 = new DoubleLabelValueUnit("c42","C1 water pump",null,"ON/OFF");
     DoubleLabelValueUnit c43 = new DoubleLabelValueUnit("c43","C2 water pum",null,"ON/OFF");
     DoubleLabelValueUnit c44 = new DoubleLabelValueUnit("c44","C3 water pump",null,"ON/OFF");
     DoubleLabelValueUnit c45 = new DoubleLabelValueUnit("c45","Heat target temp",null,"°C");
     DoubleLabelValueUnit c46 = new DoubleLabelValueUnit("c46","Cooling target temp",null,"°C");
     DoubleLabelValueUnit c47 = new DoubleLabelValueUnit("c47","DWH target temp",null,"°C");
     DoubleLabelValueUnit c48 = new DoubleLabelValueUnit("c48","Sterilization target temp",null,"°C");
     DoubleLabelValueUnit c49 = new DoubleLabelValueUnit("c49","Return lubricant oil status",null,"ON/OFF");
     DoubleLabelValueUnit c50 = new DoubleLabelValueUnit("c50","Compressor total running time",null,"h");
     DoubleLabelValueUnit c51 = new DoubleLabelValueUnit("c51","C1 water pump speed",null,"0~100 %");
     DoubleLabelValueUnit c52 = new DoubleLabelValueUnit("c52","Running mode",null,"1-DHW\n" +
            "2-A/C Heating\n" +
            "4-A/C cooling\n" +
            "3-DHW+A/C Heating\n" +
            "5-DHW+A/C Cooling");
     DoubleLabelValueUnit c53 = new DoubleLabelValueUnit("c53","Target frequency",null,"0~120 Hz");
     DoubleLabelValueUnit c54 = new DoubleLabelValueUnit("c54","Heat pump mode",null,"1-DHW\n" +
            "2-A/C Heating\n" +
            "4-A/C cooling\n" +
            "3-DHW+A/C Heating\n" +
            "5-DHW+A/C Cooling");
     DoubleLabelValueUnit c55 = new DoubleLabelValueUnit("c55","PCB Software version",null,"");
     DoubleLabelValueUnit c56 = new DoubleLabelValueUnit("c56","HMI Software version",null,"");
    
    
    
    
    default DoubleLabelValueUnit getC00(){
        return c00;
    }

    default DoubleLabelValueUnit getC01(){
        return c01;
    }

    default DoubleLabelValueUnit getC02(){
        return c02;
    }

    default DoubleLabelValueUnit getC03(){
        return c03;
    }

    default DoubleLabelValueUnit getC04() {
        return c04;
    }

    default DoubleLabelValueUnit getC05() {
        return c05;
    }

    default DoubleLabelValueUnit getC06() {
        return c06;
    }

    default DoubleLabelValueUnit getC07() {
        return c07;
    }

    default DoubleLabelValueUnit getC08() {
        return c08;
    }

    default DoubleLabelValueUnit getC09() {
        return c09;
    }

    default DoubleLabelValueUnit getC10() {
        return c10;
    }

    default DoubleLabelValueUnit getC11() {
        return c11;
    }

    default DoubleLabelValueUnit getC12() {
        return c12;
    }

    default DoubleLabelValueUnit getC13() {
        return c13;
    }

    default DoubleLabelValueUnit getC14() {
        return  c14;
    }

    default DoubleLabelValueUnit getC15() {
        return c15;
    }

    default DoubleLabelValueUnit getC16() {
        return c16;
    }

    default DoubleLabelValueUnit getC17() {
        return c17;
    }

    default DoubleLabelValueUnit getC18() {
        return c18;
    }

    default DoubleLabelValueUnit getC19() {
        return c19;
    }

    default DoubleLabelValueUnit getC20() {
        return c20;
    }

    default DoubleLabelValueUnit getC21() {
        return c21;
    }

    default DoubleLabelValueUnit getC22() {
        return c22;
    }

    default DoubleLabelValueUnit getC23() {
        return c23;
    }

    default DoubleLabelValueUnit getC24() {
        return c24;
    }

    default DoubleLabelValueUnit getC25() {
        return c25;
    }

    default DoubleLabelValueUnit getC26() {
        return c26;
    }

    default DoubleLabelValueUnit getC27() {
        return c27;
    }

    default DoubleLabelValueUnit getC28() {
        return  c28;
    }

    default DoubleLabelValueUnit getC29() {
        return c29;
    }

    default DoubleLabelValueUnit getC30() {
        return c30;
    }

    // next byte array
    default DoubleLabelValueUnit getC31() {
        return c31;
    }

    default DoubleLabelValueUnit getC32() {
        return c32;
    }

    default DoubleLabelValueUnit getC33() {
        return c33;
    }

    default DoubleLabelValueUnit getC34() {
        return c34;
    }

    default DoubleLabelValueUnit getC35() {
        return c35;
    }

    default DoubleLabelValueUnit getC36() {
        return c36;
    }

    default DoubleLabelValueUnit getC37() {
        return c37;
    }

    default DoubleLabelValueUnit getC38() {
        return c38;
    }

    default DoubleLabelValueUnit getC39() {
        return c39;
    }

    default DoubleLabelValueUnit getC40() {
        return c40;
    }

    default DoubleLabelValueUnit getC41() {
        return c41;
    }

    default DoubleLabelValueUnit getC42() {
        return c42;
    }

    default DoubleLabelValueUnit getC43() {
        return c43;
    }

    default DoubleLabelValueUnit getC44() {
        return c44;
    }

    default DoubleLabelValueUnit getC45() {
        return c45;
    }

    default DoubleLabelValueUnit getC46() {
        return c46;
    }

    default DoubleLabelValueUnit getC47() {
        return c47;
    }

    default DoubleLabelValueUnit getC48() {
        return c48;
    }

    default DoubleLabelValueUnit getC49() {
        return c49;
    }

    default DoubleLabelValueUnit getC50() {
        return c50;
    }

    default DoubleLabelValueUnit getC51() {
        return c51;
    }

    default DoubleLabelValueUnit getC52() {
        return c52;
    }

    default DoubleLabelValueUnit getC53() {
        return c53;
    }

    default DoubleLabelValueUnit getC54() {
        return c54;
    }

    default DoubleLabelValueUnit getC55() {
        return c55;
    }

    default DoubleLabelValueUnit getC56() {
        return c56;
    }
}
