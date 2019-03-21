package cin.ufpe.br.energyprofiler.modes;

import android.support.annotation.NonNull;
import android.util.Log;

import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cin.ufpe.br.energyprofiler.IpAddress;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.EnumDashboard;
import cin.ufpe.br.energyprofiler.enums.EnumSorts;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.Printer;
import cin.ufpe.br.energyprofiler.enums.exceptions.TimeThresholdException;
import cin.ufpe.br.energyprofiler.modes.abstracts.AMode;
import cin.ufpe.br.energyprofiler.modes.abstracts.ISelector;
import cin.ufpe.br.energyprofiler.modes.selectors.CollectionSelector;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;
import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.modes.selectors.SortingSelector;

/**
 * Created by welli on 18-Sep-17.
 */

public class AppExecutor {

    public ISelector selector;

    /**
     * Creates the SortingExecutor. The exceptions are used
     * to change the textview on the main activity.
     *
     * @param collecParam
     * @throws InvalidParameterException
     * @throws InterruptedException
     */
    public AppExecutor(String collecParam, AMode mode) throws InvalidParameterException, InterruptedException {

        if (collecParam != null) {
            if(mode instanceof CollectionsMode) {
                selector = new CollectionSelector(collecParam);
            }
            else if(mode instanceof SortingMode){
                selector = new SortingSelector(collecParam);
            }else{
                throw new InvalidParameterException("invalid mode");
            }
        } else {
            if (Options.offlineMode) {
                throw new InvalidParameterException(
                        "No webservice found. \n \n offline mode activated");
            } else {
                throw new InvalidParameterException(
                        "Welcome to the EnergyProfiler \n \n Use the dashboard " +
                                "\n (http://"+ IpAddress.ip+":"+IpAddress.port+"/) \n to run the experiments");
            }
        }
    }


    /**
     * Executes the action. If the collection support
     * multiples threads, executors will be used to
     * run the tasks in parallel; in the other case,
     * the action will be executed sequentially.
     *
     * @param actionParam
     * @throws InterruptedException
     */
    public void execute(final IActions actionParam)
            throws InterruptedException {

        Long timeInit = System.currentTimeMillis();

        if (Options.nThreads > 1) {
            executeActionParallel(actionParam);
        } else {
            executeActionSequential(actionParam);
        }
        Long timeEnd = System.currentTimeMillis();
        Printer.printTimeExec(actionParam.toString(), timeEnd, timeInit);
    }

    @NonNull
    private void executeWarmUp(IActions actionParam, AMode mode) throws InterruptedException {
        Options.setLimitFactor(Options.warmUpFactor);

        if (mode instanceof CollectionsMode) {
            executeCollectionsWarmUp();
        }
     else if (mode instanceof SortingMode) {
            executeSortingWarmUp(actionParam);
        }
        mode.showOnScreen("WARM UP"," done!");

        Options.setLimitFactor(1);
        Options.doWarmUp = false;
    }

    private void executeCollectionsWarmUp() throws InterruptedException {

        EnumCollections.Actions[] actions =
                EnumCollections.Actions.seqActionsCollec(selector.getCollectionsType());

       // double trueFactor = Options.warmUpFactor;

        for (EnumCollections.Actions a : actions) {
            executeAction(a);


//            boolean notCalibrated = true;
//            while(notCalibrated) {
//                long timeInit = System.currentTimeMillis();
//
//                long timeElapsed = System.currentTimeMillis() - timeInit;
//                Log.d("timeElapsed", ""+timeElapsed);
//                if (timeElapsed < 18000) {
//                    if(timeElapsed < 100) {
//                        Options.warmUpFactor = Options.warmUpFactor * 10;
//                    }else if(timeElapsed < 1000){
//                            Options.warmUpFactor = Options.warmUpFactor*4;
//                    } else {
//                        Options.warmUpFactor = Options.warmUpFactor*1.2;
//                    }
//                    Options.setLimitFactor(Options.warmUpFactor);
//                }
//                else if (timeElapsed > 50000) {
//                    if(timeElapsed > 400000){
//                        Options.warmUpFactor = Options.warmUpFactor*0.1;
//                    }
//                    else if(timeElapsed > 200000){
//                        Options.warmUpFactor = Options.warmUpFactor*0.4;
//                    }
//                    else if(timeElapsed > 100000){
//                        Options.warmUpFactor = Options.warmUpFactor*0.6;
//                    }
//                    else{
//                        Options.warmUpFactor = Options.warmUpFactor*0.8;
//                    }
//                    Options.setLimitFactor(Options.warmUpFactor);
//                }
//                else {
//                    if(Options.warmUpFactor > trueFactor){
//                        trueFactor = Options.warmUpFactor;
//                    }
//                    notCalibrated = false;
//                }
            //          }
            //      }
            //      Options.setLimitFactor(trueFactor);
            //      Log.d("sizeofcollection", ""+Options.capacity*trueFactor);
        }
    }

    private void executeAction(EnumCollections.Actions a) throws InterruptedException {
        if (Options.nThreads > 1) {
            executeActionParallel(a);
        } else {
            executeActionSequential(a);
        }
    }

    private void executeActionSequential(IActions m) {
        try {
            selector.selectAction(m);
        } catch (ActionNotImplementedException e) {
            e.printStackTrace();
        }
    }

    private void executeActionParallel(final IActions actionParam) throws InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(Options.nThreads);

        for (int i = 0; i < Options.nThreads; i++) {
            executors.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        selector.selectAction(actionParam);
                    } catch (ActionNotImplementedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executors.shutdown();
        executors.awaitTermination(1, TimeUnit.DAYS);
    }

    public void initExecWarmp(IActions action, AMode mode) throws TimeThresholdException {

        try {
            Long timeInit = System.currentTimeMillis();

            executeWarmUp(action, mode);
            Runtime.getRuntime().gc();
            mode.dashboardMsg(EnumDashboard.CLEAN_BATTERY);

            Long timeEnd = System.currentTimeMillis();
            Printer.printTimeExec("warm up",timeEnd,timeInit);
            if(timeInit-timeEnd > 400000){
                throw new TimeThresholdException("exceeded the time limit");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    private void executeSortingWarmUp(IActions actionParam) throws InterruptedException {
        if (Options.nThreads > 1) {
            executeActionParallel(EnumSorts.ACTION_INSERT);
            executeActionParallel(actionParam);
            executeActionParallel(EnumSorts.ACTION_REMOVE);
        } else {
            executeActionSequential(EnumSorts.ACTION_INSERT);
            executeActionSequential(actionParam);
            executeActionSequential(EnumSorts.ACTION_REMOVE);
        }
    }
}