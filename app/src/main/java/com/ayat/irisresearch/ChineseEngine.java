package com.ayat.irisresearch;
public final class ChineseEngine {
    private ChineseEngine(){}
    private static final String[] ANIMALS={"موش","گاو","ببر","خرگوش","اژدها","مار","اسب","بز","میمون","خروس","سگ","خوک"};
    private static final String[] ELEMENTS={"چوب","آتش","خاک","فلز","آب"};
    public static String animal(int year){ return ANIMALS[Math.floorMod(year-4,12)]; }
    public static String element(int year){ return ELEMENTS[Math.floorMod((year-4)/2,5)]; }
}
