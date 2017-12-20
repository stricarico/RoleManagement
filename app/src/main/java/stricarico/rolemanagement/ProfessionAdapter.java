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

public class ProfessionAdapter extends RecyclerView.Adapter {

    private List<Profession> listItems;
    private ProfessionFragment fragment;
    private int position;

    public ProfessionAdapter(List<Profession> listItem, ProfessionFragment fragment) {
        this.listItems = listItem;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profession_list_item, parent, false);
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

    public Profession getItem(int position) {
        return listItems.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView textViewName, textViewDuties;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewDuties = itemView.findViewById(R.id.duties);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bindView(final int position) {
            final Profession listItem = listItems.get(position);

            textViewName.setText(listItem.getName());
            textViewDuties.setText(listItem.getDuties());

            if ((position & 1) == 0)
                itemView.findViewById(R.id.professionCardView).setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater menuInflater = fragment.getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.standard_context_menu, menu);
        }
    }
}
