package stricarico.rolemanagement;

public class Item {

    private String name;
    private String description;
    private String system;

    public Item(
        String itemName,
        String itemDescription,
        String itemSystem
    ) {
        name = itemName;
        description = itemDescription;
        system = itemSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String setterName) {
        name = setterName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String setterDescription) {
        description = setterDescription;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String setterSystem) {
        system = setterSystem;
    }

    public void DBInsert(Item item) {
    }

    public void DBUpdate(Item itemOld, Item itemNew) {
    }

    public void DBDelete(Item item) {
    }

}
