package com.dmitrij.doberstein.spritfuchs.dataclasses;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmitrij.doberstein.spritfuchs.R;
import com.dmitrij.doberstein.spritfuchs.SortePreis;

public class CustomListAdapter extends BaseAdapter {
	 
    private ArrayList<TankstellenPosition> listData;
 
    private LayoutInflater layoutInflater;
 
    public CustomListAdapter(Context context, ArrayList<TankstellenPosition> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }
 
    @Override
    public int getCount() {
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            holder = new ViewHolder();
            holder.tankstelleName = (TextView) convertView.findViewById(R.id.tvTname);
            holder.tankstelleStrasseHausnr = (TextView) convertView.findViewById(R.id.tvTstrasseHausnr);
            holder.tankstellePlzOrt = (TextView) convertView.findViewById(R.id.tvTplzOrt);
            holder.tankstelleEntfernung = (TextView) convertView.findViewById(R.id.tvTentfernung);
            holder.tankstelleDiesel = (TextView) convertView.findViewById(R.id.tvTdiesel);
            holder.tankstelleE5 = (TextView) convertView.findViewById(R.id.tvTe5);
            holder.tankstelleE10 = (TextView) convertView.findViewById(R.id.tvTe10);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TankstellenPosition tp = (TankstellenPosition)listData.get(position);
        holder.tankstelleName.setText(tp.getTankstelleName());
        holder.tankstelleStrasseHausnr.setText(tp.getTankstelleStrasse() + " " + tp.getTankstelleHausnr());
        holder.tankstellePlzOrt.setText(tp.getTankstellePlz() + " " + tp.getTankstelleOrt());
        
        holder.tankstelleEntfernung.setText("Entfernung: " + tp.getTankstelleEntfernung());
        
        List<SortePreis> asp = tp.getTankstellePreise();
        if(asp != null){
        	for(int i = 0; i < asp.size(); i++){
        		SortePreis sp = asp.get(i);
        		switch(Integer.parseInt(sp.getSorteId())){
        		// E5
        		case 1:
        			holder.tankstelleE5.setText("E5: " + sp.getPreis() + " €");
        			break;
        		// E10
        		case 2:
        			holder.tankstelleE10.setText("E10: " + sp.getPreis() + " €");
        			break;
        		// Diesel
        		case 3:
        			holder.tankstelleDiesel.setText("Diesel: " + sp.getPreis() + " €");
        			break;
        		default:
        			break;
        		}
        	}
        }
        return convertView;
    }
 
    static class ViewHolder {
        TextView tankstelleName;
        TextView tankstelleStrasseHausnr;
        TextView tankstellePlzOrt;
        TextView tankstelleEntfernung;
        TextView tankstelleDiesel;
        TextView tankstelleE5;
        TextView tankstelleE10;
        
    }
 
}
