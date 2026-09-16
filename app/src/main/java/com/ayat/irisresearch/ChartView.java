package com.ayat.irisresearch;

import android.content.Context;
import android.graphics.*;
import android.view.View;
import java.util.List;

public class ChartView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final String[] signs = {"حمل","ثور","جوزا","سرطان","اسد","سنبله","میزان","عقرب","قوس","جدی","دلو","حوت"};
    private List<Double> longitudes;
    public ChartView(Context c) { super(c); paint.setTypeface(Typeface.create("sans",Typeface.NORMAL)); }
    public void setLongitudes(List<Double> v){ longitudes=v; invalidate(); }

    @Override protected void onDraw(Canvas c) {
        super.onDraw(c);
        float cx=getWidth()/2f, cy=getHeight()/2f, r=Math.min(cx,cy)-28;
        paint.setStyle(Paint.Style.STROKE); paint.setStrokeWidth(3); paint.setColor(Color.LTGRAY);
        c.drawCircle(cx,cy,r,paint); c.drawCircle(cx,cy,r*.68f,paint);
        paint.setStrokeWidth(1);
        for(int i=0;i<12;i++){
            double a=Math.toRadians(i*30-90);
            c.drawLine(cx+(float)Math.cos(a)*r,cy+(float)Math.sin(a)*r,
                       cx+(float)Math.cos(a)*r*.68f,cy+(float)Math.sin(a)*r*.68f,paint);
            paint.setStyle(Paint.Style.FILL); paint.setTextSize(14);
            float tx=cx+(float)Math.cos(a+Math.toRadians(15))*(r*.84f);
            float ty=cy+(float)Math.sin(a+Math.toRadians(15))*(r*.84f);
            c.drawText(signs[i],tx-14,ty,paint); paint.setStyle(Paint.Style.STROKE);
        }
        if(longitudes!=null){
            paint.setStyle(Paint.Style.FILL); paint.setTextSize(12);
            for(int i=0;i<longitudes.size();i++){
                double a=Math.toRadians(longitudes.get(i)-90);
                float x=cx+(float)Math.cos(a)*r*.54f;
                float y=cy+(float)Math.sin(a)*r*.54f;
                c.drawCircle(x,y,5,paint);
                c.drawText("P"+(i+1),x+7,y,paint);
            }
        }
    }
}
