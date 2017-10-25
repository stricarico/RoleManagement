package stricarico.rolemanagement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    private List<ListItem> listItems;
    private Context context;

    public ListAdapter(List<ListItem> listItem, Context context) {
        this.listItems = listItem;
        this.context = context;
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

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewName, textViewType, textViewPopulation;

        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.name);
            textViewType = (TextView) itemView.findViewById(R.id.type);
            textViewPopulation = (TextView) itemView.findViewById(R.id.population);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position) {
            ListItem listItem = listItems.get(position);

            textViewName.setText(listItem.getName());
            textViewType.setText(listItem.getType());
            textViewPopulation.setText(listItem.getPopulation());
        }

        public void onClick(View view) {
        }
    }
}
