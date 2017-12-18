package stricarico.rolemanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {

    protected Context context;

    private static MySharedPreferences mySharedPreferences;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    private MySharedPreferences(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public static synchronized MySharedPreferences getCurrent(Context context) {

        if (mySharedPreferences == null)
            mySharedPreferences = new MySharedPreferences(context.getApplicationContext());

        return mySharedPreferences;
    }

    //region STRING
    public void setStringValue(String key, String value) {

        sharedPreferencesEditor.putString(key, value);
        sharedPreferencesEditor.commit();
    }

    public String getStringValue(String key, String defaultValue) {

        return sharedPreferences.getString(key, defaultValue);
    }
    //endregion

    //region INT
    public void setIntValue(String key, int value) {

        sharedPreferencesEditor.putInt(key, value);
        sharedPreferencesEditor.commit();
    }

    public int getIntValue(String key, int defaultValue) {

        return sharedPreferences.getInt(key, defaultValue);
    }
    //endregion

    //region LONG
    public void setLongValue(String key, Long value) {

        sharedPreferencesEditor.putLong(key, value);
        sharedPreferencesEditor.commit();
    }

    public Long getLongValue(String key, Long defaultValue) {

        return sharedPreferences.getLong(key, 0);
    }
    //endregion

    public boolean containsKey(String key) {

        return sharedPreferences.contains(key);
    }

    public void removeKey(String key) {

        if (sharedPreferencesEditor != null) {
            sharedPreferencesEditor.remove(key);
            sharedPreferencesEditor.commit();
        }
    }

    public void clear() {

        sharedPreferencesEditor.clear().commit();
    }
}