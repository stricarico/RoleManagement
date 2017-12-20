package stricarico.rolemanagement;

import android.content.ContentValues;

public class Profession extends AbstractPersistentObject {

    private static final String tableName = "PROFESSION";

    private String name;
    private String duties;
    private Campaign campaign;

    public Profession(String name, String duties, Campaign campaign) {
        this.name = name;
        this.duties = duties;
        this.campaign = campaign;
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
        table = "CREATE TABLE " + tableName + " (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT," +
                "DUTIES                     TEXT," +
                "CAMPAIGN_ID                INTEGER)";

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
        contentValues.put("CAMPAIGN_ID", this.campaign.getId());

        return contentValues;
    }

    public ContentValues dataUpdateValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", this.name);
        contentValues.put("DUTIES", this.duties);

        return contentValues;
    }
}
