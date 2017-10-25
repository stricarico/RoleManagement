package stricarico.rolemanagement;

public class ListItem {

    private String name, type, population;

    public ListItem(String name, String type, String population) {
        this.name = name;
        this.type = type;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPopulation() {
        return population;
    }
}
