package stricarico.rolemanagement;

import android.content.ContentValues;


public class Campaign extends AbstractPersistentObject {

    private static final String tableName = "CAMPAIGN";

    private String name;

    public Campaign(String characterName) {
        this.name = characterName;
    }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE CAMPAIGN (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT)";

        return table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        return contentValues;
    }

    @Override
    public ContentValues dataUpdateValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", this.name);

        return contentValues;
    }
}
