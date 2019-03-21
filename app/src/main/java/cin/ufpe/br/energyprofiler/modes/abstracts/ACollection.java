package cin.ufpe.br.energyprofiler.modes.abstracts;

import java.util.concurrent.atomic.AtomicInteger;

import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;

/**
 * Created by welli on 09-Sep-17.
 */

public abstract class ACollection {

    public abstract void put(EnumCollections.Actions action) throws ActionNotImplementedException;
    public abstract void remove(EnumCollections.Actions actionParam) throws ActionNotImplementedException;
    public abstract Object get(EnumCollections.Actions actionParam) throws ActionNotImplementedException;

    protected AtomicInteger threadID = new AtomicInteger();

    protected String getParameter(int i,long threadID) {
        return new StringBuilder()
                .append(String.valueOf(i))
                .append("-")
                .append(threadID)
                .toString();
    }

    //    protected volatile int threadCtr = -1;
//    protected ThreadLocal<Integer> minShare = new ThreadLocal<Integer>();
//    protected ThreadLocal<Integer> maxShare = new ThreadLocal<Integer>();
//    protected ThreadLocal<Integer> localCtr = new ThreadLocal<Integer>();
//    protected synchronized int getThreadCtr() {
//        return (Options.nThreads-1 == threadCtr) ? threadCtr = 0 : ++threadCtr;
//    }
//    protected void configThreadLocal(){
//        localCtr.set(getThreadCtr());
//
//        minShare.set((Options.capacity/ Options.nThreads)*localCtr.get());
//        maxShare.set((Options.capacity/ Options.nThreads)*(localCtr.get()+1));
//    }
//    protected int getIndex() {
//        if(Options.nThreads > 1){
//            configThreadLocal();
//            return minShare.get();
//        }
//        else{
//            return 0;
//        }
//    }
//    protected int getLimit() {
//        if(Options.nThreads > 1){
//            return maxShare.get();
//        }
//        else{
//            return Options.capacity/Options.nThreads;
//        }
//    }


}
