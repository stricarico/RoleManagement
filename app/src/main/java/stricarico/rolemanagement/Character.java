package stricarico.rolemanagement;

import android.content.ContentValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Character extends AbstractPersistentObject {

    private long id;
    private String name;
    private Date ts;
    private int age;
    private Settlement settlement;
    private Profession profession;
    private String[] generalAttitude;
    private String strengths;
    private String weaknesses;
    private ArrayList<Character> relatedCharacters;
    private String comments;

    public Character(
            String characterName,
            Date characterTS,
            int characterAge,
            Settlement characterSettlement,
            Profession characterProfession,
            String[] characterGeneralAttitude,
            String characterStrengths,
            String characterWeaknesses,
            ArrayList<Character> characterRelatedCharacters,
            String characterComments
    ) {
        this.name = characterName;
        this.ts = characterTS;
        this.age = characterAge;
        this.settlement = characterSettlement;
        this.profession = characterProfession;
        this.generalAttitude = characterGeneralAttitude;
        this.strengths = characterStrengths;
        this.weaknesses = characterWeaknesses;
        this.relatedCharacters = characterRelatedCharacters;
        this.comments = characterComments;

        this.setTableName("CHARACTER");
    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Date getTS() {
        return ts;
    }

    public void setTS(Date ts) {
        this.ts = ts;
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
                "RELATED_CHARACTERS_IDS     TEXT," +
                "COMMENTS                   TEXT)";
        return table;
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
        contentValues.put("COMMENTS", this.comments);

        return contentValues;
    }
}
