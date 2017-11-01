package stricarico.rolemanagement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CharacterSettlementAdapter extends ArrayAdapter<Settlement> {

    private Context context;
    private List<Settlement> listItems;

    public CharacterSettlementAdapter(Context context, int textViewResourceId, List<Settlement> listItems) {
        super(context, textViewResourceId, listItems);
        this.context = context;
        this.listItems = listItems;
    }

    public int getPosition(Long id) {

        int position = -1;

        for (int i=0; i<listItems.size(); i++) {
            if (listItems.get(i).getId().equals(id)) {
                position = i;
                break;
            }
        }

        return position;
    }

    @Override
    public int getCount(){
        return listItems.size();
    }

    @Override
    public Settlement getItem(int position){
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView label = new TextView(context);
        label.setText(listItems.get(position).getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        TextView label = new TextView(context);
        label.setText(listItems.get(position).getName());

        return label;
    }
}
