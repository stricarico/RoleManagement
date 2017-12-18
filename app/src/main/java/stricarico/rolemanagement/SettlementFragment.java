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

public class SettlementFragment extends Fragment {

    private List<Settlement> listItems;
    private SettlementAdapter settlementAdapter;
    private RecyclerView recyclerView;
    private Campaign selectedCampaign;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settlement_fragment, container, false);

        recyclerView = view.findViewById(R.id.settlementRecyclerView);
        selectedCampaign = MainActivity.rma.getSelectedCampaign();

        FloatingActionButton fab = view.findViewById(R.id.newSettlement);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(getActivity(), SettlementActivity.class);
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

        MainActivity.mainToolbar.setTitle(R.string.settlements);

        listItems = getItemsToList();

        settlementAdapter = new SettlementAdapter(listItems, this);
        recyclerView.setAdapter(settlementAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(recyclerView);
    }

    private List<Settlement> getItemsToList() {

        List<Settlement> listSettlements = MainActivity.rma.getDB()
                .dbSelectAllSettlementsByCampaign(String.valueOf(selectedCampaign.getId()));

        return listSettlements;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = settlementAdapter.getPosition();

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

        String id = String.valueOf(settlementAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), SettlementActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    private void attemptToDeleteItemAtPosition (int position) {

        Settlement settlementToDelete = settlementAdapter.getItem(position);
        String relatedCharacter = validateIfSettlementIsRelatedToACharacter(
                String.valueOf(settlementToDelete.getId()));

        if (relatedCharacter == null) {
            askToDeleteSettlement(
                    settlementToDelete.getName(),
                    settlementToDelete.getTableName(),
                    String.valueOf(settlementToDelete.getId())
            );
        }
        else {
            alertThatSettlementIsRelated(
                    settlementToDelete.getName(),
                    relatedCharacter
            );
        }
    }

    private void askToDeleteSettlement(
            final String settlementName,
            final String settlementTableName,
            final String settlementId) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Eliminar Asentamiento");
        alert.setMessage("¿Está seguro que desea eliminar el Asentamiento " + settlementName + "?");

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteSettlementById(settlementTableName, settlementId);
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

    private void alertThatSettlementIsRelated(
            final String settlementName,
            final String characterName) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Relaciones Dependientes");
        alert.setMessage("El Asentamiento "
                + settlementName +
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

    private void deleteSettlementById(String tableName, String id) {

        MainActivity.rma.getDB().dbDeleteById(tableName, id);

        onResume();
    }

    public String validateIfSettlementIsRelatedToACharacter(String id) {

        return MainActivity.rma.getDB().dbCheckIfSettlementIsRelatedToACharacter(id);
    }
}
