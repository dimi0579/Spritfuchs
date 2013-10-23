package com.dmitrij.doberstein.spritfuchs.dataclasses;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.dmitrij.doberstein.spritfuchs.R;
import com.dmitrij.doberstein.spritfuchs.SortePreis;

public class CustomListAdapterNew extends BaseAdapter {
	 
    private ArrayList<TankstellenPosition> listData;
 
    private LayoutInflater layoutInflater;
    
    private Context cont;
 
    public CustomListAdapterNew(Context context, ArrayList<TankstellenPosition> listData) {
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
        	// altes layout
//            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            convertView = layoutInflater.inflate(R.layout.list_row_layout_new, null);
            
            holder = new ViewHolder();
            try {
				holder.ivMarke = (ImageView) convertView.findViewById(R.id.ivMarke);
				holder.tvTName = (TextView) convertView.findViewById(R.id.tvTName);
				holder.tvTAdresse = (TextView) convertView.findViewById(R.id.tvTAdresse);
				holder.tvTPreis = (TextView) convertView.findViewById(R.id.tvTPreis);
				holder.tvTSpritTimeEntf = (TextView) convertView.findViewById(R.id.tvTSpritTimeEntf);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.cont);
            String strKraftstoff = prefs.getString("prefKraftstoff", "1");
            int intKraftstoff = 1;
            try {
				intKraftstoff = Integer.parseInt(strKraftstoff);
			} 
            catch (Exception e) {
			}
            

//    		holder.tankstelleDiesel.setTypeface(null, Typeface.BOLD);
//    		holder.tankstelleLDiesel.setTypeface(null,Typeface.BOLD);          
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        
//    	ImageView ivMarke;
//        TextView tvTName;
//        TextView tvTAdresse;
//        TextView tvTPreis;
//        TextView tvTSpritTimeEntf;
        
        
        TankstellenPosition tp = (TankstellenPosition)listData.get(position);
//        holder.ivMarke
        // Tankstellenname
        holder.tvTName.setText(tp.getTankstelleName());
        // Tankstellenadresse
        String adresse = tp.getTankstelleStrasse() + " " + tp.getTankstelleHausnr() + "\n" + 
        		tp.getTankstellePlz() + " " + tp.getTankstelleOrt();
        holder.tvTAdresse.setText(adresse);
        
        // Preis
        String sorteTimeEntf = "";
        List<SortePreis> asp = tp.getTankstellePreise();
        if(asp != null){
        	for(int i = 0; i < asp.size(); i++){
        		SortePreis sp = asp.get(i);
        		switch(Integer.parseInt(sp.getSorteId())){
        		// E5
        		case 1:
        			sorteTimeEntf += "E5\n";
//        			holder.tankstelleLDiesel.setText("E5");
//        			holder.tankstelleDiesel.setText("" + sp.getPreis() + " €");
        			break;
        		// E10
        		case 2:
        			sorteTimeEntf += "E10\n";
//        			holder.tankstelleLDiesel.setText("E10");
//        			holder.tankstelleDiesel.setText("" + sp.getPreis() + " €");
        			break;
        		// Diesel
        		case 3:
        			sorteTimeEntf += "Diesel\n";
//        			holder.tankstelleLDiesel.setText("Diesel");
//        			holder.tankstelleDiesel.setText("" + sp.getPreis() + " €");
        			break;
        		default:
        			break;
        		}
        		// TImestamp
//        		sorteTimeEntf += sp.getPreisTimestamp(); - dies muss noch hinzugehügt werden...
        		sorteTimeEntf += "99:99:99\n";
        		// Entfernung
        		sorteTimeEntf += tp.getTankstelleEntfernung();
        		// Preis
    			holder.tvTPreis.setText(sp.getPreis());
        	}
        }
        // SpritTimeEntf
        holder.tvTSpritTimeEntf.setText(sorteTimeEntf);
        return convertView;
    }
 
    static class ViewHolder {
    	ImageView ivMarke;
        TextView tvTName;
        TextView tvTAdresse;
        TextView tvTPreis;
        TextView tvTSpritTimeEntf;
        
    }
 
}
