package cin.ufpe.br.energyprofiler.listeners;

import com.android.volley.Response;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.modes.abstracts.AMode;

/**
 * Created by Renato on 19/11/2016.
 */

public class FinishListener implements Response.Listener<String> {
    AMode mo;

    public FinishListener(AMode mo){
        this.mo = mo;
    }

    public void onResponse(String str){
        if(str.equals("cleaned")){
            mo.execute(EnumCollections.Actions.PUT_BEG);
        }
        else if(str.equals("logged")){
            mo.finishedLogging();
        }
        else if(str.equals("done")){
            mo.endApp();
        }
        else{
            mo.execute(mo.getNextAction());
        }

    }
}

