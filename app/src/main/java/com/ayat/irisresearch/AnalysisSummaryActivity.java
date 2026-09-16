package com.ayat.irisresearch;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Map;

public class AnalysisSummaryActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24,24,24,24);

        TextView title = new TextView(this);
        title.setText("گزارش جامع تحلیل پژوهشی");
        title.setTextSize(24);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(title, new LinearLayout.LayoutParams(-1,-2));

        BirthData b = new BirthData();
        b.name = getIntent().getStringExtra("name");
        int y = getIntent().getIntExtra("year", 1400);
        int m = getIntent().getIntExtra("month", 1);
        int d = getIntent().getIntExtra("day", 1);
        int life = NumerologyEngine.lifePath(y,m,d);
        int name = NumerologyEngine.nameValue(b.name);

        Map<String,String> report = ResearchReportEngine.buildReport(b, life, name);
        for (Map.Entry<String,String> e : report.entrySet()) {
            TextView tv = new TextView(this);
            tv.setText(e.getKey() + "\n" + e.getValue());
            tv.setTextSize(17);
            tv.setPadding(0,18,0,18);
            root.addView(tv, new LinearLayout.LayoutParams(-1,-2));
        }
        setContentView(root);
    }
}
