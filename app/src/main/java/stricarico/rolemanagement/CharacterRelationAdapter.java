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

public class CharacterRelationAdapter extends RecyclerView.Adapter {

    private List<CharacterRelation> listItems;
    private CharacterRelationActivity activity;
    private int position;

    public CharacterRelationAdapter(List<CharacterRelation> listItem, CharacterRelationActivity activity) {
        this.listItems = listItem;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_relation_list_item, parent, false);
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

    public CharacterRelation getItem(int position) {
        return listItems.get(position);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        private TextView textViewRelatedCharacter, textViewJudgement;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewRelatedCharacter = itemView.findViewById(R.id.relatedCharacter);
            textViewJudgement = itemView.findViewById(R.id.judgement);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bindView(final int position) {
            final CharacterRelation listItem = listItems.get(position);

            textViewRelatedCharacter.setText(listItem.getCharacterTwo().getName());
            textViewJudgement.setText(listItem.getJudgement());

            if ((position & 1) == 0)
                itemView.findViewById(R.id.characterRelationCardView).setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater menuInflater = activity.getMenuInflater();
            menuInflater.inflate(R.menu.standard_context_menu, menu);
        }
    }
}
