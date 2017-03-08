package in.informationworks.learnaptcomic.Models;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Riya on 07-Mar-17.
 */

public class AppStorageAgent  extends Application {

    private static SharedPreferences sharedPref;
    private static int recievedId;
    Context activityContext;
    private static String recievedEmail;
    private static Boolean isLoggedIn;

    public AppStorageAgent(Context activityContext)
    {
        this.activityContext = activityContext;
    }
    //These methods do use of activityContext so they can not be used as static

    public static String getSharedStoredString(String key, Context context) {
        try
        {
            //String demandedValue = context.getSharedPreferences("learnapt_preference", MODE_PRIVATE).getString(key, "");
            //return demandedValue;
            sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            recievedEmail = sharedPref.getString(key,null);
            return recievedEmail;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }
    //Returns INT value for givenKey. -99 shows that value is not stored.
    public static int getSharedStoredInt(String key, Context context)
    {
        //int demandedValue = context.getSharedPreferences("learnapt_preference", MODE_PRIVATE).getInt(key, -99);
        //return demandedValue;
        sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        recievedId = sharedPref.getInt(key,0);
        return recievedId;
    }

    public static boolean getSharedStoredBoolean(String key, Context context)
    {
        try
        {
           // boolean demandedValue = context.getSharedPreferences("learnapt_preference", MODE_PRIVATE).getBoolean(key, false);
            //return demandedValue;
            sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            isLoggedIn = sharedPref.getBoolean(key,false);
            return isLoggedIn;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Stores string value with key "key"
     *
     * @param key:   key for storage
     * @param value: value to store for given key value
     */
    public static void setSharedStoreString(String key, String value, Context context) {
        sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setSharedStoreInt(String key, int value, Context context) {
        sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static void setSharedStoreBoolean(String key, boolean value, Context context) {
        sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void removeEntries(Context context) {
        sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try
        {
            editor.clear();
        }
        catch (Exception ex)
        {

                    }
        editor.commit();
    }

    public static void removeEntry(String key, Context context) {
        sharedPref = context.getSharedPreferences("LearnaptComic_preference", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        try
        {
            editor.remove(key);
        }
        catch (Exception ex)
        {
            Log.e("Remove preference error", ex.toString());
            Log.d("Probable error", "The value for " + key + " is not set yet"
            );
        }
        editor.commit();
    }

    /**
     * Stores string value with key "key"
     *
     * @param key:   key for storage
     * @param value: value to store for given key value
     */



}
