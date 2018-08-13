package mangalorexpress.com;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by akhil on 9/7/17.
 */

public class SharedPrefManager {
    Context context;
    SharedPreferences preferences;

    public SharedPrefManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences("mangalore_xpress",Context.MODE_PRIVATE);
    }

    public String getString(String key){
        return  preferences.getString(key,null);
    }

    public int getInt(String key){
        return preferences.getInt(key,-1);
    }

    public void setString(String key,String value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public void setInt(String key,int value){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }
}
