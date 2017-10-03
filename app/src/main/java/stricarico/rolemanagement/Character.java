package stricarico.rolemanagement;

import java.util.ArrayList;

public class Character {

    private String name;
    private Object settlement;
    private Job job;
    private String[] attitude;
    private String[] strengths;
    private String[] weaknesses;
    private ArrayList<Character> relationships;
    private ArrayList<Item> items;

    public Character(
            String characterName,
            Object characterSettlement,
            Job characterJob,
            String[] characterAttitudes,
            String[] characterStrengths,
            String[] characterWeaknesses,
            ArrayList<Character> characterRelationships,
            ArrayList<Item> characterItems
    ) {

        name = characterName;
        settlement = characterSettlement;
        job = characterJob;
        attitude = characterAttitudes;
        strengths = characterStrengths;
        weaknesses = characterWeaknesses;
        relationships = characterRelationships;
        items = characterItems;
    }

}
