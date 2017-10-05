package stricarico.rolemanagement;

import android.content.ContentValues;

public class Profession extends ORPPersistentObject {

    private static final String tableName = "PROFESSION";

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

    @Override
    public ContentValues dataInsertionValues() {
        return null;
    }
}
