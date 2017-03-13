package br.com.example.apptabu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AprendaActivity extends Activity {

	TextView tvLista;
	Button btVai;
	Button btVolta;
	int produto = 2;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aprenda);
		
		tvLista = (TextView) findViewById(R.id.tvLista);
		btVai  = (Button) findViewById(R.id.btVai);
		btVolta = (Button) findViewById(R.id.btVolta);
		geraListaTab(produto);
		btVai.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(produto < 9){
					produto++;
					geraListaTab(produto);
				}
			}
		});
		btVolta.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(produto > 2){
					produto--;
					geraListaTab(produto);
				}
			}
		});
	}
	
	public void geraListaTab(int v){
		int i;
		String opp = "";
		for(i = 1; i <= 10; i++){
			opp += v + " x " + i + " = " + v*i + "\n";
		}
		tvLista.setText(opp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aprenda, menu);
		return true;
	}

}
