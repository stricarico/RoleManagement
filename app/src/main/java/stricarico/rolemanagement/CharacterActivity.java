package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class CharacterActivity extends AppCompatActivity {

    private static Bundle bundle;

    private Spinner settlementSpinner;
    private Spinner professionSpinner;

    private EditText etName;
    private EditText etAge;
    private EditText etHostileAttitude;
    private EditText etIndifferentAttitude;
    private EditText etFriendlyAttitude;
    private EditText etStrengths;
    private EditText etWeaknesses;

    private Button insertButton;
    private Button updateButton;

    private CharacterSettlementAdapter settlementAdapter;
    private CharacterProfessionAdapter professionAdapter;

    private Character character;
    private Campaign campaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        insertButton = (Button) findViewById(R.id.insertButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        campaign = MainActivity.rma.getSelectedCampaign();

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    character = new Character(
                            etName.getText().toString(),
                            Integer.parseInt(etAge.getText().toString()),
                            campaign,
                            settlementAdapter.getItem(settlementSpinner.getSelectedItemPosition()),
                            professionAdapter.getItem(professionSpinner.getSelectedItemPosition()),
                            new String[]{
                                    etHostileAttitude.getText().toString(),
                                    etIndifferentAttitude.getText().toString(),
                                    etFriendlyAttitude.getText().toString()
                            },
                            etStrengths.getText().toString(),
                            etWeaknesses.getText().toString()
                    );

                    saveCharacter(character);
                    finish();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateData()) {
                    character.setName(etName.getText().toString());
                    character.setAge(Integer.parseInt(etAge.getText().toString()));
                    character.setSettlement(settlementAdapter.getItem(settlementSpinner.getSelectedItemPosition()));
                    character.setProfession(professionAdapter.getItem(professionSpinner.getSelectedItemPosition()));
                    character.setGeneralAttitude(new String[]{
                            etHostileAttitude.getText().toString(),
                            etIndifferentAttitude.getText().toString(),
                            etFriendlyAttitude.getText().toString()
                    });
                    character.setStrengths(etStrengths.getText().toString());
                    character.setWeaknesses(etWeaknesses.getText().toString());

                    updateCharacter(character);
                    finish();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();

        bundle = getIntent().getExtras();

        etName = (EditText) findViewById(R.id.name);
        etAge = (EditText) findViewById(R.id.age);
        etHostileAttitude = (EditText) findViewById(R.id.hostileAttitude);
        etIndifferentAttitude = (EditText) findViewById(R.id.indifferentAttitude);
        etFriendlyAttitude = (EditText) findViewById(R.id.friendlyAttitude);
        etStrengths = (EditText) findViewById(R.id.strengths);
        etWeaknesses = (EditText) findViewById(R.id.weaknesses);

        List<Settlement> settlements = rma.getDB().dbSelectAllSettlementsByCampaign(String.valueOf(campaign.getId()));
        List<Profession> professions = rma.getDB().dbSelectAllProfessions();

        settlementAdapter = new CharacterSettlementAdapter(this, android.R.layout.simple_spinner_item, settlements);
        professionAdapter = new CharacterProfessionAdapter(this, android.R.layout.simple_spinner_item, professions);

        settlementSpinner = findViewById(R.id.settlement);
        professionSpinner = findViewById(R.id.profession);

        settlementSpinner.setAdapter(settlementAdapter);
        professionSpinner.setAdapter(professionAdapter);


        if (bundle == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {


            String id = bundle.getString("id", null);
            character = rma.getDB().dbSelectCharacterById(id);

            etName.setText(character.getName());
            etAge.setText(String.valueOf(character.getAge()));
            etHostileAttitude.setText(character.getGeneralAttitude()[0]);
            etIndifferentAttitude.setText(character.getGeneralAttitude()[1]);
            etFriendlyAttitude.setText(character.getGeneralAttitude()[2]);
            etStrengths.setText(character.getStrengths());
            etWeaknesses.setText(character.getWeaknesses());

            settlementSpinner.setSelection(settlementAdapter.getPosition(character.getSettlement().getId()));
            professionSpinner.setSelection(professionAdapter.getPosition(character.getProfession().getId()));

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void saveCharacter(Character character) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        character.setId(rma.getDB().dbInsert(character));
    }

    private void updateCharacter(Character character) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        rma.getDB().dbUpdateById(character);
    }

    private boolean validateData() {

        if (etName.getText().toString().matches("")) {
            etName.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Nombre", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etAge.getText().toString().matches("")) {
            etAge.setHintTextColor(ContextCompat.getColor(this, R.color.colorError));
            Toast.makeText(this, "Completar el campo Edad", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
