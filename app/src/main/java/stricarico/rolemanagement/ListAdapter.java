package stricarico.rolemanagement;

import android.support.v4.app.Fragment;;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private List<Settlement> listItems;
    private SettlementFragment fragment;

    public ListAdapter(List<Settlement> listItem, SettlementFragment fragment) {
        this.listItems = listItem;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
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

            textViewName = (TextView) itemView.findViewById(R.id.name);
            textViewType = (TextView) itemView.findViewById(R.id.type);
            textViewPopulation = (TextView) itemView.findViewById(R.id.population);

            buttonUpdate = (Button) itemView.findViewById(R.id.editButton);
            buttonDelete = (Button) itemView.findViewById(R.id.deleteButton);

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

            //textViewType.setText(listItem.getType());
            textViewPopulation.setText(String.valueOf(position));

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.deleteItemAtPosition(position);
                    listItems.remove(position);
                }
            });

            //buttonEdit.setBackground();
            //buttonDelete.setBackground();
        }

        public void onClick(View view) {
        }
    }
}
