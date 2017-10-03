package stricarico.rolemanagement;

import java.util.ArrayList;

public class Character {

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
        generalAttitude = characterGeneralAttitude;
        strengths = characterStrengths;
        weaknesses = characterWeaknesses;
        relatedCharacters = characterRelatedCharacters;
        items = characterItems;
        comments = characterComments;
    }

    public void DBInsert(Character character) {
    }

    public void DBUpdate(Character characterOld, Character characterNew) {
    }

    public void DBDelete(Character character) {
    }

}
