package stricarico.rolemanagement;

public class Settlement {

    private String name;
    private int type;
    private int population;

    public Settlement (
            String settlementName,
            int settlementType,
            int settlementPopulation
    ) {
        name = settlementName;
        type = settlementType;
        population = settlementPopulation;
    }

    public String getName() {
        return name;
    }

    public void setName(String setterName) {
        name =  setterName;
    }

    public int getType() {
        return type;
    }

    public void setType(int setterType) {
        type = setterType;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int setterPopulation) {
        population = setterPopulation;
    }

    public void DBInsert(Settlement settlement) {
    }

    public void DBUpdate(Settlement settlementOld, Settlement settlementNew) {
    }

    public void DBDelete(Settlement settlement) {
    }

}
