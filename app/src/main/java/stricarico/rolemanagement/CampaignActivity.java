package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static stricarico.rolemanagement.MainActivity.rma;

public class CampaignActivity extends AppCompatActivity {

    private static Bundle bundle;

    private EditText etName;

    private Button insertButton;
    private Button updateButton;

    private Campaign campaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign);

        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    campaign = new Campaign(
                            etName.getText().toString()
                    );

                    saveCampaign(campaign);
                    finish();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    campaign.setName(etName.getText().toString());

                    updateCampaign(campaign);
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        bundle = getIntent().getExtras();

        etName = findViewById(R.id.name);

        if (bundle == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {


            String id = bundle.getString("id", null);
            campaign = MainActivity.rma.getDB().dbSelectCampaignById(id);

            etName.setText(campaign.getName());

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void saveCampaign(Campaign campaign) {

        campaign.setId(MainActivity.rma.getDB().dbInsert(campaign));
    }

    private void updateCampaign(Campaign campaign) {

        MainActivity.rma.getDB().dbUpdateById(campaign);
    }

    private boolean validateData() {

        if (etName.getText().toString().matches("")) {
            etName.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Nombre", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (campaignNameAlreadyExists()) {
            Toast.makeText(this, "Ya existe una Campaña con el nombre "
                    + etName.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean campaignNameAlreadyExists() {

        return rma.getDB().dbCheckIfCampaignNameAlreadyExists(
                etName.getText().toString()
        );
    }

}
