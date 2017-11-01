package stricarico.rolemanagement;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

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
                Intent resultIntent = new Intent(getActivity(), CharacterActivity.class);
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

        characterAdapter = new CharacterAdapter(listItems, this);
        recyclerView.setAdapter(characterAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<Character> getItemsToList() {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();

        List<Character> listCharacters = rma.getDB().dbSelectAllCharacters();

        return listCharacters;
    }

    public void updateItemAtPosition(int position) {

        String id = String.valueOf(characterAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), CharacterActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }

    public void deleteItemAtPosition(int position) {

        RoleManagementApplication rma = (RoleManagementApplication)getActivity().getApplicationContext();
        Character characterToDelete = characterAdapter.getItem(position);
        rma.getDB().dbDeleteById(characterToDelete.getTableName(), String.valueOf(characterToDelete.getId()));

        onResume();
    }

    public void relateItemAtPosition(int position) {

        String id = String.valueOf(characterAdapter.getItem(position).getId());

        Intent resultIntent = new Intent(getActivity(), CharacterRelationActivity.class);
        resultIntent.putExtra("id", id);
        startActivity(resultIntent);
    }
}
