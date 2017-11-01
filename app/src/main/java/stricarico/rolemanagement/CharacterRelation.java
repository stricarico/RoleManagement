package stricarico.rolemanagement;

import android.content.ContentValues;

public class CharacterRelation extends AbstractPersistentObject {

    private static final String tableName = "CHARACTER_RELATION";

    private Character characterOne, characterTwo;
    private String judgement;

    public CharacterRelation(Character characterOne, Character characterTwo, String judgement) {
        this.characterOne = characterOne;
        this.characterTwo = characterTwo;
        this.judgement = judgement;
    }

    public Character getCharacterOne() {
        return characterOne;
    }

    public void setCharacterOne(Character characterOne) {
        this.characterOne = characterOne;
    }

    public Character getCharacterTwo() {
        return characterTwo;
    }

    public void setCharacterTwo(Character characterTwo) {
        this.characterTwo = characterTwo;
    }

    public String getJudgement() {
        return judgement;
    }

    public void setJudgement(String judgement) {
        this.judgement = judgement;
    }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE " + tableName + " (" +
                "ID                         INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CHARACTER_ONE_ID           INTEGER," +
                "CHARACTER_TWO_ID           INTEGER," +
                "TS                         DATE," +
                "JUDGEMENT                  TEXT)";

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
        contentValues.put("CHARACTER_ONE_ID", this.characterOne.getId());
        contentValues.put("CHARACTER_TWO_ID", this.characterTwo.getId());
        contentValues.put("JUDGEMENT", this.judgement);

        return contentValues;
    }

    @Override
    public ContentValues dataUpdateValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put("CHARACTER_ONE_ID", this.characterOne.getId());
        contentValues.put("CHARACTER_TWO_ID", this.characterTwo.getId());
        contentValues.put("JUDGEMENT", this.judgement);

        return contentValues;
    }
}
