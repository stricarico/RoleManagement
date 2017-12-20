package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import static stricarico.rolemanagement.MainActivity.rma;

public class CharacterRelationCreationActivity extends AppCompatActivity {

    private Spinner spinner;

    private EditText etJudgement;

    private Button insertButton;
    private Button updateButton;

    private CharacterCharacterAdapter adapter;

    private CharacterRelation characterRelation;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_relation_creation);

        insertButton = findViewById(R.id.insertButton);
        updateButton = findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createCharacterRelation();

                saveCharacterRelation(characterRelation);
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editCharacterRelation();

                updateCharacterRelation(characterRelation);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle bundle = getIntent().getExtras();
        String characterId = bundle.getString("character_id", null);
        character = rma.getDB().dbSelectCharacterById(characterId);

        initializeDataForCreation(characterId);

        String id = bundle.getString("id", null);

        if (id == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {

            characterRelation = rma.getDB().dbSelectCharacterRelationById(id);

            initializeDataForEdition();

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void initializeDataForCreation(String characterId) {

        etJudgement = findViewById(R.id.judgement);

        List<Character> characters = rma.getDB()
                .dbSelectAllCharactersButCurrentByCampaign(
                        characterId, String.valueOf(rma.getSelectedCampaign().getId()));

        characters.remove(character);
        adapter = new CharacterCharacterAdapter(this, android.R.layout.simple_spinner_item, characters);
        spinner = findViewById(R.id.relatedCharacter);
        spinner.setAdapter(adapter);
    }

    private void initializeDataForEdition() {

        etJudgement.setText(characterRelation.getJudgement());
        spinner.setSelection(adapter.getPosition(characterRelation.getCharacterTwo().getId()));
    }

    private void createCharacterRelation() {

        characterRelation = new CharacterRelation(
                character,
                adapter.getItem(spinner.getSelectedItemPosition()),
                etJudgement.getText().toString()
        );
    }

    private void editCharacterRelation() {

        characterRelation.setCharacterTwo(adapter.getItem(spinner.getSelectedItemPosition()));
        characterRelation.setJudgement(etJudgement.getText().toString());
    }

    private void saveCharacterRelation(CharacterRelation characterRelation) {

        characterRelation.setId(rma.getDB().dbInsert(characterRelation));
    }

    private void updateCharacterRelation(CharacterRelation characterRelation) {

        rma.getDB().dbUpdateById(characterRelation);
    }

}
