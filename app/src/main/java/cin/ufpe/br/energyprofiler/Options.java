package cin.ufpe.br.energyprofiler;

import java.util.Random;

import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.IActions;

/**
 * Created by welli on 09-Sep-17.
 */

public abstract class Options {

    public static int capacity = EnumCollections.Workload.LIST.getValue(); // 2000000
    public static final int nGetRepetitions = 10;
    public static final float loadFactor = 0.75f;
    public static final int seed = 12345;


    public static boolean doWarmUp = true;
    public static int[] randomNumbers = new int[capacity];

    public static short nThreads = 1;
    public static short maxThreads = 4;
    public static boolean isVibrating = false;
    public static boolean isClosing = true;
    public static boolean offlineMode = false;
    public static double warmUpFactor = 0.1;

    private static EnumCollections.Actions[] actionsList;


    static public int getIndex() {
        return 0;
    }
    static public int getLimit() {
        return (int) ((capacity/nThreads) * factorSizeCollection);
    }

    private static double factorSizeCollection = 1;
    public static void setLimitFactor(double limitFactor){
        factorSizeCollection = limitFactor;
    }


    public static EnumCollections.Actions getNextAction(IActions action, EnumCollections.Type type)
    {
        if(actionsList == null){
            actionsList = EnumCollections.Actions.seqActionsCollec(type);
        }

        for (int i = 0; i < actionsList.length-1; i++) {
            if(actionsList[i] == action){
                return actionsList[i+1];
            }
        }
        return actionsList[0];
    }

    public static void createRandomNumbers(){
        randomNumbers = new int[capacity];
        Random generator = new Random(seed);
        for (int i = 0; i < capacity ; i++){
            randomNumbers[i] = Math.abs(generator.nextInt())%capacity;
        }
    }

    public static int getRandomNumber(int index){
        return randomNumbers[index];
    }

}
