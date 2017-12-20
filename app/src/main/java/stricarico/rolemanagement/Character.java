package stricarico.rolemanagement;

import android.content.ContentValues;

import java.util.Arrays;
import java.util.List;


public class Character extends AbstractPersistentObject {

    private static final String tableName = "CHARACTER";

    private String name;
    private int age;
    private Campaign campaign;
    private Settlement settlement;
    private Profession profession;
    private String[] generalAttitude;
    private String strengths;
    private String weaknesses;

    public Character(
            String characterName,
            int characterAge,
            Campaign characterCampaign,
            Settlement characterSettlement,
            Profession characterProfession,
            String[] characterGeneralAttitude,
            String characterStrengths,
            String characterWeaknesses
    ) {
        this.name = characterName;
        this.age = characterAge;
        this.campaign = characterCampaign;
        this.settlement = characterSettlement;
        this.profession = characterProfession;
        this.generalAttitude = characterGeneralAttitude;
        this.strengths = characterStrengths;
        this.weaknesses = characterWeaknesses;
    }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE " + tableName + " (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TS                         DATE," +
                "NAME                       TEXT," +
                "AGE                        INTEGER," +
                "CAMPAIGN_ID                INTEGER," +
                "SETTLEMENT_ID              INTEGER," +
                "PROFESSION_ID              INTEGER," +
                "HOSTILE_ATTITUDE           TEXT," +
                "INDIFFERENT_ATTITUDE       TEXT," +
                "FRIENDLY_ATTITUDE          TEXT," +
                "STRENGTHS                  TEXT," +
                "WEAKNESSES                 TEXT)";

        return table;
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

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public String[] getGeneralAttitude() {
        return generalAttitude;
    }

    public void setGeneralAttitude(String[] generalAttitude) {
        this.generalAttitude = generalAttitude;
    }

    public String getStrengths() {
        return strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
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
        contentValues.put("AGE", this.age);
        contentValues.put("CAMPAIGN_ID", this.campaign.getId());
        contentValues.put("SETTLEMENT_ID", this.settlement.getId());
        contentValues.put("PROFESSION_ID", this.profession.getId());
        contentValues.put("HOSTILE_ATTITUDE", this.generalAttitude[0]);
        contentValues.put("INDIFFERENT_ATTITUDE", this.generalAttitude[1]);
        contentValues.put("FRIENDLY_ATTITUDE", this.generalAttitude[2]);
        contentValues.put("STRENGTHS", this.strengths);
        contentValues.put("WEAKNESSES", this.weaknesses);

        return contentValues;
    }

    @Override
    public ContentValues dataUpdateValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", this.name);
        contentValues.put("AGE", this.age);
        contentValues.put("SETTLEMENT_ID", this.settlement.getId());
        contentValues.put("PROFESSION_ID", this.profession.getId());
        contentValues.put("HOSTILE_ATTITUDE", this.generalAttitude[0]);
        contentValues.put("INDIFFERENT_ATTITUDE", this.generalAttitude[1]);
        contentValues.put("FRIENDLY_ATTITUDE", this.generalAttitude[2]);
        contentValues.put("STRENGTHS", this.strengths);
        contentValues.put("WEAKNESSES", this.weaknesses);

        return contentValues;
    }
}
