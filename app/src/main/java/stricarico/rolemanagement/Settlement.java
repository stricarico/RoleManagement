package stricarico.rolemanagement;

public class Settlement {

    private static final String tableName = "SETTLEMENT";

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

    public void dbInsert(Settlement settlement) {
    }

    public void dbUpdate(Settlement settlementOld, Settlement settlementNew) {
    }

    public void dbDelete(Settlement settlement) {
    }

    public String getName() { return name; }

    public void setName(String name) { name = this.name; }

    public int getType() { return type; }

    public void setType(int type) { type = this.type; }

    public int getPopulation() { return population; }

    public void setPopulation(int population) { this.population = population; }
}