package com.dmitrij.doberstein.spritfuchs.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmitrij.doberstein.spritfuchs.R;
import com.dmitrij.doberstein.spritfuchs.SortePreis;
import com.dmitrij.doberstein.spritfuchs.dataclasses.TankstellenPosition;

public class CustomListAdapter extends BaseAdapter {
	 
    private ArrayList<TankstellenPosition> listData;
 
    private LayoutInflater layoutInflater;
    
    private Context cont;
 
    public CustomListAdapter(Context context, ArrayList<TankstellenPosition> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        cont = context;
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
            holder.tankstelleLDiesel = (TextView)convertView.findViewById(R.id.tvLdiesel);
            
            
            
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.cont);
            String strKraftstoff = prefs.getString("prefKraftstoff", "1");
            int intKraftstoff = 1;
            try {
				intKraftstoff = Integer.parseInt(strKraftstoff);
			} 
            catch (Exception e) {
			}
            

    		holder.tankstelleDiesel.setTypeface(null, Typeface.BOLD);
    		holder.tankstelleLDiesel.setTypeface(null,Typeface.BOLD);          
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
        			holder.tankstelleLDiesel.setText("E5");
        			holder.tankstelleDiesel.setText("" + sp.getPreis() + " €");
        			break;
        		// E10
        		case 2:
        			holder.tankstelleLDiesel.setText("E10");
        			holder.tankstelleDiesel.setText("" + sp.getPreis() + " €");
        			break;
        		// Diesel
        		case 3:
        			holder.tankstelleLDiesel.setText("Diesel");
        			holder.tankstelleDiesel.setText("" + sp.getPreis() + " €");
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
        TextView tankstelleLDiesel;
        
    }
 
}
