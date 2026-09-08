package com.ayat.irisresearch;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.*;
import android.widget.*;
import java.util.*;

/**
 * Iris Research Analyzer
 * Educational/research visualization only.
 * Traditional zone labels are based on Bernard Jensen's published iridology chart.
 * The app reports image features in a zone; it does NOT infer disease.
 */
public class MainActivity extends Activity {
    static final int PICK=10, CAM=11, REQ=20;
    ImageView imageView; TextView result; Bitmap bitmap; IrisView irisView;
    int bg=Color.rgb(16,21,27), panel=Color.rgb(24,34,43), accent=Color.rgb(93,214,192);
    boolean rightEye=true, showZones=true;

    @Override public void onCreate(Bundle b){super.onCreate(b); buildUI();}
    TextView tv(String s,int sp){TextView t=new TextView(this);t.setText(s);t.setTextColor(Color.WHITE);t.setTextSize(sp);t.setPadding(20,10,20,10);return t;}
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setTextColor(Color.WHITE);b.setAllCaps(false);return b;}
    void buildUI(){
        LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(bg);
        TextView title=tv("🔬  Iris Research Analyzer — Jensen Map",21);title.setTextColor(accent);title.setTypeface(null,1);root.addView(title);
        TextView sub=tv("تحلیل آموزشی و پژوهشی عنبیه بر اساس نقشه سنتی Bernard Jensen — غیرتشخیصی",13);sub.setTextColor(Color.LTGRAY);root.addView(sub);
        LinearLayout bar=new LinearLayout(this);bar.setGravity(Gravity.CENTER);
        Button gallery=btn("🖼 انتخاب تصویر"),camera=btn("📷 دوربین"),clear=btn("پاک‌کردن");
        bar.addView(gallery,new LinearLayout.LayoutParams(0,-2,1));bar.addView(camera,new LinearLayout.LayoutParams(0,-2,1));bar.addView(clear,new LinearLayout.LayoutParams(0,-2,1));root.addView(bar);
        LinearLayout eyeBar=new LinearLayout(this);eyeBar.setGravity(Gravity.CENTER);
        Button right=btn("چشم راست"),left=btn("چشم چپ"),zones=btn("نقشه: روشن");
        eyeBar.addView(right,new LinearLayout.LayoutParams(0,-2,1));eyeBar.addView(left,new LinearLayout.LayoutParams(0,-2,1));eyeBar.addView(zones,new LinearLayout.LayoutParams(0,-2,1));root.addView(eyeBar);
        irisView=new IrisView(this);root.addView(irisView,new LinearLayout.LayoutParams(-1,0,1));
        result=tv("یک تصویر واضح از عنبیه انتخاب کنید.\n\nپس از تحلیل، تغییرات رنگ و بافت هر ناحیه با نام ناحیه سنتی Jensen نمایش داده می‌شود؛ مثلاً «ناحیه منتسب به کبد: تغییر رنگ/بافت تصویری». این عبارت به معنی وجود بیماری نیست.",14);result.setBackgroundColor(panel);root.addView(result,new LinearLayout.LayoutParams(-1,230));
        Button refs=btn("📚 مراجع فارسی Jensen");root.addView(refs,new LinearLayout.LayoutParams(-1,-2));
        Button share=btn("📄 اشتراک گزارش");root.addView(share,new LinearLayout.LayoutParams(-1,-2));
        setContentView(root);
        gallery.setOnClickListener(v->pick());camera.setOnClickListener(v->cam());
        clear.setOnClickListener(v->{bitmap=null;irisView.setBitmap(null);result.setText("تصویر پاک شد.\nیک تصویر واضح از عنبیه انتخاب کنید.");});
        right.setOnClickListener(v->{rightEye=true;if(bitmap!=null)analyze();irisView.invalidate();});
        left.setOnClickListener(v->{rightEye=false;if(bitmap!=null)analyze();irisView.invalidate();});
        zones.setOnClickListener(v->{showZones=!showZones;zones.setText(showZones?"نقشه: روشن":"نقشه: خاموش");irisView.invalidate();});
        refs.setOnClickListener(v->showReferences());
        share.setOnClickListener(v->shareReport());
    }
    void pick(){startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),PICK);}
    void cam(){if(checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){requestPermissions(new String[]{Manifest.permission.CAMERA},REQ);return;}startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),CAM);}
    @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(c!=RESULT_OK||d==null)return;try{if(r==PICK){Uri u=d.getData();bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),u);}else bitmap=(Bitmap)d.getExtras().get("data");analyze();}catch(Exception e){result.setText("خطا در خواندن تصویر: "+e.getMessage());}}
    void analyze(){if(bitmap==null)return;irisView.setBitmap(bitmap);Analysis a=Analysis.run(bitmap);result.setText(a.report(rightEye));}

    void showReferences(){
        final String text = "مراجع فارسی — Bernard Jensen\n\n"+
                "۱) علم و عمل عنبیه‌شناسی (The Science and Practice of Iridology)\n"+
                "نویسنده: Bernard Jensen. اثر اصلی او در زمینه عنبیه‌شناسی است و نسخه‌های فهرست‌شده آن شامل نمودار عنبیه و نواحی اندام‌ها هستند.\n\n"+
                "۲) Iridology: Science and Practice in the Healing Arts\n"+
                "اثر دیگری از Bernard Jensen که در منابع کتاب‌شناختی با عنوان ویرایش ۱۹۸۲ و ۵۸۰ صفحه ثبت شده است.\n\n"+
                "۳) Iridology Simplified\n"+
                "در این برنامه، نقشه نواحی از نمودار منتشرشده Jensen به‌عنوان مرجع تاریخی/آموزشی استفاده می‌شود. نام‌هایی مانند کبد، کلیه، ریه، طحال، تیروئید، معده، پانکراس، قلب، روده و سایر نواحی در نمودار دیده می‌شوند.\n\n"+
                "نحوه استفاده در برنامه:\n"+
                "• تصویر عنبیه از نظر رنگ، روشنایی و تنوع بافت به‌صورت ناحیه‌ای بررسی می‌شود.\n"+
                "• اگر یک ناحیه با میانگین تصویر تفاوت داشته باشد، همان ناحیه روی نقشه برجسته می‌شود.\n"+
                "• نام عضو فقط «ناحیه منتسب در نقشه Jensen» است.\n"+
                "• برنامه نمی‌گوید آن عضو بیمار است و برای آینده بیماری پیش‌بینی قطعی ارائه نمی‌کند.\n\n"+
                "⚠️ هشدار علمی: محتوای Jensen در این برنامه به‌عنوان مرجع تاریخی و آموزشی عنبیه‌شناسی استفاده شده است. شواهد علمی معتبر، عنبیه‌شناسی را ابزار قابل اتکای تشخیص یا غربالگری بیماری‌های اندام‌های داخلی نمی‌دانند. برای تشخیص واقعی باید از معاینه، آزمایش و تصویربرداری پزشکی معتبر استفاده شود.";
        new AlertDialog.Builder(this).setTitle("📚 مراجع فارسی Jensen").setMessage(text).setPositiveButton("باشه",null).show();
    }
    void shareReport(){String s=result.getText().toString();Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,s);startActivity(Intent.createChooser(i,"اشتراک گزارش"));}

    class IrisView extends View{
        Paint p=new Paint(3);Bitmap bm;Analysis a;
        IrisView(android.content.Context c){super(c);p.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));}
        void setBitmap(Bitmap b){bm=b;a=b==null?null:Analysis.run(b);invalidate();}
        protected void onDraw(Canvas c){
            super.onDraw(c);c.drawColor(Color.rgb(9,12,15));if(bm==null)return;
            float sc=Math.min(getWidth()/(float)bm.getWidth(),getHeight()/(float)bm.getHeight());float w=bm.getWidth()*sc,h=bm.getHeight()*sc;float x=(getWidth()-w)/2,y=(getHeight()-h)/2;
            c.drawBitmap(bm,null,new RectF(x,y,x+w,y+h),p);
            if(a==null)return;float cx=x+a.cx*sc,cy=y+a.cy*sc;
            p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(3);p.setColor(accent);c.drawCircle(cx,cy,a.irisR*sc,p);p.setColor(Color.YELLOW);p.setStrokeWidth(2);c.drawCircle(cx,cy,a.pupilR*sc,p);
            if(showZones){drawJensenZones(c,cx,cy,a.irisR*sc);}
            p.setStyle(Paint.Style.FILL);p.setColor(Color.WHITE);p.setTextSize(18);c.drawText(rightEye?"RIGHT / چشم راست":"LEFT / چشم چپ",20,28,p);
            p.setTextSize(14);p.setColor(Color.YELLOW);c.drawText("نقاط نارنجی = تغییر رنگ/بافت تصویری نسبت به میانگین شعاعی",20,getHeight()-18,p);
        }
        void drawJensenZones(Canvas c,float cx,float cy,float R){
            p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setColor(Color.argb(150,255,255,255));
            for(int i=0;i<12;i++){double ang=Math.toRadians(i*30-90);float x=(float)(cx+R*Math.cos(ang)),y=(float)(cy+R*Math.sin(ang));c.drawLine(cx,cy,x,y,p);}
            // Highlight sectors with detected visual deviations.
            if(a!=null)for(ZoneResult z:a.zones){if(z.score>0.24){double a0=Math.toRadians(z.startDeg-90),a1=Math.toRadians(z.endDeg-90);Path path=new Path();path.moveTo(cx,cy);path.lineTo((float)(cx+R*Math.cos(a0)),(float)(cy+R*Math.sin(a0)));path.arcTo(new RectF(cx-R,cy-R,cx+R,cy+R),z.startDeg-90,30,false);path.close();p.setStyle(Paint.Style.FILL);p.setColor(Color.argb(85,255,150,0));c.drawPath(path,p);}}
            p.setStyle(Paint.Style.FILL);p.setTextSize(12);p.setColor(Color.WHITE);
            for(int i=0;i<12;i++){double ang=Math.toRadians(i*30-75);float tx=(float)(cx+R*0.72*Math.cos(ang)),ty=(float)(cy+R*0.72*Math.sin(ang));c.drawText(""+(i+1),tx,ty,p);}
        }
    }

    static class ZoneResult{String name;double score;int startDeg;ZoneResult(String n,double s,int d){name=n;score=s;startDeg=d;}}
    static class Analysis{
        float cx,cy,pupilR,irisR;String color="نامشخص",texture="نامشخص";double variance;
        ArrayList<ZoneResult> zones=new ArrayList<>();
        // Traditional Jensen-style clock sectors. Labels intentionally describe association, not disease.
        static final String[] RIGHT={"مغز/حسّی","صورت","ریه","جنب/قفسه سینه","کبد","تناسلی","کلیه","سیستم سمپاتیک/گردش خون","تیروئید","ناحیه فوقانی گوارش","مغز/عصبی","ناحیه سر"};
        static final String[] LEFT ={"مغز/حسّی","صورت","ریه","جنب/قفسه سینه","طحال","تناسلی","کلیه","لنفاتیک/گردش خون","تیروئید","ناحیه فوقانی گوارش","مغز/عصبی","ناحیه سر"};
        static Analysis run(Bitmap b){
            Analysis a=new Analysis();int W=b.getWidth(),H=b.getHeight();int step=Math.max(1,Math.min(W,H)/300);double best=1e18;int bx=W/2,by=H/2;
            for(int y=H/5;y<4*H/5;y+=step)for(int x=W/5;x<4*W/5;x+=step){int col=b.getPixel(x,y);double v=(Color.red(col)+Color.green(col)+Color.blue(col))/3.0;if(v<best){best=v;bx=x;by=y;}}
            a.cx=bx;a.cy=by;int maxR=Math.min(W,H)/4;
            for(int r=5;r<maxR;r+=Math.max(1,step*2)){int dark=0,total=0;for(int k=0;k<360;k+=6){double q=Math.toRadians(k);int x=(int)(bx+r*Math.cos(q)),y=(int)(by+r*Math.sin(q));if(x<0||y<0||x>=W||y>=H)continue;int c=b.getPixel(x,y);double g=(Color.red(c)+Color.green(c)+Color.blue(c))/3.0;if(g<65)dark++;total++;}if(total>0&&dark/(double)total<0.25){a.pupilR=r;break;}}
            if(a.pupilR<5)a.pupilR=Math.min(W,H)*.08f;a.irisR=Math.min(Math.min(W,H)*.42f,a.pupilR*2.35f);
            int rr0=(int)(a.pupilR*1.15),rr1=(int)(a.irisR*.92);ArrayList<Double> vals=new ArrayList<>();long sr=0,sg=0,sb=0;int n=0;
            for(int r=rr0;r<rr1;r+=Math.max(2,step*2))for(int k=0;k<360;k+=8){double q=Math.toRadians(k);int x=(int)(bx+r*Math.cos(q)),y=(int)(by+r*Math.sin(q));if(x<0||y<0||x>=W||y>=H)continue;int cc=b.getPixel(x,y);int R=Color.red(cc),G=Color.green(cc),B=Color.blue(cc);sr+=R;sg+=G;sb+=B;n++;vals.add((R+G+B)/3.0);}
            if(n>0){double R=sr/(3.0*n),G=sg/(3.0*n),B=sb/(3.0*n);if(R>G*1.35&&R>B*1.5)a.color="قهوه‌ای/عنابی";else if(R>G*1.12&&G>B*1.12)a.color="فندقی";else if(B>R*1.08&&B>G*1.03)a.color="آبی/خاکستری";else if(G>R*1.08&&G>B*1.05)a.color="سبز";else a.color="ترکیبی/نامشخص";}
            double mean=0;for(double v:vals)mean+=v;mean/=Math.max(1,vals.size());for(double v:vals)a.variance+=(v-mean)*(v-mean);a.variance/=Math.max(1,vals.size());a.texture=a.variance>800?"بافت متنوع":a.variance>300?"بافت متوسط":"بافت یکنواخت‌تر";
            // Estimate sector-by-sector color/texture deviation from the iris-wide mean.
            double[] secMean=new double[12];int[] secN=new int[12];double global=mean;
            for(int k=0;k<360;k+=2){int sec=((k+15)/30)%12;double q=Math.toRadians(k);for(int r=rr0;r<rr1;r+=Math.max(3,step*3)){int x=(int)(bx+r*Math.cos(q)),y=(int)(by+r*Math.sin(q));if(x<0||y<0||x>=W||y>=H)continue;int cc=b.getPixel(x,y);double lum=(Color.red(cc)+Color.green(cc)+Color.blue(cc))/3.0;secMean[sec]+=lum;secN[sec]++;}}
            for(int i=0;i<12;i++){double m=secN[i]>0?secMean[i]/secN[i]:global;double score=Math.min(1.0,Math.abs(m-global)/Math.max(18,global)*1.8);a.zones.add(new ZoneResult("",score,i*30));}
            return a;
        }
        String jensenNote(String n){
            if(n.contains("کبد")) return "در نقشه Jensen این بخش به کبد نسبت داده می‌شود؛ تغییر رنگ/بافت صرفاً یک یافته تصویری است. در این برنامه از آن برای تشخیص بیماری یا پیش‌بینی آینده استفاده نمی‌شود.";
            if(n.contains("طحال")) return "در نقشه Jensen این بخش به طحال نسبت داده می‌شود؛ یافته تصویری را می‌توان برای مطالعه تطبیقی ثبت کرد.";
            if(n.contains("کلیه")) return "در نقشه Jensen این بخش به کلیه نسبت داده می‌شود؛ این نتیجه نشان‌دهنده بیماری کلیه نیست.";
            if(n.contains("ریه")) return "در نقشه Jensen این بخش به ریه نسبت داده می‌شود؛ تغییر مشاهده‌شده فقط ویژگی تصویر است.";
            if(n.contains("تیروئید")) return "در نقشه Jensen این بخش به تیروئید نسبت داده می‌شود؛ تشخیص عملکرد تیروئید با آزمایش و ارزیابی پزشکی انجام می‌شود، نه از این تصویر.";
            if(n.contains("گردش")) return "این ناحیه در نمودار Jensen با سیستم گردش خون/لنفاتیک مرتبط شده است؛ ارتباط مزبور در این برنامه صرفاً مرجع تاریخی است.";
            if(n.contains("گوارش")) return "این ناحیه به بخش‌هایی از دستگاه گوارش در نمودار سنتی مربوط شده است؛ تغییر تصویری به‌تنهایی بیماری گوارشی را ثابت نمی‌کند.";
            if(n.contains("تناسلی")) return "در نمودار Jensen این ناحیه با بخش‌های تناسلی مرتبط شده است؛ نتیجه تصویری، تشخیص پزشکی نیست.";
            if(n.contains("صورت")) return "در نمودار Jensen با ناحیه صورت مرتبط شده است؛ فقط تغییرات تصویری ثبت می‌شوند.";
            if(n.contains("جنب")) return "در نمودار Jensen با جنب/قفسه سینه مرتبط شده است؛ این فقط برچسب نقشه آموزشی است.";
            if(n.contains("مغز")) return "در نمودار Jensen با نواحی مغزی مرتبط شده است؛ این برنامه ادعای تشخیص عصبی ندارد.";
            if(n.contains("سر")) return "این عنوان از تقسیم‌بندی آموزشی نمودار Jensen گرفته شده است و معنای تشخیصی ندارد.";
            return "برچسب این ناحیه از نقشه سنتی Jensen گرفته شده و نتیجه فقط تصویری است.";
        }

        String report(boolean rightEye){
            String[] names=rightEye?RIGHT:LEFT;StringBuilder s=new StringBuilder();s.append("نتیجه تحلیل تصویری — ").append(rightEye?"چشم راست":"چشم چپ").append("\n\n");
            s.append("رنگ غالب عنبیه: ").append(color).append("\n");
            s.append("قطر تقریبی مردمک: ").append(String.format(Locale.US,"%.1f",pupilR*2)).append(" px\n");
            s.append("قطر تقریبی ناحیه عنبیه: ").append(String.format(Locale.US,"%.1f",irisR*2)).append(" px\n");
            s.append("الگوی کلی بافت: ").append(texture).append("\n");
            s.append("شاخص تنوع بافت: ").append(String.format(Locale.US,"%.1f",variance)).append("\n\n");
            s.append("ناحیه‌های دارای تغییر رنگ/بافت تصویری:\n");
            boolean any=false;for(int i=0;i<12;i++){ZoneResult z=zones.get(i);if(z.score>0.24){any=true;String n=names[i];s.append("• ساعت ").append(i==0?12:i).append(": ناحیه ").append(n).append(" — تغییر تصویری ").append(String.format(Locale.US,"%.0f%%",z.score*100)).append("\n");s.append("  مرجع سنتی Jensen: ").append(jensenNote(n)).append("\n");s.append("  پیامد پزشکی قطعی: قابل تعیین از روی عنبیه نیست.\n\n");}}
            if(!any)s.append("• تغییر برجسته‌ای نسبت به میانگین کل تصویر در این تحلیل ساده دیده نشد.\n");
            s.append("\nمنبع نقشه: نمودار عنبیه‌شناسی Bernard Jensen؛ در نمودار راست، کبد در یکی از بخش‌های محیطی حوالی ساعت 8 مشخص شده و در چشم چپ، ناحیه متناظر متفاوت است. این برنامه فقط همان ناحیه را از نظر تصویر علامت می‌زند.\n\n");
            s.append("⚠️ مهم: «تغییر در ناحیه منتسب به کبد» به هیچ‌وجه به معنی مشکل یا بیماری کبد نیست. برنامه فقط تغییر رنگ/بافت قابل مشاهده در عکس را گزارش می‌کند. عنبیه‌شناسی به‌عنوان روش تشخیص پزشکی معتبر اثبات نشده است.");
            return s.toString();
        }
    }
}
