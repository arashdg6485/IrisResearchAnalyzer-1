package com.ayat.irisresearch;
import java.util.*;
public final class VedicEngine {
    private VedicEngine(){}
    private static final String[] RASHI={"میشا","وریشابها","میتونا","کارکا","سیمها","کانیا","تولا","ویریشچیکا","دهنو","ماکارا","کومبها","مینا"};
    public static String rashi(double siderealLongitude){ return RASHI[(int)(AstrologyEngine.norm(siderealLongitude)/30)]; }
    public static String nakshatra(double lon){
        String[] n={"Ashwini","Bharani","Krittika","Rohini","Mrigashira","Ardra","Punarvasu","Pushya","Ashlesha",
        "Magha","Purva Phalguni","Uttara Phalguni","Hasta","Chitra","Swati","Vishakha","Anuradha","Jyeshtha",
        "Mula","Purva Ashadha","Uttara Ashadha","Shravana","Dhanishta","Shatabhisha","Purva Bhadrapada","Uttara Bhadrapada","Revati"};
        return n[(int)(AstrologyEngine.norm(lon)/ (360.0/27.0))];
    }
    public static double sidereal(double tropical){ return AstrologyEngine.norm(tropical-24.0); }
}
