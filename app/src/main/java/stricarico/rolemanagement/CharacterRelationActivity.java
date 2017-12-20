package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import static stricarico.rolemanagement.MainActivity.rma;

public class CharacterRelationActivity extends AppCompatActivity {

    private Bundle bundle;
    private Character character;

    private CharacterRelationAdapter characterRelationAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_relation);

        recyclerView = findViewById(R.id.characterRelationRecyclerView);

        FloatingActionButton fab = findViewById(R.id.newCharacterRelation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String character_id = String.valueOf(character.getId());

                Intent resultIntent = new Intent(view.getContext(), CharacterRelationCreationActivity.class);
                resultIntent.putExtra("character_id", character_id);
                startActivity(resultIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        bundle = getIntent().getExtras();
        String id = bundle.getString("id", null);
        character = rma.getDB().dbSelectCharacterById(id);

        List<CharacterRelation> listItems = getItemsToList();

        characterRelationAdapter = new CharacterRelationAdapter(listItems, this);
        recyclerView.setAdapter(characterRelationAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(recyclerView);
    }

    private List<CharacterRelation> getItemsToList() {

        String id = bundle.getString("id", null);

        return rma.getDB().dbSelectAllCharacterRelationsByCharacterId(id);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = characterRelationAdapter.getPosition();

        switch (item.getItemId()) {

            case R.id.edit:
                updateItemAtPosition(position);
                break;
            case R.id.delete:
                attemptToDeleteItemAtPosition(position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void updateItemAtPosition(int position) {

        String id = String.valueOf(characterRelationAdapter.getItem(position).getId());
        String character_id = String.valueOf(characterRelationAdapter.getItem(position).getCharacterOne().getId());

        Intent resultIntent = new Intent(this, CharacterRelationCreationActivity.class);
        resultIntent.putExtra("id", id);
        resultIntent.putExtra("character_id", character_id);
        startActivity(resultIntent);
    }

    private void attemptToDeleteItemAtPosition (int position) {

        CharacterRelation characterRelationToDelete = characterRelationAdapter.getItem(position);

        askToDeleteCharacter(
                characterRelationToDelete.getCharacterOne().getName(),
                characterRelationToDelete.getCharacterTwo().getName(),
                characterRelationToDelete.getTableName(),
                String.valueOf(characterRelationToDelete.getId())
        );
    }

    private void askToDeleteCharacter(
            final String characterOneName,
            final String characterTwoName,
            final String characterTableName,
            final String characterId) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Eliminar Relación entre Personajes");
        alert.setMessage("¿Está seguro que desea eliminar la relación entre el Personaje "
                + characterOneName +
                " y el Personaje "
                + characterTwoName + "?");

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteCharacterRelationById(characterTableName, characterId);
            }
        });

        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    public void deleteCharacterRelationById(String tableName, String id) {

        rma.getDB().dbDeleteById(tableName, id);

        onResume();
    }

}
