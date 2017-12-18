package stricarico.rolemanagement;

import android.app.Application;
import android.content.res.Configuration;

public class RoleManagementApplication extends Application {

    private DatabaseHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public DatabaseHelper getDB() {
        if (this.db == null) this.initializeDataBase();
        return this.db;
    }

    public Campaign getSelectedCampaign() {

        Campaign campaign = null;

        if (checkIfSharedPreferencesContainsKey("selectedCampaign")) {

            String campaignId = String.valueOf(
                    getMySharedPreferences()
                            .getLongValue("selectedCampaign", null));

            campaign = getDB().dbSelectCampaignById(campaignId);
        }
        return campaign;
    }

    public void setSelectedCampaign(Long selectedCampaignId) {

        getMySharedPreferences().setLongValue("selectedCampaign", selectedCampaignId);
    }

    public boolean checkIfSharedPreferencesContainsKey(String key) {

        if (getMySharedPreferences().containsKey(key))
            return true;

        return false;
    }

    private MySharedPreferences getMySharedPreferences() {

        return MySharedPreferences.getCurrent(this);
    }

    private void initializeDataBase() {
        this.db = DatabaseHelper.getCurrent(this);
    }
}
