package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static RoleManagementApplication rma;
    public static Toolbar mainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rma = (RoleManagementApplication)getApplicationContext();
        mainToolbar = findViewById(R.id.app_bar);
        setSupportActionBar(mainToolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mainToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, new CampaignFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void onFragmentSelected(int id) {
        Fragment fragment = null;

        switch (id) {
            case R.id.fragmentHome:
                fragment = new CampaignFragment();
                break;
            case R.id.fragmentCharacter:
                if (checkIfAnyCampaignIsSelected()) fragment = new CharacterFragment();
                break;
            case R.id.fragmentSettlement:
                if (checkIfAnyCampaignIsSelected()) fragment = new SettlementFragment();
                break;
            case R.id.fragmentProfession:
                if (checkIfAnyCampaignIsSelected()) fragment = new ProfessionFragment();
                break;
            default:
                fragment = new CampaignFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private boolean checkIfAnyCampaignIsSelected() {

        boolean selectedCampaign = rma.checkIfSharedPreferencesContainsKey("selectedCampaign");

        if (!selectedCampaign) alertThatThereIsNoCampaignSelected();

        return selectedCampaign;
    }

    private void alertThatThereIsNoCampaignSelected() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Camapaña no Seleccionada");
        alert.setMessage("Para poder acceder " +
                "a cualquier otro menú de la aplicación, " +
                "primero debe seleccionar una Camapaña");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        onFragmentSelected(item.getItemId());
        return true;
    }
}