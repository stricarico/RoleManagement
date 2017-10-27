package stricarico.rolemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper current;
    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        if (this.db == null) this.initializeDataBase();
        return this.db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public static String dbName = "ROLEMANAGEMENT";

    private DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(Character.tableCreationString());
        db.execSQL(Settlement.tableCreationString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DatabaseHelper getCurrent(Context context) {
        if (current == null) current = new DatabaseHelper(context);
        return current;
    }

    private void initializeDataBase() {
        this.db = this.getWritableDatabase();
    }

    public Long dbInsert(AbstractPersistentObject object) {
        return db.insert(object.getTableName(), null, object.dataInsertionValues());
    }

    public List<Settlement> dbSelectAllSettlements() {

        String query = "SELECT * FROM SETTLEMENT ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        List<Settlement> listItems = new ArrayList();

        Settlement settlement;

        while (cursor.moveToNext()) {
            settlement = new Settlement(
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4))
            );
            settlement.setId(Long.parseLong(cursor.getString(0)));
            settlement.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(settlement);
        }

        cursor.close();

        return listItems;
    }
}
