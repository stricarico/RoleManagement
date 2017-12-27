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

public class CampaignFragment extends Fragment {

    private CampaignAdapter campaignAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.campaign_fragment, container, false);
        recyclerView = view.findViewById(R.id.campaignRecyclerView);

        FloatingActionButton fab = view.findViewById(R.id.newCampaign);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Intent resultIntent = new Intent(getActivity(), CampaignActivity.class);
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

        MainActivity.mainToolbar.setTitle(R.string.campaigns);

        List<Campaign> listItems = rma.getDB().dbSelectAllCampaigns();

        campaignAdapter = new CampaignAdapter(listItems, this);
        recyclerView.setAdapter(campaignAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = campaignAdapter.getPosition();

        switch (item.getItemId()) {

            case R.id.select:
                selectItemAtPosition(position);
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

    public void selectItemAtPosition(int position) {

        Campaign selectedCampaign = campaignAdapter.getItem(position);
        rma.setSelectedCampaign(selectedCampaign.getId());

        onResume();
    }

    public void updateItemAtPosition(int position) {

        String id = String.valueOf(campaignAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), CampaignActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    private void attemptToDeleteItemAtPosition (int position) {

        Campaign campaignToDelete = campaignAdapter.getItem(position);

        askToDeleteCampaign(
                campaignToDelete.getName(),
                campaignToDelete.getTableName(),
                String.valueOf(campaignToDelete.getId())
        );
    }

    private void askToDeleteCampaign(
            final String campaignName,
            final String campaignTableName,
            final String campaignId) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("Eliminar Campaña");
        alert.setMessage("¿Está seguro que desea eliminar la Campaña " + campaignName + "?");

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                deleteCampaignById(campaignTableName, campaignId);
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

    private void deleteCampaignById(String tableName, String id) {

        rma.deleteSelectedCampaignSharedPreferences();
        rma.getDB().dbDeleteById(tableName, id);

        onResume();
    }
}
