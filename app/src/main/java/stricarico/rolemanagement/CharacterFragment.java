package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class CharacterFragment extends Fragment {

    private RoleManagementApplication rma;

    private List<Character> listItems;
    private CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);

        rma = (RoleManagementApplication)getActivity().getApplicationContext();
        recyclerView = view.findViewById(R.id.characterRecyclerView);

        FloatingActionButton fab = view.findViewById(R.id.newCharacter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 if (validateIfThereIsAnySettlement()) {
                     if (validateIfThereIsAnyProfession()) {

                         Intent resultIntent = new Intent(getActivity(), CharacterActivity.class);
                         startActivity(resultIntent);
                     }
                     else {

                         AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                         alert.setTitle("Relaciones Dependientes");
                         alert.setMessage("No se puede crear un nuevo Personaje sin haber creado al menos un Oficio");
                         alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                             }
                         });
                         alert.show();
                     }
                 } else {

                     AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                     alert.setTitle("Relaciones Dependientes");
                     alert.setMessage("No se puede crear un nuevo Personaje sin haber creado al menos un Asentamiento");
                     alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                         }
                     });
                     alert.show();
                 }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity.mainToolbar.setTitle(R.string.characters);

        listItems = rma.getDB().dbSelectAllCharacters();

        characterAdapter = new CharacterAdapter(listItems, this);
        recyclerView.setAdapter(characterAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = characterAdapter.getPosition();

        switch (item.getItemId()) {

            case R.id.relationships:
                break;
            case R.id.edit:
                break;
            case R.id.delete:
                attemptToDeleteItemAtPosition(position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    public void updateItemAtPosition(int position) {

        String id = String.valueOf(characterAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), CharacterActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    private void attemptToDeleteItemAtPosition (int position) {

        Character characterToDelete = characterAdapter.getItem(position);
        String relatedCharacter = validateIfCharacterIsRelatedToAnotherCharacter(
                String.valueOf(characterToDelete.getId()));

        if (relatedCharacter == null) {
            askToDeleteCharacter(
                    characterToDelete.getName(),
                    characterToDelete.getTableName(),
                    String.valueOf(characterToDelete.getId())
            );
        }
        else {
            alertThatCharacterIsRelated(
                    characterToDelete.getName(),
                    relatedCharacter
            );
        }
    }

    private void askToDeleteCharacter(
            final String characterName,
            final String characterTableName,
            final String characterId) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Eliminar Personaje");
        alert.setMessage("¿Está seguro que desea eliminar el Personaje " + characterName + "?");

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteCharacterById(characterTableName, characterId);
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

    private void alertThatCharacterIsRelated(
            final String characterName,
            final String relatedCharacterName) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Relaciones Dependientes");
        alert.setMessage("El Personaje "
                + characterName
                + " tiene una relación creada con el Personaje "
                + relatedCharacterName
                + " y no se puede eliminar");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    private void deleteCharacterById(String tableName, String id) {

        rma.getDB().dbDeleteById(tableName, id);

        onResume();
    }

    public void relateItemAtPosition(int position) {

        String id = String.valueOf(characterAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), CharacterRelationActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    private boolean validateIfThereIsAnySettlement() {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        return rma.getDB().dbCheckIfThereIsAnySettlement();
    }

    private boolean validateIfThereIsAnyProfession() {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        return rma.getDB().dbCheckIfThereIsAnyProfession();
    }

    public boolean validateIfThereIsAnyOtherCharacter(String id) {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        return rma.getDB().dbCheckIfThereIsAnyOtherCharacter(id);
    }

    public String validateIfCharacterIsRelatedToAnotherCharacter(String id) {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        return rma.getDB().dbCheckIfCharacterIsRelatedToAnotherCharacter(id);
    }
}
