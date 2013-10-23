package com.dmitrij.doberstein.spritfuchs;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyPreferencesCategory extends PreferenceCategory {


	public MyPreferencesCategory(Context context) {
        super(context);
    }
 
    public MyPreferencesCategory(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public MyPreferencesCategory(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    /**
     * We catch the view after its creation, and before the activity will use it, in order to make our changes
     * @param parent
     * @return
     */
    @Override
    protected View onCreateView(ViewGroup parent) {
        // And it's just a TextView!
        TextView categoryTitle =  (TextView)super.onCreateView(parent);
        categoryTitle.setBackgroundColor(Color.WHITE);
        categoryTitle.setTextColor(Color.RED);
 
        return categoryTitle;
    }
}