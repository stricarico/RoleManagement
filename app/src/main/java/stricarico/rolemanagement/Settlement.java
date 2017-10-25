package stricarico.rolemanagement;

import android.content.ContentValues;
import java.sql.Timestamp;

public class Settlement extends AbstractPersistentObject {

    private static final String tableName = "SETTLEMENT";

    private long id;
    private Timestamp ts;
    private String name;
    private int type;
    private int population;

    public Settlement(
            String settlementName,
            int settlementType,
            int settlementPopulation
    ) {
        name = settlementName;
        type = settlementType;
        population = settlementPopulation;
    }

    public String getName() { return name; }
    public void setName(String name) { name = this.name; }

    public int getType() { return type; }
    public void setType(int type) { type = this.type; }

    public int getPopulation() { return population; }
    public void setPopulation(int population) { this.population = population; }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE SETTLEMENT (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT," +
                "TYPE                       INTEGER," +
                "POPULATION                 INTEGER)";

        return table;
    }

    @Override
    public ContentValues dataInsertionValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("TS", this.ts.getTime());
        contentValues.put("NAME", this.name);
        contentValues.put("TYPE", this.type);
        contentValues.put("POPULATION", this.population);

        return contentValues;

    }
}