package cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections;

import java.util.Map;
import java.util.Set;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.Printer;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.modes.abstracts.ACollection;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;

/**
 * Created by welli on 09-Sep-17.
 */

public class CollectionMap extends ACollection {

    private Map collection;

    public CollectionMap(Map collecParam) {
        collection = collecParam;
    }

    @Override
    public void put(EnumCollections.Actions action) throws ActionNotImplementedException {
        int threadIDLocal = threadID.getAndIncrement();
        if(threadIDLocal == (Options.nThreads-1)){
            threadID.set(0);
        }
        int index = Options.getIndex();
        int limit = Options.getLimit();
        switch (action) {
            case PUT_BEG:
                Printer.print("threadid put: " + threadIDLocal);
                for (; index < limit; index++) {
                    collection.put(getParameter(index, threadIDLocal), index);
                }
                break;
            case PUT: case PUT_MID: case PUT_END:
                throw new ActionNotImplementedException("action" + action.toString() + "not implemented");
        }
        Printer.printEndAction(action.toString(), collection.size());
    }



    @Override
    public void remove(EnumCollections.Actions action) throws ActionNotImplementedException {
        int threadIDLocal = threadID.getAndIncrement();
        if(threadIDLocal == (Options.nThreads-1)){
            threadID.set(0);
        }
        int index = Options.getIndex();
        int limit = Options.getLimit();
        switch (action) {
            case REMOVE_BEG:
                try{
                    for (; index < limit; index++) {
                        collection.remove(getParameter(index, threadIDLocal));
                    }
                    break;
                }catch (Exception e){
                    e.fillInStackTrace();
                }
                break;
            case REMOVE_MID: case REMOVE_END: case REMOVE_OBJ:
                throw new ActionNotImplementedException("action" + action.toString() + "not implemented");
        }
        Printer.printEndAction(action.toString(), collection.size());
    }

    public Object get(EnumCollections.Actions action) {
        Object elem = null;
        switch (action) {
            case GET:
                int size = Options.getLimit();
                int threadIDLocal = threadID.getAndIncrement();
                if(threadIDLocal == (Options.nThreads-1)){
                    threadID.set(0);
                }
                for(int j = 0; j < Options.nGetRepetitions; j++) {
                    for (int index = 0; index < size; index++) {
                        elem = collection.get(getParameter(index, threadIDLocal));
                        //  Printer.print(elem + "  for",100);
                    }
                }
                break;
            case GET_ITERATOR:
                Set<Object> set = collection.keySet();
                for(int j = 0; j < Options.nGetRepetitions; j++) {
                    for (Object key : set) {
                        elem = key;
                        //  Printer.print(elem + " ite",1000);
                    }
                }
                break;
        }
        return elem;
    }
}
