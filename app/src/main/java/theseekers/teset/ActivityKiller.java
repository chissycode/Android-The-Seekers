package theseekers.teset;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xkgoo on 2016/4/3.
 */
public class ActivityKiller {
    public static List<Activity> activities=new ArrayList<Activity>();
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    public  static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}
