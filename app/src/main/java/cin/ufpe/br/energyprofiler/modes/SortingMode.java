package cin.ufpe.br.energyprofiler.modes;

import java.security.InvalidParameterException;

import cin.ufpe.br.energyprofiler.MainActivity;
import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.enums.EnumSorts;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.enums.EnumDashboard;
import cin.ufpe.br.energyprofiler.enums.EnumModes;
import cin.ufpe.br.energyprofiler.enums.exceptions.TimeThresholdException;
import cin.ufpe.br.energyprofiler.modes.abstracts.AMode;

/**
 * Created by welli on 03-Dec-17.
 */

public class SortingMode extends AMode {

    public SortingMode(MainActivity activity, String collecParam) {
        super(activity, collecParam);
    }

    @Override
    public IActions getNextAction() {
        return action;
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
        catch (InterruptedException e) {
            mainText.setText("ERROR:\n Threads got interrupted");
        }
        catch(Exception e){
            e.fillInStackTrace();
        }
    }

    @Override
    public void execute(IActions action) {
        if(isReady()) {
            if(Options.doWarmUp){
                try {
                    executor.initExecWarmp(action,this);
                } catch (TimeThresholdException e) {
                    e.printStackTrace();
                }
            }else {
                executeAndDashBoard(EnumSorts.ACTION_INSERT,
                        EnumDashboard.CLEAN_BATTERY);

                executeAndDashBoard(action,
                        EnumDashboard.LOG_DATA);
                showOnScreen(action.getName(), " done!");
            }
        }
    }

    @Override
    public void finishedLogging() {
        executeAndDashBoard(EnumSorts.ACTION_REMOVE,
                EnumDashboard.FINISHING_SORTING);
    }

    @Override
    public String getModeName() {
        return "Sorting";
    }
}
