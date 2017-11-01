package stricarico.rolemanagement;

import android.content.ContentValues;

public class CharacterRelation extends AbstractPersistentObject {

    private static final String tableName = "CHARACTER_RELATION";

    private Character characterOne, characterTwo;
    private String characterOneTowardsTwo, getCharacterTwoTowardsOne;

    public CharacterRelation(Character characterOne, Character characterTwo, String characterOneTowardsTwo, String getCharacterTwoTowardsOne) {
        this.characterOne = characterOne;
        this.characterTwo = characterTwo;
        this.characterOneTowardsTwo = characterOneTowardsTwo;
        this.getCharacterTwoTowardsOne = getCharacterTwoTowardsOne;
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

    public String getCharacterOneTowardsTwo() {
        return characterOneTowardsTwo;
    }

    public void setCharacterOneTowardsTwo(String characterOneTowardsTwo) {
        this.characterOneTowardsTwo = characterOneTowardsTwo;
    }

    public String getGetCharacterTwoTowardsOne() {
        return getCharacterTwoTowardsOne;
    }

    public void setGetCharacterTwoTowardsOne(String getCharacterTwoTowardsOne) {
        this.getCharacterTwoTowardsOne = getCharacterTwoTowardsOne;
    }

    public static String tableCreationString() {

        String table;
        table = "CREATE TABLE " + tableName + " (" +
                "CHARACTER_ONE_ID           INTEGER," +
                "CHARACTER_TWO_ID           INTEGER," +
                "ONE_TOWARDS_TWO            TEXT," +
                "TWO_TOWARDS_ONE            TEXT," +
                "PRIMARY KEY (CHARACTER_ONE_ID, CHARACTER_TWO_ID)" +
                ")";

        return table;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public ContentValues dataInsertionValues() {
        return null;
    }

    @Override
    public ContentValues dataUpdateValues() {
        return null;
    }
}
