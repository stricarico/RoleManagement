package stricarico.rolemanagement;

import android.content.ContentValues;
import java.sql.Timestamp;

public class Settlement extends AbstractPersistentObject {

    private static final String tableName = "SETTLEMENT";

    private String name;
    private int type;
    private int population;

    public Settlement(
            String settlementName,
            int settlementType,
            int settlementPopulation
    ) {
        this.name = settlementName;
        this.type = settlementType;
        this.population = settlementPopulation;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public int getType() { return type; }
    public void setType(int type) {
        this.type = type;
    }

    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE " + tableName + " (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT," +
                "TYPE                       INTEGER," +
                "POPULATION                 INTEGER)";

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
        contentValues.put("TYPE", this.type);
        contentValues.put("POPULATION", this.population);

        return contentValues;
    }

    @Override
    public ContentValues dataUpdateValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", this.name);
        contentValues.put("TYPE", this.type);
        contentValues.put("POPULATION", this.population);

        return contentValues;
    }
}