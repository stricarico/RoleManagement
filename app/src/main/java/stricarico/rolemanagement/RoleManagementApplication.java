package stricarico.rolemanagement;

import android.app.Application;

public class RoleManagementApplication extends Application {

    private DatabaseHelper db;

    public RoleManagementApplication(){

    }

    public DatabaseHelper getDB() {
        if (this.db == null) this.initializeDataBase();
        return this.db;
    }

    private void initializeDataBase() {
        this.db = DatabaseHelper.getCurrent(this);
    }
}
