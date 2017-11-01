package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfessionActivity extends AppCompatActivity {

    private static Bundle bundle;

    private EditText etName;
    private EditText etDuties;
    private Button insertButton;
    private Button updateButton;

    private Profession profession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession);

        insertButton = (Button) findViewById(R.id.insertButton);
        updateButton = (Button) findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    profession = new Profession(
                            etName.getText().toString(),
                            etDuties.getText().toString()
                    );

                    saveProfession(profession);
                    finish();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    profession.setName(etName.getText().toString());
                    profession.setDuties(etDuties.getText().toString());

                    updateProfession(profession);
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        bundle = getIntent().getExtras();

        etName = (EditText) findViewById(R.id.name);
        etDuties = (EditText) findViewById(R.id.duties);

        if (bundle == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {

            RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
            String id = bundle.getString("id", null);
            profession = rma.getDB().dbSelectProfessionById(id);

            etName.setText(profession.getName());
            etDuties.setText(profession.getDuties());

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void saveProfession(Profession profession) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        profession.setId(rma.getDB().dbInsert(profession));
    }

    private void updateProfession(Profession profession) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        rma.getDB().dbUpdateById(profession);
    }

    private boolean validateData() {

        if (etName.getText().toString().matches("")) {
            etName.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Nombre", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
