package cin.ufpe.br.energyprofiler.listeners;

import com.android.volley.Response;

import cin.ufpe.br.energyprofiler.ModeOperator;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.EnumModes;
import cin.ufpe.br.energyprofiler.enums.EnumSorts;

/**
 * Created by Renato on 19/11/2016.
 */

public class WhatNowListener implements Response.Listener<String>{
    private ModeOperator mo;
    private String collecParam;

    public WhatNowListener(ModeOperator mo, String collecParam){
        this.mo = mo; this.collecParam = collecParam;
    }

    @Override
    public void onResponse(String isSorting) {
        //This is executed only the first time for each collection
        if(EnumSorts.exist(isSorting) != EnumSorts.NOT_FOUND){
            mo.start(EnumModes.SORTING, collecParam, EnumSorts.exist(isSorting));
        }else{
            mo.start(EnumModes.COLLECTIONS, collecParam, EnumCollections.Actions.PUT_BEG);
        }
    }
}
