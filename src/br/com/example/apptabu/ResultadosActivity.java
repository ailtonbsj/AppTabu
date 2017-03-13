package br.com.example.apptabu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ResultadosActivity extends Activity {

	ArrayList<ItemMultiplicar> tab, org;
	ItemMultiplicar itemAtual;
	File arrUser;
	LinearLayout l,lbt;
	RadioButton rdBar, rdLine, rdRadar;
	int typeGraph = 0;
	boolean stageClear = false;
	private ArrayList<BarChart> ptBc;
	private ArrayList<LineChart> ptLc;
	private ArrayList<RadarChart> ptRc;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resultados);
		setTitle("Resultados");
		l = (LinearLayout) findViewById(R.id.linearLayout1);
		lbt = (LinearLayout) findViewById(R.id.radioGroup1);
		rdBar = (RadioButton) findViewById(R.id.rdBar);
		rdLine = (RadioButton) findViewById(R.id.rdLine);
		rdRadar = (RadioButton) findViewById(R.id.rdRadar);

		rdBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeGraph = 0;
				rdLine.setChecked(false);
				rdRadar.setChecked(false);
				gerarViewsGraphs();
			}
		});
		rdLine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeGraph = 1;
				rdBar.setChecked(false);
				rdRadar.setChecked(false);
				gerarViewsGraphs();
			}
		});
		rdRadar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				typeGraph = 2;
				rdBar.setChecked(false);
				rdLine.setChecked(false);
				gerarViewsGraphs();
			}
		});

		Intent it = getIntent();
		if (it != null) {
			Bundle bl = it.getExtras();
			if (bl != null) {
				if (bl.getBoolean("Congratulation")) {
					alert("Parabéns!");
					stageClear = true;
				}
			}
		}

		org = new ArrayList<ItemMultiplicar>();
		try {
			arrUser = new File(getFilesDir(), "randtabu.ser");
			FileInputStream fis = new FileInputStream(arrUser);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tab = (ArrayList<ItemMultiplicar>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			alert(e.getMessage());
		}

		int i, j;
		for (i = 0; i < 64; i++) {
			j = 0;
			while (tab.get(j).getPosicao() != i)
				j++;
			org.add(tab.get(j));
		}

		// /
		if (stageClear) {
			File file = new File(Environment.getExternalStorageDirectory(),
					getNewId() + ".csv");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					alert("Não foi possivel criar save");
				}
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				for (ItemMultiplicar imp : org) {
					String str1;
					String str2 = imp.getPontuacao() + "";
					str1 = imp.getTextOperacao() + ";" + str2.replace('.', ',');
					fos.write((String.valueOf(str1) + "\n").getBytes());
				}
				fos.flush();
				fos.close();
			} catch (IOException e) {
				alert("Falha de escrita em save");
			}
		}
		ptBc = new ArrayList<BarChart>();
		ptLc = new ArrayList<LineChart>();
		ptRc = new ArrayList<RadarChart>();
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 350);
		

		int index, contTab = 0;
		for (index = 0; index < 8; index++) {

			BarChart bc = new BarChart(this);
	        bc.setLayoutParams(lp);
	        ptBc.add(index,bc);
	        ArrayList<BarEntry> yValsBc = new ArrayList<BarEntry>();
	        ArrayList<String> xValsBc = new ArrayList<String>();
	        
	        LineChart lc = new LineChart(this);
	        lc.setLayoutParams(lp);
	        ptLc.add(index, lc);
	        ArrayList<Entry> yValsLc = new ArrayList<Entry>();
	        
	        RadarChart rc = new RadarChart(this);
	        rc.setLayoutParams(lp);
	        ptRc.add(index, rc);
			
			int a;
			for (a = 0; a < 8; a++) {
				itemAtual = org.get(contTab);
				yValsBc.add(new BarEntry(itemAtual.getPontuacao(), a));
				yValsLc.add(new Entry(itemAtual.getPontuacao(), a));
				xValsBc.add(itemAtual.getTextOperacao());
				
				contTab++;
			}
			
	        BarDataSet bds = new BarDataSet(yValsBc, "Tabuada de " + (index+2));
	        bds.setColor(Color.rgb(135, 61, 135));
	        ArrayList<BarDataSet> datasetsBc = new ArrayList<BarDataSet>();
	        datasetsBc.add(bds);
	        BarData dataBc =  new BarData(xValsBc,bds);
	        bc.setData(dataBc);
	        
	        LineDataSet lds = new LineDataSet(yValsLc, "Tabuada de " + (index+2));
	        lds.setColor(Color.rgb(135, 61, 135));
	        lds.setCircleColor(Color.rgb(135, 61, 135));
	        ArrayList<LineDataSet> datasetsLc = new ArrayList<LineDataSet>();
	        datasetsLc.add(lds);
	        LineData dataLc =  new LineData(xValsBc, datasetsLc);
	        lc.setData(dataLc);
	        
	        RadarDataSet setRd = new RadarDataSet(yValsLc, "Tabuada de " + (index+2));
	        setRd.setColor(Color.rgb(135, 61, 135));
	        ArrayList<RadarDataSet> datasetsRd = new ArrayList<RadarDataSet>();
	        datasetsRd.add(setRd);
	        RadarData dataRd =  new RadarData(xValsBc, datasetsRd);
	        rc.setData(dataRd);
			
		}

		gerarViewsGraphs();
	}

	public void gerarViewsGraphs() {
		l.removeAllViews();
		l.addView(lbt);
		if(typeGraph == 0){
			int i = 0;
			for(BarChart it : ptBc){
				TextView tv = new TextView(this);
				tv.setText("Tabuada de " + (i+2));
				i++;
				l.addView(tv);
				l.addView(it);
			}
		}
		else if(typeGraph == 1){
			int i = 0;
			for(LineChart it : ptLc){
				TextView tv = new TextView(this);
				tv.setText("Tabuada de " + (i+2));
				i++;
				l.addView(tv);
				l.addView(it);
			}
		}
		else if(typeGraph == 2){
			int i = 0;
			for(RadarChart it : ptRc){
				TextView tv = new TextView(this);
				tv.setText("Tabuada de " + (i+2));
				i++;
				l.addView(tv);
				l.addView(it);
			}
		}
	}

	public void alert(String arg0) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				ResultadosActivity.this);
		dialog.setTitle("Alerta");
		dialog.setMessage(arg0);
		dialog.setNeutralButton("OK", null);
		dialog.show();
	}

	public static String getNewId() {
		Calendar cld = Calendar.getInstance();
		int ano = cld.get(Calendar.YEAR);
		int mes = cld.get(Calendar.MONTH);
		int dia = cld.get(Calendar.DATE);
		int hor = cld.get(Calendar.HOUR);
		int min = cld.get(Calendar.MINUTE);
		int sec = cld.get(Calendar.SECOND);
		int mil = cld.get(Calendar.MILLISECOND);

		return ano + "." + mes + "." + dia + "." + hor + "." + min + "." + sec
				+ "." + mil;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.resultados, menu);
		return true;
	}

}
