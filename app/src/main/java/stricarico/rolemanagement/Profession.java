package stricarico.rolemanagement;

import android.content.ContentValues;

public class Profession extends AbstractPersistentObject {

    private static final String tableName = "PROFESSION";

    private String name;
    private String duties;

    public Profession(String name, String duties) {
        this.name = name;
        this.duties = duties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String duties) {
        this.duties = duties;
    }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE PROFESSION (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT," +
                "DUTIES                     TEXT)";

        return table;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public ContentValues dataInsertionValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("TS", System.currentTimeMillis());
        contentValues.put("NAME", this.name);
        contentValues.put("DUTIES", this.duties);

        return contentValues;
    }

    public ContentValues dataUpdateValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", this.name);
        contentValues.put("DUTIES", this.duties);

        return contentValues;
    }
}
