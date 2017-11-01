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
            final Character listItem = listItems.get(position);

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

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

                    String character = fragment.validateIfCharacterIsRelatedToAnotherCharacter(String.valueOf(listItem.getId()));
                    if (character == null) {

                        alert.setTitle("Eliminar Personaje");
                        alert.setMessage("¿Está seguro que desea eliminar el Personaje " + listItem.getName() + "?");
                        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                fragment.deleteItemAtPosition(position);
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
                    else {
                        alert.setTitle("Relaciones Dependientes");
                        alert.setMessage("El Personaje " + listItem.getName() + " tiene una relación creada con el Personaje " + character + " y no se puede eliminar");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alert.show();
                    }
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

        public void onClick(View view) {
        }
    }
}
