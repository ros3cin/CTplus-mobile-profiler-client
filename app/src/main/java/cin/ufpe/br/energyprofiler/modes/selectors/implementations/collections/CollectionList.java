package cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections;

import java.util.List;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.Printer;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.modes.abstracts.ACollection;

/**
 * Created by welli on 09-Sep-17.
 */

public class CollectionList extends ACollection {


    public static List collection;
    public CollectionList(List collecParam){
        collection = collecParam;
    }

    @Override
    public void put(EnumCollections.Actions action){
        int index = Options.getIndex();
        int limit = Options.getLimit();

        switch (action){
            case PUT:
                for (; index <limit ; index++){
                    collection.add(index);
                } break;
            case PUT_BEG:
                for (; index <limit ; index++){
                    collection.add(0,index);
                } break;
            case PUT_MID:
                for (; index <limit ; index++){
                    collection.add(collection.size()/2,index);
                } break;
            case PUT_END:
                for (; index <limit ; index++){
                    collection.add(collection.size(),index);
                } break;
        }
        Printer.printEndAction(action.toString(), collection.size());
    }

    @Override
    public void remove(EnumCollections.Actions action) {
        int index = Options.getIndex();
        int limit = Options.getLimit();
        switch (action) {
            case REMOVE_OBJ:
                for (; index < limit ; index++){
                   collection.remove((Object) index);
                } break;
            case REMOVE_BEG:
                for (; index < limit ; index++){
                    collection.remove(0);
                } break;
            case REMOVE_MID:
                for (; limit > index ; limit--){
                    collection.remove(limit/2);;
                } break;
            case REMOVE_END:
                limit--;
                for (; limit >= index ; limit--){
                    collection.remove(limit);
                } break;
        }
        Printer.printEndAction(action.toString(), collection.size());
    }

    @Override
    public Object get(EnumCollections.Actions action) {
        Object elem = null;
        int size = collection.size();
        switch (action){
            case GET:
                for(int j = 0; j < Options.nGetRepetitions; j++) {
                    for (int i = 0; i < size; i++) {
                        elem = collection.get(i);
                    }
                }
                break;
            case GET_RANDOM:
                for(int j = 0; j < Options.nGetRepetitions; j++) {
                    for (int i = 0; i < size; i++) {
                        elem = collection.get(Options.getRandomNumber(i) % size);
                    }
                }
                break;
            case GET_ITERATOR:
                for(int j = 0; j < Options.nGetRepetitions; j++) {
                    for (Object key : collection) {
                        elem = key;
                    }
                }
                break;
        }
        return elem;
    }

}