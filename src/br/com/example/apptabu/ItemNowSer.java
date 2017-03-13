package br.com.example.apptabu;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ItemNowSer implements Serializable {
	private int item;

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}
	
	public void addOneItem(){
		this.item++;
	}
}
