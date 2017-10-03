package stricarico.rolemanagement;

public class Job {

    private String name;
    private String duties;

    public Job (String jobName, String jobDuties) {
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

}
