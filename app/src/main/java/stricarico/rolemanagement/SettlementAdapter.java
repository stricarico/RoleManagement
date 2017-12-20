package stricarico.rolemanagement;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SettlementAdapter extends RecyclerView.Adapter {

    private List<Settlement> listItems;
    private SettlementFragment fragment;
    private int position;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        ((ListViewHolder) holder).bindView(position);
        ((ListViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public Settlement getItem(int position) {
        return listItems.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView textViewName, textViewType, textViewPopulation;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewType = itemView.findViewById(R.id.type);
            textViewPopulation = itemView.findViewById(R.id.population);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bindView(final int position) {
            final Settlement listItem = listItems.get(position);

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

            textViewPopulation.setText(String.valueOf(listItem.getPopulation()) + " habitantes");

            if ((position & 1) == 0)
                itemView.findViewById(R.id.settlementCardView).setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater menuInflater = fragment.getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.standard_context_menu, menu);
        }
    }
}
