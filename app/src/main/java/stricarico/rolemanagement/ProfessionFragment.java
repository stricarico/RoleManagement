package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static stricarico.rolemanagement.MainActivity.rma;

public class ProfessionFragment extends Fragment {

    private ProfessionAdapter professionAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profession_fragment, container, false);

        recyclerView = view.findViewById(R.id.professionRecyclerView);

        FloatingActionButton fab = view.findViewById(R.id.newProfession);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(getActivity(), ProfessionActivity.class);
                startActivity(resultIntent);
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

        MainActivity.mainToolbar.setTitle(R.string.professions);

        List<Profession> listItems = getItemsToList();

        professionAdapter = new ProfessionAdapter(listItems, this);
        recyclerView.setAdapter(professionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(recyclerView);
    }

    private List<Profession> getItemsToList() {

        return rma.getDB()
                .dbSelectAllProfessionsByCampaign(
                        String.valueOf(rma.getSelectedCampaign().getId()));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = professionAdapter.getPosition();

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

    public void updateItemAtPosition(int position) {

        String id = String.valueOf(professionAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), ProfessionActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    private void attemptToDeleteItemAtPosition (int position) {

        Profession professionToDelete = professionAdapter.getItem(position);
        String relatedCharacter = validateIfProfessionIsRelatedToACharacter(
                String.valueOf(professionToDelete.getId()));

        if (relatedCharacter == null) {
            askToDeleteProfession(
                    professionToDelete.getName(),
                    professionToDelete.getTableName(),
                    String.valueOf(professionToDelete.getId())
            );
        }
        else {
            alertThatProfessionIsRelated(
                    professionToDelete.getName(),
                    relatedCharacter
            );
        }
    }

    private void askToDeleteProfession(
            final String professionName,
            final String professionTableName,
            final String professionId) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Eliminar Oficio");
        alert.setMessage("¿Está seguro que desea eliminar el Oficio " + professionName + "?");

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteProfessionById(professionTableName, professionId);
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

    private void alertThatProfessionIsRelated(
            final String professionName,
            final String characterName) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Relaciones Dependientes");
        alert.setMessage("El Oficio "
                + professionName +
                " está relacionado con el Personaje "
                + characterName +
                " y no se puede eliminar");

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    private void deleteProfessionById(String tableName, String id) {

        rma.getDB().dbDeleteById(tableName, id);

        onResume();
    }

    public String validateIfProfessionIsRelatedToACharacter(String id) {

        return rma.getDB().dbCheckIfProfessionIsRelatedToACharacter(id);
    }
}
