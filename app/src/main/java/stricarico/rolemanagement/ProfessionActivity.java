package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static stricarico.rolemanagement.MainActivity.rma;

public class ProfessionActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDuties;
    private Button insertButton;
    private Button updateButton;

    private Profession profession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession);

        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    profession = createProfession();

                    saveProfession(profession);
                    finish();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    editProfession();

                    updateProfession(profession);
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
            profession = rma.getDB().dbSelectProfessionById(id);

            initializeDataForEdition();

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void initializeDataForCreation() {

        etName = findViewById(R.id.name);
        etDuties = findViewById(R.id.duties);
    }

    private void initializeDataForEdition() {

        etName.setText(profession.getName());
        etDuties.setText(profession.getDuties());
    }

    private Profession createProfession() {

        return new Profession(
                etName.getText().toString(),
                etDuties.getText().toString(),
                rma.getSelectedCampaign()
        );
    }

    private void editProfession() {

        profession.setName(etName.getText().toString());
        profession.setDuties(etDuties.getText().toString());
    }

    private void saveProfession(Profession profession) {

        profession.setId(rma.getDB().dbInsert(profession));
    }

    private void updateProfession(Profession profession) {

        rma.getDB().dbUpdateById(profession);
    }

    private boolean validateData() {

        if (etName.getText().toString().matches("")) {
            etName.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Nombre", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (professionNameAlreadyExists()) {
            Toast.makeText(this, "Ya existe un Oficio con el nombre "
                    + etName.getText().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean professionNameAlreadyExists() {

        return rma.getDB().dbCheckIfProfessionNameByCampaignAlreadyExists(
                etName.getText().toString(),
                String.valueOf(rma.getSelectedCampaign().getId())
        );
    }

}
