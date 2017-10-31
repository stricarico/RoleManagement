package stricarico.rolemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

        db.execSQL(Settlement.tableCreationString());
        db.execSQL(Profession.tableCreationString());
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

    public void dbUpdateById(AbstractPersistentObject object) {
        db.update(object.getTableName(), object.dataUpdateValues(), "ID=?", new String[]{String.valueOf(object.getId())});
    }

    public void dbDeleteById(String table, String id) {
        db.delete(table, "ID=?", new String[]{id});
    }

    public Settlement dbSelectSettlementById(String id) {

        String query = "SELECT * FROM SETTLEMENT WHERE ID=" + id + " ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, null);

        Settlement settlement;

        cursor.moveToNext();

        settlement = new Settlement(
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4))
        );
        settlement.setId(Long.parseLong(cursor.getString(0)));
        settlement.setTs(Long.parseLong(cursor.getString(1)));

        cursor.close();

        return settlement;
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

    public Profession dbSelectProfessionById(String id) {

        String query = "SELECT * FROM PROFESSION WHERE ID=" + id + " ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, null);

        Profession profession;

        cursor.moveToNext();

        profession = new Profession(
                cursor.getString(2),
                cursor.getString(3)
        );
        profession.setId(Long.parseLong(cursor.getString(0)));
        profession.setTs(Long.parseLong(cursor.getString(1)));

        cursor.close();

        return profession;
    }

    public List<Profession> dbSelectAllProfessions() {

        String query = "SELECT * FROM PROFESSION ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, null);

        List<Profession> listItems = new ArrayList();

        Profession profession;

        while (cursor.moveToNext()) {
            profession = new Profession(
                    cursor.getString(2),
                    cursor.getString(3)
            );
            profession.setId(Long.parseLong(cursor.getString(0)));
            profession.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(profession);
        }

        cursor.close();

        return listItems;
    }
}
