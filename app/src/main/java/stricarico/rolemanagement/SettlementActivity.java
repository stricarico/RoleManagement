package stricarico.rolemanagement;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettlementActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);

        spinner = (Spinner) findViewById(R.id.type);
        adapter = ArrayAdapter.createFromResource(this, R.array.type_names, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText etName = (EditText) findViewById(R.id.name);
        final EditText etPopulation = (EditText) findViewById(R.id.population);

        Button okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                int type = spinner.getSelectedItemPosition();
                int population = Integer.parseInt(etPopulation.getText().toString());

                saveSettlement(name, type, population);
            }
        });
    }

    private void saveSettlement(String name, int type, int population) {
        Settlement settlement = new Settlement(name, type, population);

        RoleManagementApplication rma = (RoleManagementApplication)getApplicationContext();
        rma.getDB().dbInsert(settlement);
    }

}
