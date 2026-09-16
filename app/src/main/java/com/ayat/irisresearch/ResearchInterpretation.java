package com.ayat.irisresearch;
import java.util.*;
public final class ResearchInterpretation {
    private ResearchInterpretation(){}
    public static String relationship(int sun,int moon,int venus){
        return "شاخص پژوهشی رابطه: خورشید در خانه/نشانه "+sun+"، ماه "+moon+"، زهره "+venus+
               ". این بخش الگوهای سنتی را گزارش می‌کند و پیش‌بینی قطعی رابطه نیست.";
    }
    public static String career(int sun,int mars,int jupiter){
        return "محور پژوهشی شغل بر پایه جایگاه‌های محاسبه‌شده: خورشید "+sun+
               "، مریخ "+mars+"، مشتری "+jupiter+". برای مقایسه سناریوهای شغلی استفاده شود.";
    }
    public static String health(int sun,int moon,int saturn){
        return "بخش سلامت پژوهشی: نشانگرهای نمادین خورشید "+sun+"، ماه "+moon+" و زحل "+saturn+
               ". این خروجی تشخیص پزشکی یا پیش‌بینی بیماری نیست.";
    }
    public static String investment(int venus,int jupiter,int sun){
        return "بخش مالی پژوهشی: زهره "+venus+"، مشتری "+jupiter+" و خورشید "+sun+
               ". این داده‌ها برای تحلیل سنتی/پژوهشی‌اند و تضمین بازده سرمایه‌گذاری نیستند.";
    }
}
