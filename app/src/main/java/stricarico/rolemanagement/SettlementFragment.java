package stricarico.rolemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class SettlementFragment extends Fragment {

    private List<Settlement> listItems;
    private ListAdapter listAdapter;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settlement_fragment, container, false);

        recyclerView = view.findViewById(R.id.settlementRecyclerView);

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

        listItems = getItemsToList();

        listAdapter = new ListAdapter(listItems, this);
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<Settlement> getItemsToList() {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        List<Settlement> listSettlements = rma.getDB().dbSelectAllSettlements();

        return listSettlements;
    }

    public void updateItemAtPosition(int position) {

    }

    public void deleteItemAtPosition(int position) {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();
        Settlement settlementToDelete = listAdapter.getItem(position);
        rma.getDB().dbDeleteById(settlementToDelete.getTableName(), String.valueOf(settlementToDelete.getId()));

        onResume();
    }
}
