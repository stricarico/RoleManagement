package stricarico.rolemanagement;

import android.app.Application;
import android.content.res.Configuration;

public class RoleManagementApplication extends Application {

    private DatabaseHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public DatabaseHelper getDB() {
        if (this.db == null) this.initializeDataBase();
        return this.db;
    }

    private void initializeDataBase() {
        this.db = DatabaseHelper.getCurrent(this);
    }
}
