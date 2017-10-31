package stricarico.rolemanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class SettlementAdapter extends RecyclerView.Adapter {

    private List<Settlement> listItems;
    private SettlementFragment fragment;

    public SettlementAdapter(List<Settlement> listItem, SettlementFragment fragment) {
        this.listItems = listItem;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settlement_list_item, parent, false);
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

    public Settlement getItem(int position) {
        return listItems.get(position);
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewName, textViewType, textViewPopulation;
        private Button buttonUpdate, buttonDelete;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewType = itemView.findViewById(R.id.type);
            textViewPopulation = itemView.findViewById(R.id.population);

            buttonUpdate = itemView.findViewById(R.id.editButton);
            buttonDelete = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
        }

        public void bindView(final int position) {
            Settlement listItem = listItems.get(position);

            textViewName.setText(listItem.getName());

            switch (listItem.getType()) {
                case 0:
                    textViewType.setText("Aldea");
                    break;
                case 1:
                    textViewType.setText("Pueblo");
                    break;
                case 2:
                    textViewType.setText("Ciudad");
                    break;
            }

            textViewPopulation.setText(String.valueOf(position));

            buttonUpdate.setBackgroundResource(R.drawable.ic_edit_black_24dp);
            buttonDelete.setBackgroundResource(R.drawable.ic_delete_black_24dp);

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
