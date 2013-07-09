package com.dmitrij.doberstein.spritfuchs;

import java.util.ArrayList;

import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

public interface MyListener {
	void setListView(ArrayList<TankstellenPosition> data);
	void setListDetailView(TankstellenPosition data);
}
