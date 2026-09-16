package com.ayat.irisresearch;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResearchReportEngine {
    public static Map<String,String> buildReport(BirthData b, int lifePath, int nameNumber) {
        Map<String,String> r = new LinkedHashMap<>();
        r.put("خلاصه", "این گزارش یک تحلیل پژوهشی و تفسیری بر پایه داده‌های تولد و مدل‌های نمادین است؛ نتیجه قطعی یا تشخیص پزشکی/مالی نیست.");
        r.put("عددشناسی", "عدد مسیر زندگی: " + lifePath + " — " + NumerologyEngine.meaning(lifePath) +
                "\\nعدد نام: " + nameNumber + " — " + NumerologyEngine.meaning(nameNumber));
        r.put("روابط", "برای بررسی رابطه باید داده‌های هر دو نفر، زمان و مکان تولد و سپس هم‌پوشانی شاخص‌های نموداری مقایسه شود.");
        r.put("شغل", "شاخص‌های شغلی می‌توانند برای بررسی علایق، الگوهای تصمیم‌گیری و سبک کاری به‌صورت پژوهشی استفاده شوند؛ تصمیم شغلی باید با مهارت و شرایط واقعی فرد ترکیب شود.");
        r.put("سلامت پژوهشی", "این بخش صرفاً موضوعات نمادین/پژوهشی را فهرست می‌کند و جایگزین تشخیص، درمان یا توصیه پزشک نیست.");
        r.put("سرمایه‌گذاری پژوهشی", "این بخش می‌تواند الگوهای زمانی و ویژگی‌های تصمیم‌گیری را برای مطالعه ثبت کند؛ نباید به‌عنوان پیش‌بینی سود یا توصیه خرید/فروش استفاده شود.");
        return r;
    }
}
