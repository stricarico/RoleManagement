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

import static stricarico.rolemanagement.MainActivity.rma;

public class CharacterFragment extends Fragment {

    private List<Character> listItems;
    private CharacterAdapter characterAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.character_fragment, container, false);

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
                     else alertThatThereIsNoProfessionCreated();
                 } else alertThatThereIsNoSettlementCreated();
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

        listItems = rma.getDB()
                .dbSelectAllCharactersByCampaign(
                        String.valueOf(rma.getSelectedCampaign().getId()));

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
                attemptToRelateItemAtPosition(position);
                break;
            case R.id.edit:
                updateItemAtPosition(position);
                break;
            case R.id.delete:
                attemptToDeleteItemAtPosition(position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void attemptToRelateItemAtPosition(int position) {

        Character characterToRelate = characterAdapter.getItem(position);
        String characterToRelateId = String.valueOf(characterToRelate.getId());

        if (validateIfThereIsAnyOtherCharacter(characterToRelateId))
            relateItemById(characterToRelateId);
        else alertThatThereIsNoOtherCharacterCreated();
    }

    private void alertThatThereIsNoOtherCharacterCreated() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Relaciones Dependientes");
        alert.setMessage("No se puede relacionar este Personaje con otro," +
                " dado que aún no se ha creado otro Personaje con el cual relacionarlo");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    private void relateItemById(String characterId) {

        Intent resultIntent = new Intent(getActivity(), CharacterRelationActivity.class);
        resultIntent.putExtra("id", characterId);
        startActivity(resultIntent);
    }

    private void updateItemAtPosition(int position) {

        String id = String.valueOf(characterAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), CharacterActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    private void attemptToDeleteItemAtPosition(int position) {

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

    private void alertThatThereIsNoProfessionCreated() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Relaciones Dependientes");
        alert.setMessage("No se puede crear un nuevo Personaje sin haber creado al menos un Oficio");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    private void alertThatThereIsNoSettlementCreated() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Relaciones Dependientes");
        alert.setMessage("No se puede crear un nuevo Personaje sin haber creado al menos un Asentamiento");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

    private boolean validateIfThereIsAnySettlement() {

        return rma.getDB().dbCheckIfThereIsAnySettlementByCampaign(String.valueOf(rma.getSelectedCampaign().getId()));
    }

    private boolean validateIfThereIsAnyProfession() {

        return rma.getDB().dbCheckIfThereIsAnyProfessionByCampaign(String.valueOf(rma.getSelectedCampaign().getId()));
    }

    private boolean validateIfThereIsAnyOtherCharacter(String id) {

        return rma.getDB().dbCheckIfThereIsAnyOtherCharacterByCampaign(id, String.valueOf(rma.getSelectedCampaign().getId()));
    }

    private String validateIfCharacterIsRelatedToAnotherCharacter(String id) {

        return rma.getDB().dbCheckIfCharacterIsRelatedToAnotherCharacter(id);
    }
}
