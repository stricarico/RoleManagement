package stricarico.rolemanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper current;
    private SQLiteDatabase db;
    public static String dbName = "ROLEMANAGEMENT";

    private DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Character.tableCreationString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DatabaseHelper getCurrent(Context context) {
        if (current == null) current = new DatabaseHelper(context);
        return current;
    }

    public long dbInsert(AbstractPersistentObject object) {
        return db.insert(object.getTableName(), null, object.dataInsertionValues());
    }
}
