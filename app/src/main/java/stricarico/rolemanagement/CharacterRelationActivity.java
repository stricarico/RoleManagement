package stricarico.rolemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class CharacterRelationActivity extends AppCompatActivity {

    private Bundle bundle;
    private Character character;

    private List<CharacterRelation> listItems;
    private CharacterRelationAdapter characterRelationAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_relation);

        recyclerView = (RecyclerView) findViewById(R.id.characterRelationRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newCharacterRelation);
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

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();

        bundle = getIntent().getExtras();
        String id = bundle.getString("id", null);
        character = rma.getDB().dbSelectCharacterById(id);

        listItems = getItemsToList();

        characterRelationAdapter = new CharacterRelationAdapter(listItems, this);
        recyclerView.setAdapter(characterRelationAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<CharacterRelation> getItemsToList() {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();

        String id = bundle.getString("id", null);
        List<CharacterRelation> listRelations = rma.getDB().dbSelectAllCharacterRelationsByCharacterId(id);

        return listRelations;
    }

    public void updateItemAtPosition(int position) {

        String id = String.valueOf(characterRelationAdapter.getItem(position).getId());
        String character_id = String.valueOf(characterRelationAdapter.getItem(position).getCharacterOne().getId());

        Intent resultIntent = new Intent(this, CharacterRelationCreationActivity.class);
        resultIntent.putExtra("id", id);
        resultIntent.putExtra("character_id", character_id);
        startActivity(resultIntent);
    }

    public void deleteItemAtPosition(int position) {

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        CharacterRelation characterRelationToDelete = characterRelationAdapter.getItem(position);
        rma.getDB().dbDeleteById(characterRelationToDelete.getTableName(), String.valueOf(characterRelationToDelete.getId()));

        onResume();
    }

}
