package cin.ufpe.br.energyprofiler.modes.selectors;

import java.security.InvalidParameterException;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.Printer;
import cin.ufpe.br.energyprofiler.enums.EnumSorts;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.modes.abstracts.ISelector;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections.CollectionFactory;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections.CollectionList;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.sorting.SortingList;

/**
 * Created by welli on 09-Sep-17.
 */

public class SortingSelector implements ISelector {

    static SortingList sortingList;
    static EnumCollections.Type collectionsType;

    public SortingSelector(String collecParam)
            throws InvalidParameterException {

        EnumCollections nameCollection = EnumCollections.exist(collecParam);
        sortingList = new SortingList(selectCollection(nameCollection).collection);
    }

    /**
     * Create and returns an collection based on the
     * NameCollection on the parameter.
     * @param nameCollection
     * @return a concrete collection, created based on the parameter
     */
    private CollectionList selectCollection(EnumCollections nameCollection) {

        switch (nameCollection) {

            //LISTS
            case ARRAY_LIST:
                Options.nThreads = 1;
                return CollectionFactory.getArrayList();
            case LINKED_LIST:
                Options.nThreads = 1;
                return CollectionFactory.getLinkedList();

            case VECTOR:
                return CollectionFactory.getVector();
            case COPY_WRITE_ARRAY_LIST:
                return CollectionFactory.getCopyWriteArrayList();
            case SYNC_LIST:
                return CollectionFactory.getSyncList();
            case SYNC_ARRAY_LIST:
                return CollectionFactory.getSyncArrayList();

            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());
        }
    }

    /**
     * Selects and executes an action based on the
     * NameAction on the parameter.
     * @param sortingParam
     */

    public void selectAction(IActions sortingParam) throws ActionNotImplementedException {
         Object obj = null;
         if(sortingParam instanceof EnumSorts) {
            switch ((EnumSorts) sortingParam){
                case ACTION_INSERT:
                    sortingList.fillRandom(Options.capacity*2,1);
                    break;
                case ACTION_REMOVE:
                    sortingList.removeAll();
                    break;
                default:
                    obj = sortingList.sort((EnumSorts) sortingParam);
                    Printer.print(obj);
                    break;
            }
        }
    }

    @Override
    public EnumCollections.Type getCollectionsType() {
        return collectionsType;
    }
}