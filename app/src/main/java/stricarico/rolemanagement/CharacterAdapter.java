package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter {

    private List<Character> listItems;
    private CharacterFragment fragment;
    private int position;

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
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

        ((ListViewHolder) holder).itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public Character getItem(int position) {
        return listItems.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView textViewName, textViewSettlement, textViewProfession;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewSettlement = itemView.findViewById(R.id.settlement);
            textViewProfession = itemView.findViewById(R.id.profession);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bindView(final int position) {
            final Character listItem = listItems.get(position);

            textViewName.setText(listItem.getName());
            textViewSettlement.setText(listItem.getSettlement().getName());
            textViewProfession.setText(listItem.getProfession().getName());

            if ((position & 1) == 0)
                itemView.findViewById(R.id.characterCardView).setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater menuInflater = fragment.getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.character_context_menu, menu);
        }
    }
}
