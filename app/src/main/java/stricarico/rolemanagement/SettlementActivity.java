package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

        insertButton = (Button) findViewById(R.id.insertButton);
        updateButton = (Button) findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settlement = new Settlement(
                        etName.getText().toString(),
                        spinner.getSelectedItemPosition(),
                        Integer.parseInt(etPopulation.getText().toString())
                );

                saveSettlement(settlement);
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                settlement.setName(etName.getText().toString());
                settlement.setType(spinner.getSelectedItemPosition());
                settlement.setPopulation(Integer.parseInt(etPopulation.getText().toString()));

                updateSettlement(settlement);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        bundle = getIntent().getExtras();
        spinner = (Spinner) findViewById(R.id.type);

        etName = (EditText) findViewById(R.id.name);
        etPopulation = (EditText) findViewById(R.id.population);

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

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        settlement.setId(rma.getDB().dbInsert(settlement));
    }

    private void updateSettlement(Settlement settlement) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        rma.getDB().dbUpdateSettlementById(settlement);
    }

}
