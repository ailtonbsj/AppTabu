package br.ailtonbsj.apptabu;

import java.io.Serializable;
import java.math.RoundingMode;

import android.text.TextUtils.TruncateAt;

public class ItemMultiplicar implements Serializable {
	public int a, b, pontuacao = 0, posicao;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
	public float getPontuacao() {
		return Math.round(100.0*(this.pontuacao/19.0));
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	public String getTextOperacao(){
		return String.valueOf(a) + "X" + String.valueOf(b);
	}

	public ItemMultiplicar(int pos, int a, int b) {
		this.a = a;
		this.b = b;
		this.posicao = pos;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
}
