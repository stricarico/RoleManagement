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

import java.util.ArrayList;
import java.util.List;

public class SettlementFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settlement_fragment, container, false);

        List<ListItem> listItems = new ArrayList<>();

        for(int i=0; i<=10; i++) {
            ListItem listItem = new ListItem(
                    "Settlement " + i+1,
                    "Type",
                    "Population is " + (i+1)*1000
            );

            listItems.add(listItem);
        }

        RecyclerView recyclerView = view.findViewById(R.id.settlementRecyclerView);
        ListAdapter listAdapter = new ListAdapter(listItems, this.getContext());
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

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
}
