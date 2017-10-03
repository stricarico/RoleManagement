package stricarico.rolemanagement;

public class Profession {

    private String name;
    private String duties;

    public Profession(String jobName, String jobDuties) {
        name = jobName;
        duties = jobDuties;
    }

    public String getName() {
        return name;
    }

    public void setName(String setterName) {
        name = setterName;
    }

    public String getDuties() {
        return duties;
    }

    public void setDuties(String setterDuties) {
        duties = setterDuties;
    }

    public void DBInsert(Profession profession) {
    }

    public void DBUpdate(Profession professionOld, Profession professionNew) {
    }

    public void DBDelete(Profession profession) {
    }

}
