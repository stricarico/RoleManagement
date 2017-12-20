package stricarico.rolemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper current;
    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
        if (this.db == null) this.initializeDataBase();
        return this.db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    public static String dbName = "ROLEMANAGEMENT";

    private DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Campaign.tableCreationString());
        db.execSQL(CharacterRelation.tableCreationString());
        db.execSQL(Character.tableCreationString());
        db.execSQL(Settlement.tableCreationString());
        db.execSQL(Profession.tableCreationString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DatabaseHelper getCurrent(Context context) {
        if (current == null) current = new DatabaseHelper(context);
        return current;
    }

    private void initializeDataBase() {
        this.db = this.getWritableDatabase();
    }

    public Long dbInsert(AbstractPersistentObject object) {
        return db.insert(object.getTableName(), null, object.dataInsertionValues());
    }

    public void dbUpdateById(AbstractPersistentObject object) {
        db.update(object.getTableName(), object.dataUpdateValues(), "ID=?", new String[]{String.valueOf(object.getId())});
    }

    public void dbDeleteById(String table, String id) {
        db.delete(table, "ID=?", new String[]{id});
    }

    public Campaign dbSelectCampaignById(String id) {

        String query = "SELECT * FROM CAMPAIGN WHERE ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        Campaign campaign;

        cursor.moveToNext();

        campaign = new Campaign(
                cursor.getString(2)
        );
        campaign.setId(Long.parseLong(cursor.getString(0)));
        campaign.setTs(Long.parseLong(cursor.getString(1)));

        cursor.close();

        return campaign;
    }

    public List<Campaign> dbSelectAllCampaigns() {

        String query = "SELECT * FROM CAMPAIGN ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        List<Campaign> listItems = new ArrayList();

        Campaign campaign;

        while (cursor.moveToNext()) {
            campaign = new Campaign(
                    cursor.getString(2)
            );
            campaign.setId(Long.parseLong(cursor.getString(0)));
            campaign.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(campaign);
        }

        cursor.close();

        return listItems;
    }

    public Character dbSelectCharacterById(String id) {

        String query = "SELECT * FROM CHARACTER WHERE ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        Character character;

        cursor.moveToNext();

        character = new Character(
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                dbSelectCampaignById(cursor.getString(4)),
                dbSelectSettlementById(cursor.getString(5)),
                dbSelectProfessionById(cursor.getString(6)),
                new String[] {
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9)
                },
                cursor.getString(10),
                cursor.getString(11)
        );
        character.setId(Long.parseLong(cursor.getString(0)));
        character.setTs(Long.parseLong(cursor.getString(1)));

        cursor.close();

        return character;
    }

    public List<Character> dbSelectAllCharactersByCampaign(String campaignId) {

        String query = "SELECT * FROM CHARACTER WHERE CAMPAIGN_ID=" + campaignId + " ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        List<Character> listItems = new ArrayList();

        Character character;

        while (cursor.moveToNext()) {
            character = new Character(
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    dbSelectCampaignById(cursor.getString(4)),
                    dbSelectSettlementById(cursor.getString(5)),
                    dbSelectProfessionById(cursor.getString(6)),
                    new String[] {
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9)
                    },
                    cursor.getString(10),
                    cursor.getString(11)
            );
            character.setId(Long.parseLong(cursor.getString(0)));
            character.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(character);
        }

        cursor.close();

        return listItems;
    }

    public List<Character> dbSelectAllCharactersButCurrentByCampaign(String id, String campaignId) {

        String query = "SELECT * FROM CHARACTER WHERE ID!=" + id
                + " AND CAMPAIGN_ID=" + campaignId + " ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        List<Character> listItems = new ArrayList();

        Character character;

        while (cursor.moveToNext()) {
            character = new Character(
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    dbSelectCampaignById(cursor.getString(4)),
                    dbSelectSettlementById(cursor.getString(5)),
                    dbSelectProfessionById(cursor.getString(6)),
                    new String[] {
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9)
                    },
                    cursor.getString(10),
                    cursor.getString(11)
            );
            character.setId(Long.parseLong(cursor.getString(0)));
            character.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(character);
        }

        cursor.close();

        return listItems;
    }

    public String dbCheckIfCharacterIsRelatedToAnotherCharacter(String id) {

        String query = "SELECT * FROM CHARACTER_RELATION WHERE CHARACTER_TWO_ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        if (cursor.moveToNext()) {
            Character character = dbSelectCharacterById(cursor.getString(1));

            return character.getName();
        }
        else return null;
    }

    public boolean dbCheckIfThereIsAnyOtherCharacterByCampaign(String id, String campaignId) {

        String query = "SELECT * FROM CHARACTER WHERE ID!=" + id + " AND CAMPAIGN_ID=" + campaignId;
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        if (cursor.moveToNext()) {
            return true;
        }
        else return false;
    }

    public CharacterRelation dbSelectCharacterRelationById(String id) {

        String query = "SELECT * FROM CHARACTER_RELATION WHERE ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        CharacterRelation characterRelation = null;

        if (cursor.moveToNext()) {

            characterRelation = new CharacterRelation(
                    dbSelectCharacterById(cursor.getString(1)),
                    dbSelectCharacterById(cursor.getString(2)),
                    cursor.getString(4)
            );
            characterRelation.setId(Long.parseLong(cursor.getString(0)));
            characterRelation.setTs(Long.parseLong(cursor.getString(3)));
        }

        cursor.close();

        return characterRelation;
    }

    public List<CharacterRelation> dbSelectAllCharacterRelationsByCharacterId(String id) {

        String query = "SELECT * FROM CHARACTER_RELATION WHERE CHARACTER_ONE_ID=" + id + " ORDER BY CHARACTER_TWO_ID ASC";
        Cursor cursor = this.getDb().rawQuery(query, null);

        List<CharacterRelation> listItems = new ArrayList();

        CharacterRelation characterRelation;

        while (cursor.moveToNext()) {
            characterRelation = new CharacterRelation(
                    dbSelectCharacterById(cursor.getString(1)),
                    dbSelectCharacterById(cursor.getString(2)),
                    cursor.getString(4)
            );
            characterRelation.setId(Long.parseLong(cursor.getString(0)));
            characterRelation.setTs(Long.parseLong(cursor.getString(3)));

            listItems.add(characterRelation);
        }

        cursor.close();

        return listItems;
    }

    public Settlement dbSelectSettlementById(String id) {

        String query = "SELECT * FROM SETTLEMENT WHERE ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        Settlement settlement;

        cursor.moveToNext();

        settlement = new Settlement(
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                dbSelectCampaignById(cursor.getString(5))
        );
        settlement.setId(Long.parseLong(cursor.getString(0)));
        settlement.setTs(Long.parseLong(cursor.getString(1)));

        cursor.close();

        return settlement;
    }

    public List<Settlement> dbSelectAllSettlementsByCampaign(String campaignId) {


        String query = "SELECT * FROM SETTLEMENT WHERE CAMPAIGN_ID=" + campaignId + " ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        List<Settlement> listItems = new ArrayList();

        Settlement settlement;

        while (cursor.moveToNext()) {
            settlement = new Settlement(
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    dbSelectCampaignById(cursor.getString(5))
            );
            settlement.setId(Long.parseLong(cursor.getString(0)));
            settlement.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(settlement);
        }

        cursor.close();

        return listItems;
    }

    public String dbCheckIfSettlementIsRelatedToACharacter(String id) {

        String query = "SELECT * FROM CHARACTER WHERE SETTLEMENT_ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        if (cursor.moveToNext()) {
            return cursor.getString(2);
        }
        else return null;
    }

    public boolean dbCheckIfThereIsAnySettlementByCampaign(String campaignId) {

        String query = "SELECT * FROM SETTLEMENT WHERE CAMPAIGN_ID=" + campaignId;
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        if (cursor.moveToNext()) {
            return true;
        }
        else return false;
    }

    public Profession dbSelectProfessionById(String id) {

        String query = "SELECT * FROM PROFESSION WHERE ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        Profession profession;

        cursor.moveToNext();

        profession = new Profession(
                cursor.getString(2),
                cursor.getString(3),
                dbSelectCampaignById(cursor.getString(4))
        );
        profession.setId(Long.parseLong(cursor.getString(0)));
        profession.setTs(Long.parseLong(cursor.getString(1)));

        cursor.close();

        return profession;
    }

    public List<Profession> dbSelectAllProfessionsByCampaign(String campaignId) {

        String query = "SELECT * FROM PROFESSION WHERE CAMPAIGN_ID=" + campaignId + " ORDER BY NAME ASC";
        Cursor cursor = this.getDb().rawQuery(query, null);

        List<Profession> listItems = new ArrayList();

        Profession profession;

        while (cursor.moveToNext()) {
            profession = new Profession(
                    cursor.getString(2),
                    cursor.getString(3),
                    dbSelectCampaignById(cursor.getString(4))
            );
            profession.setId(Long.parseLong(cursor.getString(0)));
            profession.setTs(Long.parseLong(cursor.getString(1)));

            listItems.add(profession);
        }

        cursor.close();

        return listItems;
    }

    public String dbCheckIfProfessionIsRelatedToACharacter(String id) {

        String query = "SELECT * FROM CHARACTER WHERE PROFESSION_ID=" + id;
        Cursor cursor = this.getDb().rawQuery(query, null);

        if (cursor.moveToNext()) {
            return cursor.getString(2);
        }
        else return null;
    }

    public boolean dbCheckIfThereIsAnyProfessionByCampaign(String campaignId) {

        String query = "SELECT * FROM PROFESSION WHERE CAMPAIGN_ID=" + campaignId;
        Cursor cursor = this.getDb().rawQuery(query, new String[]{});

        if (cursor.moveToNext()) {
            return true;
        }
        else return false;
    }
}
