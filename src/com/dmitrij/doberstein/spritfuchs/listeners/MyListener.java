package com.dmitrij.doberstein.spritfuchs.listeners;

import java.util.ArrayList;

import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

public interface MyListener {
	void setListView(ArrayList<TankstellenPosition> data);
	void setListDetailView(ArrayList<TankstellenPosition> data);
}
