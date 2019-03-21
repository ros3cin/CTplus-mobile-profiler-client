package cin.ufpe.br.energyprofiler;

import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.enums.EnumModes;
import cin.ufpe.br.energyprofiler.modes.CollectionsMode;
import cin.ufpe.br.energyprofiler.modes.SortingMode;
import cin.ufpe.br.energyprofiler.modes.abstracts.AMode;

/**
 * Created by welli on 03-Dec-17.
 */

public class ModeOperator {

    MainActivity activity;
    AMode mode;

    ModeOperator(MainActivity activity){
        this.activity = activity;
    }


    public void start(EnumModes enumModes, String collecParam, IActions action) {
         switch (enumModes) {
            case COLLECTIONS:
                mode = new CollectionsMode(activity,collecParam);
                break;
            case SORTING:
                mode = new SortingMode(activity,collecParam);
                break;
            default:
               break;
        }
        initExecutor(collecParam,action);
    }

    public void initExecutor(String collecParam,IActions action){
        mode.initExecutor(collecParam,action);
    }

}
