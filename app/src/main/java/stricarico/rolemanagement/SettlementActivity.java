package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static stricarico.rolemanagement.MainActivity.rma;

public class SettlementActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText etName;
    private EditText etPopulation;
    private Button insertButton;
    private Button updateButton;

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
                    settlement = createSettlement();

                    saveSettlement(settlement);
                    finish();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    editSettlement();

                    updateSettlement(settlement);
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        initializeDataForCreation();

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {

            String id = bundle.getString("id", null);
            settlement = rma.getDB().dbSelectSettlementById(id);

            initializeDataForEdition();

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void initializeDataForCreation() {

        etName = findViewById(R.id.name);
        etPopulation = findViewById(R.id.population);
        etPopulation.setFilters(new InputFilter[]{new InputFilterMinMax("1", "2147483647")});

        spinner = findViewById(R.id.type);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_names, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initializeDataForEdition() {

        etName.setText(settlement.getName());
        spinner.setSelection(settlement.getType());
        etPopulation.setText(String.valueOf(settlement.getPopulation()));
    }

    private Settlement createSettlement() {

        return new Settlement(
                etName.getText().toString(),
                spinner.getSelectedItemPosition(),
                Integer.parseInt(etPopulation.getText().toString()),
                rma.getSelectedCampaign()

        );
    }

    private void editSettlement() {

        settlement.setName(etName.getText().toString());
        settlement.setType(spinner.getSelectedItemPosition());
        settlement.setPopulation(Integer.parseInt(etPopulation.getText().toString()));
    }

    private void saveSettlement(Settlement settlement) {

        settlement.setId(rma.getDB().dbInsert(settlement));
    }

    private void updateSettlement(Settlement settlement) {

        rma.getDB().dbUpdateById(settlement);
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

        if (settlementNameAlreadyExists()) {
            Toast.makeText(this, "Ya existe un Asentamiento con el nombre "
                    + etName.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean settlementNameAlreadyExists() {

        return rma.getDB().dbCheckIfSettlementNameByCampaignAlreadyExists(
                etName.getText().toString(),
                String.valueOf(rma.getSelectedCampaign().getId())
        );
    }

}
