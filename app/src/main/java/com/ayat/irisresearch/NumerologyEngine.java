package com.ayat.irisresearch;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class NumerologyEngine {
    private static final Map<Character, Integer> ABJAD = new HashMap<>();
    static {
        String letters = "ابجدوهزحطی";
        int[] values = {1,2,3,4,5,6,7,8,9,10};
        for (int i=0;i<letters.length();i++) ABJAD.put(letters.charAt(i), values[i]);
        String[] groups = {"ک","ل","م","ن","س","ع","ف","ص","ق","ر","ش","ت","ث","خ","ذ","ض","ظ","غ"};
        int[] vals = {20,30,40,50,60,70,80,90,100,200,300,400,500,600,700,800,900,1000};
        for (int i=0;i<groups.length;i++) ABJAD.put(groups[i].charAt(0), vals[i]);
    }

    public static int lifePath(int y, int m, int d) {
        int n = reduce(Math.abs(y)) + reduce(m) + reduce(d);
        return reduce(n);
    }

    public static int nameValue(String name) {
        if (name == null) return 0;
        String s = Normalizer.normalize(name, Normalizer.Form.NFC);
        int sum = 0;
        for (char c : s.toCharArray()) {
            Integer v = ABJAD.get(c);
            if (v != null) sum += v;
        }
        return reduce(sum);
    }

    public static int reduce(int n) {
        n = Math.abs(n);
        while (n > 9 && n != 11 && n != 22 && n != 33) {
            int s = 0;
            while (n > 0) { s += n % 10; n /= 10; }
            n = s;
        }
        return n;
    }

    public static String meaning(int n) {
        switch (n) {
            case 1: return "استقلال، آغازگری و رهبری";
            case 2: return "همکاری، دیپلماسی و تعادل";
            case 3: return "بیان، خلاقیت و ارتباط";
            case 4: return "نظم، ساختار و پشتکار";
            case 5: return "آزادی، تجربه و تغییر";
            case 6: return "مسئولیت، خانواده و مراقبت";
            case 7: return "پژوهش، درون‌نگری و تحلیل";
            case 8: return "مدیریت، هدف‌گذاری و منابع";
            case 9: return "همدلی، آرمان‌گرایی و جمع‌بندی";
            case 11: return "شهود و الهام؛ عدد استاد";
            case 22: return "ساختن و اجرای پروژه‌های بزرگ؛ عدد استاد";
            case 33: return "خدمت و آموزش؛ عدد استاد";
            default: return "الگوی عددی نیازمند داده بیشتر است";
        }
    }
}
