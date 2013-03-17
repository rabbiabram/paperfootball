package com.rnr.paperfootball.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rnr.paperfootball.Config;
import com.rnr.paperfootball.R;
import com.rnr.paperfootball.core.PlayerBuilder;
import com.rnr.paperfootball.map.MapBuilderFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ConfigActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.config);
        
        Config.getInstance().read();

        List<String> items = new ArrayList<String>(PlayerBuilder.getPlayerTypes().values());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        Spinner spinner = (Spinner)(this.findViewById(R.id.sPlayer1Type));
        Spinner spinner2 = (Spinner)(this.findViewById(R.id.sPlayer2Type));

        spinner.setAdapter(adapter);
        spinner.setPrompt("Тип первого игрока");
        spinner.setSelection(items.indexOf(PlayerBuilder.getPlayerTypes().get(Config.getInstance().getPlayer1Type())));

        spinner2.setAdapter(adapter);
        spinner2.setPrompt("Тип второго игрока");
        spinner2.setSelection(items.indexOf(PlayerBuilder.getPlayerTypes().get(Config.getInstance().getPlayer2Type())));
        
        List<String> itemsMap = new ArrayList<String>(MapBuilderFactory.getMapTypes().values());
        ArrayAdapter<String> adapterMap = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_spinner_item, itemsMap);
        adapterMap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerMap = (Spinner)(this.findViewById(R.id.sMapType));
        spinnerMap.setAdapter(adapterMap);
        spinnerMap.setPrompt("Тип карты");
        spinnerMap.setSelection(itemsMap.indexOf(MapBuilderFactory.getMapTypes().get(Config.getInstance().getMapType())));

        ((EditText)this.findViewById(R.id.ePlayer1Name)).setText(Config.getInstance().getPlayer1Name());
        ((EditText)this.findViewById(R.id.ePlayer2Name)).setText(Config.getInstance().getPlayer2Name());
	}

	protected String getMapKey(Map<String, String> map, String value) {
		for (String key : map.keySet()) {
			if (map.get(key) == value) {
				return key;
			}
		}
		return "";
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) {
		case R.id.config_save:
			Config.getInstance().setPlayer1Type(this.getMapKey(PlayerBuilder.getPlayerTypes(), 
					((Spinner)this.findViewById(R.id.sPlayer1Type)).getSelectedItem().toString()));
			Config.getInstance().setPlayer2Type(this.getMapKey(PlayerBuilder.getPlayerTypes(), 
					((Spinner)this.findViewById(R.id.sPlayer2Type)).getSelectedItem().toString()));

			Config.getInstance().setMapType(this.getMapKey(MapBuilderFactory.getMapTypes(), 
					((Spinner)this.findViewById(R.id.sMapType)).getSelectedItem().toString()));

			Config.getInstance().setPlayer1Name(((EditText)this.findViewById(R.id.ePlayer1Name)).getText().toString());
			Config.getInstance().setPlayer2Name(((EditText)this.findViewById(R.id.ePlayer2Name)).getText().toString());
			Config.getInstance().write();
			this.finish();
			return true;
		case R.id.config_close:
			this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.config, menu);
        return true;    
    }
}
