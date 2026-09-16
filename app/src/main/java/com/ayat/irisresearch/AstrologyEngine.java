package com.ayat.irisresearch;

import java.time.*;
import java.util.*;

public final class AstrologyEngine {
    private AstrologyEngine(){}

    public static final String[] PLANETS = {"☉ خورشید","☽ ماه","☿ عطارد","♀ زهره","♂ مریخ","♃ مشتری","♄ زحل","♅ اورانوس","♆ نپتون","♇ پلوتو"};
    private static final double[] BASE = {280.147,218.316,252.251,181.979,355.433,34.351,50.077,314.055,304.348,238.929};
    private static final double[] SPEED = {0.985647,13.176396,1.0,1.60213,0.52402,0.08309,0.03346,0.01173,0.00598,0.00396};

    public static List<Double> approximateLongitudes(LocalDateTime dt){
        long days=Duration.between(LocalDateTime.of(2000,1,1,12,0),dt).toDays();
        List<Double> out=new ArrayList<>();
        for(int i=0;i<BASE.length;i++) out.add(norm(BASE[i]+SPEED[i]*days));
        return out;
    }
    public static double norm(double x){ x%=360; return x<0?x+360:x; }
    public static int sign(double longitude){ return (int)(norm(longitude)/30); }
    public static String signName(double longitude){
        String[] s={"حمل","ثور","جوزا","سرطان","اسد","سنبله","میزان","عقرب","قوس","جدی","دلو","حوت"};
        return s[sign(longitude)];
    }
    public static String house(double longitude,double asc){
        double rel=norm(longitude-asc);
        return String.valueOf((int)(rel/30)+1);
    }
    public static List<String> aspects(List<Double> p){
        List<String> a=new ArrayList<>();
        for(int i=0;i<p.size();i++) for(int j=i+1;j<p.size();j++){
            double d=Math.abs(p.get(i)-p.get(j)); d=Math.min(d,360-d);
            if(d<=8) a.add(PLANETS[i]+" ↔ "+PLANETS[j]+" هم‌نشینی ("+Math.round(d)+"°)");
            else if(Math.abs(d-60)<=5) a.add(PLANETS[i]+" ↔ "+PLANETS[j]+" تسدیس");
            else if(Math.abs(d-90)<=6) a.add(PLANETS[i]+" ↔ "+PLANETS[j]+" تربیع");
            else if(Math.abs(d-120)<=6) a.add(PLANETS[i]+" ↔ "+PLANETS[j]+" تثلیث");
            else if(Math.abs(d-180)<=8) a.add(PLANETS[i]+" ↔ "+PLANETS[j]+" مقابله");
        }
        return a;
    }
}
