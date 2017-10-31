package stricarico.rolemanagement;

import android.content.ContentValues;

public abstract class AbstractPersistentObject {

    private Long id;
    private Long ts;

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public abstract String getTableName();

    public abstract ContentValues dataInsertionValues();

    public abstract ContentValues dataUpdateValues();
}
