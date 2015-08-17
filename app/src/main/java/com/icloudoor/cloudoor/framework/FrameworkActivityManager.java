package com.icloudoor.cloudoor.framework;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * Activity Stack 管理类
 * Created by Derrick Guan on 8/5/15.
 */
public class FrameworkActivityManager {

    private Stack<Activity> mActivityStack = new Stack<Activity>();
    private Map<Activity, String> mActivityTagMap = new HashMap<Activity, String>();

    private static FrameworkActivityManager instance;

    private FrameworkActivityManager() {
    }

    synchronized public static FrameworkActivityManager getInstance() {
        if (instance == null) {
            instance = new FrameworkActivityManager();
        }
        return instance;
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            mActivityTagMap.remove(activity);
            activity = null;
        }
    }


    public void addActivity(Activity activity) {
        if (activity == null)
            return;

        mActivityStack.push(activity);
    }

    public boolean setActivityTag(Activity activity, String activityTag){
        if(mActivityStack.contains(activity)){
            mActivityTagMap.put(activity, activityTag);
            return true;
        }

        return false;
    }

    public Activity getActivityByTag(String tag){
        Iterator<Map.Entry<Activity, String>> mapIt = mActivityTagMap.entrySet().iterator();
        while(mapIt.hasNext()){
            Map.Entry<Activity, String> it = mapIt.next();
            if(it.getValue().equals(tag)){
                return it.getKey();
            }
        }

        return null;
    }


    public void FinishAllActivity() {

        Activity finishActivity = null;
        while (mActivityStack.size() > 0) {
            finishActivity = mActivityStack.pop();
            if (finishActivity != null) {
                finishActivity.finish();
                finishActivity = null;
            }
        }
        mActivityTagMap.clear();
    }

}
