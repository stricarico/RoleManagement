package stricarico.rolemanagement;

import java.util.ArrayList;

public class Character {

    private static final String tableName = "CHARACTER";

    private String name;
    private int age;
    private Settlement settlement;
    private Profession profession;
    private String[] generalAttitude;
    private String[] strengths;
    private String[] weaknesses;
    private ArrayList<Character> relatedCharacters;
    private ArrayList<Item> items;
    private String comments;

    public Character(
            String characterName,
            int characterAge,
            Settlement characterSettlement,
            Profession characterProfession,
            String[] characterGeneralAttitude,
            String[] characterStrengths,
            String[] characterWeaknesses,
            ArrayList<Character> characterRelatedCharacters,
            ArrayList<Item> characterItems,
            String characterComments
    ) {

        name = characterName;
        age = characterAge;
        settlement = characterSettlement;
        profession = characterProfession;
        generalAttitude = characterGeneralAttitude.clone();
        strengths = characterStrengths.clone();
        weaknesses = characterWeaknesses.clone();
        relatedCharacters = characterRelatedCharacters;
        items = characterItems;
        comments = characterComments;
    }

    public void dbInsert(Character character) {
    }

    public void dbUpdate(Character characterOld, Character characterNew) {
    }

    public void dbDelete(Character character) {
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
}
