package stricarico.rolemanagement;

import android.content.ContentValues;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;


public class Character extends AbstractPersistentObject {

    private static final String tableName = "CHARACTER";

    private String name;
    private int age;
    private Settlement settlement;
    private Profession profession;
    private String[] generalAttitude;
    private String strengths;
    private String weaknesses;
    private ArrayList<Character> relatedCharacters;

    public Character(
            String characterName,
            int characterAge,
            Settlement characterSettlement,
            Profession characterProfession,
            String[] characterGeneralAttitude,
            String characterStrengths,
            String characterWeaknesses,
            ArrayList<Character> characterRelatedCharacters
    ) {
        this.name = characterName;
        this.age = characterAge;
        this.settlement = characterSettlement;
        this.profession = characterProfession;
        this.generalAttitude = characterGeneralAttitude;
        this.strengths = characterStrengths;
        this.weaknesses = characterWeaknesses;
        this.relatedCharacters = characterRelatedCharacters;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public Settlement getSettlement() {
        return settlement;
    }
    public void setSettlement(Settlement settlement) {
        this.settlement = settlement;
    }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE CHARACTER (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT," +
                "AGE                        INT," +
                "SETTLEMENT_ID              INTEGER," +
                "PROFESSION_ID              INTEGER," +
                "HOSTILE_ATTITUDE           TEXT," +
                "INDIFFERENT_ATTITUDE       TEXT," +
                "FRIENDLY_ATTITUDE          TEXT," +
                "STRENGTHS                  TEXT," +
                "WEAKNESSES                 TEXT," +
                "RELATED_CHARACTERS_IDS     TEXT)";

        return table;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public ContentValues dataInsertionValues() {

        ContentValues contentValues = new ContentValues();
        String[] relatedCharactersIDS = new String[relatedCharacters.size()];

        for (Character each: relatedCharacters) {
            relatedCharactersIDS[relatedCharacters.indexOf(each)] = String.valueOf(each.getId());
        }

        String relatedCharactersString = Arrays.toString(relatedCharactersIDS);
        relatedCharactersString = relatedCharactersString.substring(1, relatedCharactersString.length() -1);

        contentValues.put("TS", System.currentTimeMillis());
        contentValues.put("NAME", this.name);
        contentValues.put("AGE", this.age);
        contentValues.put("SETTLEMENT_ID", this.settlement.getId());
        contentValues.put("PROFESSION_ID", this.profession.getId());
        contentValues.put("HOSTILE_ATTITUDE", this.generalAttitude[0]);
        contentValues.put("INDIFFERENT_ATTITUDE", this.generalAttitude[1]);
        contentValues.put("FRIENDLY_ATTITUDE", this.generalAttitude[2]);
        contentValues.put("STRENGTHS", this.strengths);
        contentValues.put("WEAKNESSES", this.weaknesses);
        contentValues.put("RELATED_CHARACTERS_IDS", relatedCharactersString);

        return contentValues;
    }

    @Override
    public ContentValues dataUpdateValues() {
        return null;
    }
}
