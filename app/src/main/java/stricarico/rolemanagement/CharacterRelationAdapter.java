package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class CharacterRelationAdapter extends RecyclerView.Adapter {

    private List<CharacterRelation> listItems;
    private CharacterRelationActivity activity;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public CharacterRelation getItem(int position) {
        return listItems.get(position);
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewRelatedCharacter, textViewJudgement;
        private Button buttonUpdate, buttonDelete;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewRelatedCharacter = itemView.findViewById(R.id.relatedCharacter);
            textViewJudgement = itemView.findViewById(R.id.judgement);

            buttonUpdate = itemView.findViewById(R.id.editButton);
            buttonDelete = itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
        }

        public void bindView(final int position) {
            final CharacterRelation listItem = listItems.get(position);

            textViewRelatedCharacter.setText(listItem.getCharacterTwo().getName());
            textViewJudgement.setText(listItem.getJudgement());

            buttonUpdate.setBackgroundResource(R.drawable.ic_edit_black_24dp);
            buttonDelete.setBackgroundResource(R.drawable.ic_delete_black_24dp);

            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.updateItemAtPosition(position);
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                    alert.setTitle("Eliminar Relación entre Personajes");
                    alert.setMessage("¿Está seguro que desea eliminar la relación entre el Personaje " + listItem.getCharacterOne().getName() + " y el Personaje " + listItem.getCharacterTwo().getName() + "?");
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            activity.deleteItemAtPosition(position);
                            listItems.remove(position);
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.show();
                }
            });
        }

        public void onClick(View view) {
        }
    }
}
