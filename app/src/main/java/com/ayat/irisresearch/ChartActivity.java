package com.ayat.irisresearch;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;
import java.time.LocalDateTime;
import java.util.*;

public class ChartActivity extends Activity {
    @Override public void onCreate(Bundle b){
        super.onCreate(b);
        LinearLayout box=new LinearLayout(this); box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(18,18,18,18); box.setBackgroundColor(Color.rgb(11,16,32));
        TextView title=new TextView(this); title.setText("نمودار تولد و موتورهای تحلیل"); title.setTextColor(Color.WHITE);
        title.setTextSize(22); title.setGravity(Gravity.CENTER); box.addView(title);
        ChartView chart=new ChartView(this); box.addView(chart,new LinearLayout.LayoutParams(-1,650));
        TextView report=new TextView(this); report.setTextColor(Color.WHITE); report.setTextSize(15); report.setPadding(8,12,8,8);
        String date=getIntent().getStringExtra("date");
        String time=getIntent().getStringExtra("time");
        try{
            LocalDateTime dt=LocalDateTime.parse(date+"T"+time);
            List<Double> p=AstrologyEngine.approximateLongitudes(dt);
            chart.setLongitudes(p);
            StringBuilder s=new StringBuilder();
            for(int i=0;i<p.size();i++)
                s.append(AstrologyEngine.PLANETS[i]).append(": ").append(Math.round(p.get(i)*100)/100.0)
                 .append("° — ").append(AstrologyEngine.signName(p.get(i))).append("\\n");
            s.append("\\nجنبه‌ها:\\n");
            for(String a:AstrologyEngine.aspects(p)) s.append(a).append("\\n");
            double moonSid=VedicEngine.sidereal(p.get(1));
            s.append("\\nودیک — راشی ماه: ").append(VedicEngine.rashi(moonSid))
             .append("\\nناکشترا: ").append(VedicEngine.nakshatra(moonSid));
            report.setText(s.toString());
        }catch(Exception e){ report.setText("خطا در محاسبه: "+e.getMessage()); }
        ScrollView sv=new ScrollView(this); sv.addView(report); box.addView(sv,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(box);
    }
}
