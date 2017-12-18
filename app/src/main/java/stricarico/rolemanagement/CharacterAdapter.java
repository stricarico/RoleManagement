package stricarico.rolemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        private Button buttonUpdate, buttonDelete, buttonRelate;


        public ListViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            textViewSettlement = itemView.findViewById(R.id.settlement);
            textViewProfession = itemView.findViewById(R.id.profession);

            buttonUpdate = itemView.findViewById(R.id.editButton);
            buttonDelete = itemView.findViewById(R.id.deleteButton);
            buttonRelate = itemView.findViewById(R.id.relateButton);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void bindView(final int position) {
            final Character listItem = listItems.get(position);

            textViewName.setText(listItem.getName());
            textViewSettlement.setText(listItem.getSettlement().getName());
            textViewProfession.setText(listItem.getProfession().getName());

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

                }
            });

            buttonRelate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (fragment.validateIfThereIsAnyOtherCharacter(String.valueOf(listItem.getId()))) {

                        fragment.relateItemAtPosition(position);
                    }
                    else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setTitle("Relaciones Dependientes");
                        alert.setMessage("No se puede relacionar este Personaje con otro, dado que aún no se ha creado otro Personaje con el cual relacionarlo");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.show();
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater menuInflater = fragment.getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.character_context_menu, menu);
        }
    }
}
