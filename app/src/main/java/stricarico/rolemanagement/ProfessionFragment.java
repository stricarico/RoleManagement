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

public class ProfessionFragment extends Fragment {

    private List<Profession> listItems;
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

        listItems = getItemsToList();

        professionAdapter = new ProfessionAdapter(listItems, this);
        recyclerView.setAdapter(professionAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<Profession> getItemsToList() {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        List<Profession> listProfessions = rma.getDB().dbSelectAllProfessions();

        return listProfessions;
    }

    public void updateItemAtPosition(int position) {

        String id = String.valueOf(professionAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), ProfessionActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    public void deleteItemAtPosition(int position) {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();
        Profession professionToDelete = professionAdapter.getItem(position);
        rma.getDB().dbDeleteById(professionToDelete.getTableName(), String.valueOf(professionToDelete.getId()));

        onResume();
    }
}
