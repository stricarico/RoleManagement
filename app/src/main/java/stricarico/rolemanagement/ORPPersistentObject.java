package stricarico.rolemanagement;

import android.content.ContentValues;

public abstract class ORPPersistentObject {

    private long id;
    private String tableName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public abstract ContentValues dataInsertionValues();
}
