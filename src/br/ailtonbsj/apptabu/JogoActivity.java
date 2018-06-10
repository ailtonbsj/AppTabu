package br.ailtonbsj.apptabu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import br.ailtonbsj.apptabu.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JogoActivity extends Activity {

	Vibrator vibrator;
	Random randomico = new Random();
	Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bc, bn;
	OnClickListener evtClickBtn;
	TextView displayYou, displayConta, tempoConta;
	ArrayList<ItemMultiplicar> tab;
	ItemMultiplicar itemAtual;
	ItemNowSer itemNow;

	int a, b, res, tempoOperacao = 20;
	File arrUser;
	File itemUser;
	Handler timerHandler;
	Runnable timerRunnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jogo);
		setTitle("Jogo");

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		b0 = (Button) findViewById(R.id.button0);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		b5 = (Button) findViewById(R.id.button5);
		b6 = (Button) findViewById(R.id.button6);
		b7 = (Button) findViewById(R.id.button7);
		b8 = (Button) findViewById(R.id.button8);
		b9 = (Button) findViewById(R.id.button9);
		bc = (Button) findViewById(R.id.buttonC);
		bn = (Button) findViewById(R.id.buttonN);
		displayYou = (TextView) findViewById(R.id.displayYou);
		displayConta = (TextView) findViewById(R.id.displayConta);
		tempoConta = (TextView) findViewById(R.id.tempoConta);

		arrUser = new File(getFilesDir(), "randtabu.ser");
		itemUser = new File(getFilesDir(), "itemuser.ser");

		tab = new ArrayList<ItemMultiplicar>();
		itemNow = new ItemNowSer();

		if (!itemUser.exists()) {
			try {
				itemUser.createNewFile();
				itemNow.setItem(0);
				FileOutputStream fos2 = new FileOutputStream(itemUser);
				ObjectOutputStream os2 = new ObjectOutputStream(fos2);
				os2.writeObject(itemNow);
			} catch (Exception e) {
				alert(e.getMessage());
			}
		} else {
			try {
				FileInputStream fis2 = new FileInputStream(itemUser);
				ObjectInputStream ois2 = new ObjectInputStream(fis2);
				itemNow = (ItemNowSer) ois2.readObject();
			} catch (Exception e) {
				alert(e.getMessage());
			}
		}

		if (!arrUser.exists()) {
			// Cria
			try {
				arrUser.createNewFile();
				gerarTabuadaVezes();
				FileOutputStream fos = new FileOutputStream(arrUser);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(tab);

			} catch (Exception e) {
				alert(e.getMessage());
			}
		} else {
			// Ler
			try {
				FileInputStream fis = new FileInputStream(arrUser);
				ObjectInputStream ois = new ObjectInputStream(fis);
				tab = (ArrayList<ItemMultiplicar>) ois.readObject();
			} catch (Exception e) {
				alert(e.getMessage());
			}
		}

		gerarTela();

		b0.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "0");
			}
		});
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "1");
			}
		});
		b2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "2");
			}
		});
		b3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "3");
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "4");
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "5");
			}
		});
		b6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "6");
			}
		});
		b7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "7");
			}
		});
		b8.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "8");
			}
		});
		b9.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText(displayYou.getText() + "9");
			}
		});
		bc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				displayYou.setText("");
			}
		});

		evtClickBtn = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (displayYou.getText().toString().equals(""))
					res = 0;
				else
					res = Integer.parseInt(displayYou.getText().toString());
				int pontos = 0;
				if ((a * b) != res)
					vibrator.vibrate(500);
				else
					pontos = tempoOperacao;
				itemAtual.setPontuacao(pontos);
				tab.set(itemNow.getItem(), itemAtual);
				itemNow.addOneItem();
				salvarItemNow();

				if (itemNow.getItem() < 64) {
					gerarTela();
				} else {
					itemNow.setItem(0);
					salvarItemNow();
					Intent it = new Intent(JogoActivity.this,
							ResultadosActivity.class);
					Bundle bl = new Bundle();
					bl.putBoolean("Congratulation", true);
					// bl.putString("param2", param2.getText().toString());
					it.putExtras(bl);
					startActivity(it);
					finish();
				}
				tempoOperacao = 20;
			}
		};

		bn.setOnClickListener(evtClickBtn);

		timerHandler = new Handler();
		timerRunnable = new Runnable() {
			@Override
			public void run() {
				if (tempoOperacao > 0) {
					tempoOperacao--;
					tempoConta.setText("Tempo restante: "
							+ String.valueOf(tempoOperacao));
				} else {
					evtClickBtn.onClick(null);
					Log.v("Ai", "Click");
				}
				timerHandler.postDelayed(this, 1000);
			}
		};
		timerHandler.postDelayed(timerRunnable, 0);
		
	}

	public void salvarItemNow() {
		try {
			FileOutputStream fos2 = new FileOutputStream(itemUser);
			ObjectOutputStream os2 = new ObjectOutputStream(fos2);
			os2.writeObject(itemNow);
			FileOutputStream fos = new FileOutputStream(arrUser);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(tab);
		} catch (Exception e) {
			alert(e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jogo, menu);
		return true;
	}

	public void alert(String arg0) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(JogoActivity.this);
		dialog.setTitle("Alerta");
		dialog.setMessage(arg0);
		dialog.setNeutralButton("OK", null);
		dialog.show();
	}

	public void gerarTabuadaVezes() {
		ArrayList<ItemMultiplicar> tb = new ArrayList<ItemMultiplicar>();
		int a, b, pos = 0;
		for (a = 2; a <= 9; a++) {
			for (b = 2; b <= 9; b++) {
				tb.add(new ItemMultiplicar(pos, a, b));
				pos++;
			}
		}
		// /here
		int gerNum = tb.size();
		while (gerNum > 0) {
			int rd = randomico.nextInt(gerNum);
			tab.add(tb.get(rd));
			tb.remove(rd);
			gerNum--;
		}
	}

	public void gerarTela() {
		try {
			itemAtual = tab.get(itemNow.getItem());
			a = itemAtual.getA();
			b = itemAtual.getB();
			displayConta.setText(Integer.toString(a) + " X "
					+ Integer.toString(b) + " = ?");
			displayYou.setText("");
		} catch (Exception e) {
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timerHandler.removeCallbacks(timerRunnable);
	}

}