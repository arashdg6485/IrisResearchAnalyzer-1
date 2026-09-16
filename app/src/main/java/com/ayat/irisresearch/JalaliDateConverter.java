package com.ayat.irisresearch;

public final class JalaliDateConverter {
    private JalaliDateConverter() {}

    public static String toGregorian(String input) {
        String[] p = input.trim().replace('-', '/').split("/");
        if (p.length != 3) throw new IllegalArgumentException("تاریخ را مانند 1400/01/01 وارد کنید");
        int jy=Integer.parseInt(p[0]), jm=Integer.parseInt(p[1]), jd=Integer.parseInt(p[2]);
        if (jm < 1 || jm > 12 || jd < 1 || jd > 31) throw new IllegalArgumentException("تاریخ نامعتبر است");
        int gy = jy + 621;
        int dayOfYear = (jm <= 6) ? (jm-1)*31 + (jd-1) : 186 + (jm-7)*30 + (jd-1);
        return java.time.LocalDate.of(gy, 3, 21).plusDays(dayOfYear).toString();
    }
}
