package stricarico.rolemanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class CharacterRelationCreationActivity extends AppCompatActivity {

    private static Bundle bundle;

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

        insertButton = (Button) findViewById(R.id.insertButton);
        updateButton = (Button) findViewById(R.id.updateButton);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                characterRelation = new CharacterRelation(
                        character,
                        adapter.getItem(spinner.getSelectedItemPosition()),
                        etJudgement.getText().toString()
                );

                saveCharacterRelation(characterRelation);
                finish();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                characterRelation.setCharacterTwo(adapter.getItem(spinner.getSelectedItemPosition()));
                characterRelation.setJudgement(etJudgement.getText().toString());

                updateCharacterRelation(characterRelation);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();

        bundle = getIntent().getExtras();
        String character_id = bundle.getString("character_id", null);
        character = rma.getDB().dbSelectCharacterById(character_id);

        etJudgement = (EditText) findViewById(R.id.judgement);

        List<Character> characters = rma.getDB().dbSelectAllCharactersButCurrent(character_id);
        characters.remove(character);
        adapter = new CharacterCharacterAdapter(this, android.R.layout.simple_spinner_item, characters);
        spinner = (Spinner) findViewById(R.id.relatedCharacter);
        spinner.setAdapter(adapter);

        String id = bundle.getString("id", null);

        if (id == null) {

            updateButton.setVisibility(View.GONE);
            insertButton.setVisibility(View.VISIBLE);
        }
        else {


            characterRelation = rma.getDB().dbSelectCharacterRelationById(id);

            etJudgement.setText(characterRelation.getJudgement());

            spinner.setSelection(adapter.getPosition(characterRelation.getCharacterTwo().getId()));

            insertButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.VISIBLE);
        }
    }

    private void saveCharacterRelation(CharacterRelation characterRelation) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        characterRelation.setId(rma.getDB().dbInsert(characterRelation));
    }

    private void updateCharacterRelation(CharacterRelation characterRelation) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        rma.getDB().dbUpdateById(characterRelation);
    }

}
