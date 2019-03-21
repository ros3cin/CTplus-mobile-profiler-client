package cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections;

import java.util.Set;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.Printer;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.modes.abstracts.ACollection;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;

/**
 * Created by welli on 09-Sep-17.
 */

public class CollectionSet extends ACollection {

    private Set collection;
    public CollectionSet(Set collecParam){
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
        switch(action){
            case PUT_BEG:
                for (;index<limit;index++){
                    collection.add(getParameter(index,threadIDLocal));
                } break;
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
        switch(action) {
            case REMOVE_BEG:
                for (; index < limit; index++) {
                    collection.remove(getParameter(index, threadIDLocal));
                } break;
            case REMOVE_MID: case REMOVE_END: case REMOVE_OBJ:
                throw new ActionNotImplementedException("action" + action.toString() + "not implemented");
        }
        Printer.printEndAction(action.toString(), collection.size());
    }

    @Override
    public Object get(EnumCollections.Actions action) throws ActionNotImplementedException {
        Object elem = null;
        switch (action){
            case GET_ITERATOR:
                for(int j = 0; j < Options.nGetRepetitions; j++) {
                    for (Object key : collection) {
                        elem = key;
                    }
                }
                break;
            case GET:
                throw new ActionNotImplementedException("action" + action.toString() + "not implemented");
        }
        return elem;
    }
}
