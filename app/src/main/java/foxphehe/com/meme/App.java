package foxphehe.com.meme;

import android.app.Application;
import android.content.Context;

/**
 * Created by foxphehe on 2018/1/26.
 */

public class App extends Application {

    private static Context context;
    private static int[] syokuzi;
    private static int[] hikii;
    private static int[] kaeru_niwa;
    private static int[] sagyou_ie;
    private static int[] sitaku_ie;
    private static int[] sleepy;
    private static int[] sleep;


    @Override
    public void onCreate() {
        super.onCreate();
        App.context = this;
        //吃饭
        syokuzi = new int[]{9, 12, 19};
        //写东西
        hikii = new int[]{15, 16, 20};
        //背帽子
        kaeru_niwa = new int[]{10, 18};
        //做手工
        sagyou_ie = new int[]{11, 13, 14};
        //整理背包
        sitaku_ie = new int[]{17, 21};
        //犯困
        sleepy = new int[]{22};
        //睡觉
        sleep = new int[]{23, 0, 1, 2, 3, 4, 5, 6, 7, 8};
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        App.context = context;
    }

    public static int[] getSyokuzi() {
        return syokuzi;
    }

    public static void setSyokuzi(int[] syokuzi) {
        App.syokuzi = syokuzi;
    }

    public static int[] getHikii() {
        return hikii;
    }

    public static void setHikii(int[] hikii) {
        App.hikii = hikii;
    }

    public static int[] getKaeru_niwa() {
        return kaeru_niwa;
    }

    public static void setKaeru_niwa(int[] kaeru_niwa) {
        App.kaeru_niwa = kaeru_niwa;
    }

    public static int[] getSagyou_ie() {
        return sagyou_ie;
    }

    public static void setSagyou_ie(int[] sagyou_ie) {
        App.sagyou_ie = sagyou_ie;
    }

    public static int[] getSitaku_ie() {
        return sitaku_ie;
    }

    public static void setSitaku_ie(int[] sitaku_ie) {
        App.sitaku_ie = sitaku_ie;
    }

    public static int[] getSleepy() {
        return sleepy;
    }

    public static void setSleepy(int[] sleepy) {
        App.sleepy = sleepy;
    }

    public static int[] getSleep() {
        return sleep;
    }

    public static void setSleep(int[] sleep) {
        App.sleep = sleep;
    }
}
