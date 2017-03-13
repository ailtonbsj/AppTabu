package br.com.example.apptabu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	ListView menuPrincipal;
	String opcoes[] = { "Aprender","Jogar", "Desempenho", "Sobre" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		setTitle("AppTabu");
		ArrayAdapter<String> arrayAdapter1;
		arrayAdapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, opcoes);
		menuPrincipal = (ListView) findViewById(R.id.listViewMenu);
		menuPrincipal.setAdapter(arrayAdapter1);

		OnItemClickListener evt1 = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String opcao = ((TextView) arg1).getText().toString();
				if (opcao.equals("Jogar")) {
					Intent it = new Intent(HomeActivity.this,JogoActivity.class);
					Bundle bl = new Bundle();
					//bl.putString("param1", param1.getText().toString());
					//bl.putString("param2", param2.getText().toString());
					//it.putExtras(bl);
					startActivity(it);
				}
				else if(opcao.equals("Desempenho")){
					Intent it = new Intent(HomeActivity.this,ResultadosActivity.class);
					Bundle bl = new Bundle();
					startActivity(it);
				}
				else if(opcao.equals("Aprender")){
					Intent it = new Intent(HomeActivity.this,AprendaActivity.class);
					Bundle bl = new Bundle();
					startActivity(it);
				}
				else if(opcao.equals("Sobre")){
					alert("Programador:\nJosé Ailton B. da Silva\nAplicação e Testes:\nRegiano Monte Viana");
				}
			}
		};
		menuPrincipal.setOnItemClickListener(evt1);

	}

	public void alert(String arg0) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
		dialog.setTitle("Alerta");
		dialog.setMessage(arg0);
		dialog.setNeutralButton("OK", null);
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
