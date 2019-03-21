package cin.ufpe.br.energyprofiler.modes;

import java.security.InvalidParameterException;

import cin.ufpe.br.energyprofiler.MainActivity;
import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.enums.EnumDashboard;
import cin.ufpe.br.energyprofiler.enums.exceptions.TimeThresholdException;
import cin.ufpe.br.energyprofiler.modes.abstracts.AMode;

/**
 * Created by welli on 03-Dec-17.
 */

public class CollectionsMode extends AMode {

    public CollectionsMode(MainActivity activity,String collecParam){
        super(activity,collecParam);
    }

    @Override
    public void initExecutor(String collecParam, IActions action) {
        try {
            executor = new AppExecutor(collecParam, this);
            this.action = action;
            execute(action);
        }
        catch(InvalidParameterException e){
            mainText.setText(e.getMessage());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public IActions getNextAction(){
        action = Options.getNextAction(action,executor.selector.getCollectionsType());
        return action;
    }

    @Override
    public void execute(IActions action) {
        if(isReady()) {
            try {
                if (Options.doWarmUp) {
                    executor.initExecWarmp(action, this);
                } else {
                    executeAndDashBoard(action, EnumDashboard.LOG_DATA);
                    showOnScreen(action.getName(), " done!");
                }
            }  catch (TimeThresholdException e) {
                showOnScreen(action.toString(),e.getMessage());
                dashboardMsg(EnumDashboard.FINISHING_COLLECTIONS);
            }
        }
    }

    @Override
    public void finishedLogging() {
        Runtime.getRuntime().gc();
        dashboardMsg(EnumDashboard.FINISHING_COLLECTIONS);
    }
    @Override
    public String getModeName() {
        return "Collection";
    }
}
