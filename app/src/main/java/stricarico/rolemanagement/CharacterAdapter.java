package stricarico.rolemanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter {

    private List<Character> listItems;
    private CharacterFragment fragment;

    public CharacterAdapter(List<Character> listItem, CharacterFragment fragment) {
        this.listItems = listItem;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public Character getItem(int position) {
        return listItems.get(position);
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewName, textViewSettlement, textViewProfession;
        private Button buttonUpdate, buttonDelete, buttonRelate;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewSettlement = itemView.findViewById(R.id.settlement);
            textViewProfession = itemView.findViewById(R.id.profession);

            buttonUpdate = itemView.findViewById(R.id.editButton);
            buttonDelete = itemView.findViewById(R.id.deleteButton);
            buttonRelate = itemView.findViewById(R.id.relateButton);

            itemView.setOnClickListener(this);
        }

        public void bindView(final int position) {
            Character listItem = listItems.get(position);

            textViewName.setText(listItem.getName());
            textViewSettlement.setText(listItem.getSettlement().getName().toString());
            textViewProfession.setText(listItem.getProfession().getName().toString());

            buttonUpdate.setBackgroundResource(R.drawable.ic_edit_black_24dp);
            buttonDelete.setBackgroundResource(R.drawable.ic_delete_black_24dp);
            buttonRelate.setBackgroundResource(R.drawable.ic_character_relation_black_24dp);

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.updateItemAtPosition(position);
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.deleteItemAtPosition(position);
                    listItems.remove(position);
                }
            });
        }

        public void onClick(View view) {
        }
    }
}
