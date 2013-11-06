package com.dmitrij.doberstein.spritfuchs.adapters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmitrij.doberstein.spritfuchs.R;
import com.dmitrij.doberstein.spritfuchs.dataclasses.FuelSort;
import com.dmitrij.doberstein.spritfuchs.dataclasses.NavigateData;
import com.dmitrij.doberstein.spritfuchs.dataclasses.Price;
import com.dmitrij.doberstein.spritfuchs.dataclasses.StationItem;
import com.dmitrij.doberstein.spritfuchs.utils.Utils;

public class CustomListAdapterNew extends BaseAdapter {
    private ArrayList<StationItem> listData;
    private LayoutInflater layoutInflater;
    private Context cont;

    public CustomListAdapterNew(Context context, ArrayList<StationItem> listData) {
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
 
//    @SuppressWarnings("unused")
//	private FuelSort getFuelSortFromSettings(){
//    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.cont);
//        String strKraftstoff = prefs.getString("prefKraftstoff", "1");
//        int intKraftstoff = 1;
//        try {
//			intKraftstoff = Integer.parseInt(strKraftstoff);
//		} 
//        catch (Exception e) {
//		}
//        
//        if(intKraftstoff == 3){
//        	return FuelSort.DIESEL;
//        }
//        else if(intKraftstoff == 2){
//        	return FuelSort.E10;
//        }
//        else{
//        	return FuelSort.E5;
//        }
//    }
    
    @SuppressWarnings("unused")
	public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
        	// altes layout
//            convertView = layoutInflater.inflate(R.layout.list_row_layout, null);
            convertView = layoutInflater.inflate(R.layout.list_row_layout_new, null);
            
            holder = new ViewHolder();
            holder.ivMarke = (ImageView) convertView.findViewById(R.id.ivMarke);
            holder.tvTName = (TextView) convertView.findViewById(R.id.tvTName);
            holder.tvTAdresse = (TextView) convertView.findViewById(R.id.tvTAdresse);
            holder.tvTPreis = (TextView) convertView.findViewById(R.id.tvTPreis);
            holder.tvTSpritTimeEntf = (TextView) convertView.findViewById(R.id.tvTSpritTimeEntf);
            holder.ibNavigation = (ImageButton) convertView.findViewById(R.id.ibNavigation);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        StationItem tp = (StationItem)listData.get(position);
        
        // Tankstellenname
        holder.tvTName.setText(tp.getName());
        // Tankstellenadresse
//        String adresse = tp.getStreet() + " " + tp.getHouseNumber() + "\n" + ///**********************
//        		tp.getCityCode() + " " + tp.getCity();
        String adresse = tp.getStreet() + "\n" + 
        		tp.getCityCode() + " " + tp.getCity();
        holder.tvTAdresse.setText(adresse);
        
        // Marke
//        holder.ivMarke.setImageResource(Utils.getTankstellenImageSmall(tp.getMark()));
        
        // Preis
        String fuelsort = "";
        String timestamp = "";
        String dist = "";
        
        List<Price> asp = tp.getPrices();
        if(asp != null){
        	for(int i = 0; i < asp.size(); i++){
        		Price sp = asp.get(i);
        		switch(Utils.getSettingsFuelSort(this.cont)){
        		// E5
        		case E5:
        			fuelsort = "E5";
        			break;
        		// E10
        		case E10:
        			fuelsort = "E10";
        			break;
        		// Diesel
        		case DIESEL:
        			fuelsort = "Diesel";
        			break;
        		default:
        			break;
        		}
        		// TImestamp
//        		long ts = sp.getTimestamp();
        		Date ts = sp.getTimestamp();
        		
        		Calendar cal = Calendar.getInstance();
        		cal.setTime(ts);
//        		cal.setTimeInMillis(ts * 1000);
        		
        		String jahr = "" + cal.get(Calendar.YEAR);
        		String monat = cal.get(Calendar.MONTH) < 10 ? "0" + cal.get(Calendar.MONTH) : "" + cal.get(Calendar.MONTH);
        		String day = cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + cal.get(Calendar.DAY_OF_MONTH) : "" + cal.get(Calendar.DAY_OF_MONTH);
        		
        		String stunden = (cal.get(Calendar.HOUR)) < 10 ? "0" + cal.get(Calendar.HOUR) : "" + cal.get(Calendar.HOUR);
        		String minuten = (cal.get(Calendar.MINUTE)) < 10 ? "0" + cal.get(Calendar.MINUTE) : "" + cal.get(Calendar.MINUTE);
        		String sekunden = (cal.get(Calendar.SECOND)) < 10 ? "0" + cal.get(Calendar.SECOND) : "" + cal.get(Calendar.SECOND);
        		
        		timestamp = day + "." + monat + "." + jahr + "<br>" +
        				stunden + ":" + minuten + ":" + sekunden;
        		
        		// Entfernung
        		double distanz = tp.getDestination();
//        		sorteTimeEntf += (distanz < 1.00) ? distanz + " m" : distanz + " km";
        		String distStr = (distanz < 1.00) ? " m" : " km";
        		dist = String.format("%.2f", distanz) + distStr;
        		
        		// Preis
        		String preis = "€ " + String.format("%.3f",sp.getPrice());
    			holder.tvTPreis.setText(preis);
    			break;
        	}
        }
        // SpritTimeEntf
        Spanned sd = Html.fromHtml("<b><big>" + fuelsort + "</big></b>" +  "<br />" + 
                timestamp + "<br />" + dist);
        holder.tvTSpritTimeEntf.setText(sd);
        
        // setze navigationsdaten
        NavigateData nd = new NavigateData(0.0, 0.0, 
        		tp.getLatitude(), tp.getLongtitude());
        holder.ibNavigation.setTag(nd);
        
        return convertView;
    }
 
    static class ViewHolder {
    	ImageView ivMarke;
        TextView tvTName;
        TextView tvTAdresse;
        TextView tvTPreis;
        TextView tvTSpritTimeEntf;
        
        ImageButton ibNavigation;
        
    }
 
}
