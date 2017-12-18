package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettlementActivity extends AppCompatActivity {

    private static Bundle bundle;

    private Spinner spinner;
    private EditText etName;
    private EditText etPopulation;
    private Button insertButton;
    private Button updateButton;

    private ArrayAdapter<CharSequence> adapter;
    private Settlement settlement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    settlement = new Settlement(
                            etName.getText().toString(),
                            spinner.getSelectedItemPosition(),
                            Integer.parseInt(etPopulation.getText().toString()),
                            MainActivity.rma.getSelectedCampaign()

                    );

                    saveSettlement(settlement);
                    finish();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    settlement.setName(etName.getText().toString());
                    settlement.setType(spinner.getSelectedItemPosition());
                    settlement.setPopulation(Integer.parseInt(etPopulation.getText().toString()));

                    updateSettlement(settlement);
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        bundle = getIntent().getExtras();
        spinner = findViewById(R.id.type);

        etName = findViewById(R.id.name);
        etPopulation = findViewById(R.id.population);

        adapter = ArrayAdapter.createFromResource(this, R.array.type_names, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (bundle == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {

            RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
            String id = bundle.getString("id", null);
            settlement = rma.getDB().dbSelectSettlementById(id);

            etName.setText(settlement.getName());
            spinner.setSelection(settlement.getType());
            etPopulation.setText(String.valueOf(settlement.getPopulation()));

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void saveSettlement(Settlement settlement) {

        settlement.setId(MainActivity.rma.getDB().dbInsert(settlement));
    }

    private void updateSettlement(Settlement settlement) {

        MainActivity.rma.getDB().dbUpdateById(settlement);
    }

    private boolean validateData() {

        if (etName.getText().toString().matches("")) {
            etName.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPopulation.getText().toString().matches("")) {
            etPopulation.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Población", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
