package cin.ufpe.br.energyprofiler.modes.selectors;

import java.security.InvalidParameterException;

import cin.ufpe.br.energyprofiler.Printer;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.modes.abstracts.ISelector;
import cin.ufpe.br.energyprofiler.modes.abstracts.ACollection;
import cin.ufpe.br.energyprofiler.enums.exceptions.ActionNotImplementedException;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections.CollectionFactory;
import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;

/**
 * Created by welli on 09-Sep-17.
 */

public class CollectionSelector implements ISelector {

    static ACollection selectedCollection;
    public static EnumCollections.Type collectionsType;


    public CollectionSelector(String collecParam)
            throws InvalidParameterException {

        EnumCollections nameCollection = EnumCollections.exist(collecParam);
        selectedCollection = selectCollection(nameCollection);
    }

    /**
     * Create and returns an collection based on the
     * NameCollection on the parameter.
     * @param nameCollection
     * @return a concrete collection, created based on the parameter
     */
    private ACollection selectCollection(EnumCollections nameCollection) {

        switch (nameCollection) {

            case ARRAY_LIST: case LINKED_LIST: case FAST_LIST:
            case TREE_LIST: case NODE_CACHING_LINKED_LIST:
            case CURSORABLE_LINKED_LIST:
                Options.capacity = EnumCollections.Workload.LIST.getValue();
                return listNotThreadSafe(nameCollection);

            case VECTOR: case COPY_WRITE_ARRAY_LIST: case SYNC_LIST:
            case SYNC_ARRAY_LIST: case SYNC_FAST_LIST:
                Options.capacity = EnumCollections.Workload.LIST.getValue();
                return listThreadSafe(nameCollection);

            case HASH_MAP: case LINKED_HASH_MAP: case TREE_MAP:
            case WEAK_HASH_MAP: case UNIFIED_MAP: case HASHED_MAP:
            case ARRAY_MAP:
            //    case SPARSE_ARRAY:
                Options.capacity = EnumCollections.Workload.MAP.getValue();
                return mapNotThreadSafe(nameCollection);

            case HASH_TABLE:  case CONC_HASH_MAP: case CONC_SKIP_LIST_MAP:
            case SYNC_HASH_MAP: case SYNC_LINKED_HASH_MAP: case SYNC_TREE_MAP:
            case SYNC_WEAK_HASH_MAP: case CONC_HASH_MAP_V8: case CONC_HASH_MAP_EC:
            case SYNC_UNIFIED_MAP: case STATIC_BUCKET_MAP:
                Options.capacity = EnumCollections.Workload.MAP.getValue();
                return mapThreadSafe(nameCollection);

            case HASH_SET: case LINKED_HASH_SET: case TREE_SET:
            case UNIFIED_SET: case TREE_SORTED_SET:
                Options.capacity = EnumCollections.Workload.SET.getValue();
                return setNotThreadSafe(nameCollection);

            case CONC_SKIP_LIST_SET: case COPY_WRITE_ARRAY_SET:
            case SYNC_HASH_SET: case SYNC_LINKED_HASH_SET:
            case SYNC_TREE_SET: case SET_CONC_HASH_MAP:
            case SET_CONC_HASH_MAP_V8:  case SYNC_UNIFIED_SET:
            case SYNC_TREE_SORTED_SET:
                Options.capacity = EnumCollections.Workload.SET.getValue();
                return setThreadSafe(nameCollection);

            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());

        }
    }

    private ACollection setThreadSafe(EnumCollections nameCollection) {
        Options.nThreads = Options.maxThreads;
        collectionsType = EnumCollections.Type.SET;
        switch (nameCollection) {
            case CONC_SKIP_LIST_SET:
                return CollectionFactory.getConcSkipListSet();
            case COPY_WRITE_ARRAY_SET:
                return CollectionFactory.getCopyWriteArraySet();
            case SYNC_HASH_SET:
                return CollectionFactory.getSyncHashSet();
            case SYNC_LINKED_HASH_SET:
                return CollectionFactory.getSyncLinkedHashSet();
            case SYNC_TREE_SET:
                return CollectionFactory.getSyncTreeSet();
            case SET_CONC_HASH_MAP:
                return CollectionFactory.getSetConcHashMap();
            case SET_CONC_HASH_MAP_V8:
                return CollectionFactory.getSetConcHashMapV8();
            case SYNC_UNIFIED_SET:
                return CollectionFactory.getSyncUnifiedSet();
            case SYNC_TREE_SORTED_SET:
                return CollectionFactory.getSyncTreeSortedSet();
            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());
        }
    }

    private ACollection setNotThreadSafe(EnumCollections nameCollection) {
        collectionsType = EnumCollections.Type.SET;
        switch (nameCollection){
            case HASH_SET:
                return CollectionFactory.getHashSet();
            case LINKED_HASH_SET:
                return CollectionFactory.getLinkedHashSet();
            case TREE_SET:
                return CollectionFactory.getTreeSet();
            case UNIFIED_SET:
                return CollectionFactory.getUnifiedSet();
            case TREE_SORTED_SET:
                return CollectionFactory.getTreeSortedSet();
            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());
        }
    }

    private ACollection mapThreadSafe(EnumCollections nameCollection) {
        Options.nThreads = Options.maxThreads;
        collectionsType = EnumCollections.Type.MAP;
        switch (nameCollection) {
            case HASH_TABLE:
                return CollectionFactory.getHashTable();
            case CONC_HASH_MAP:
                return CollectionFactory.getConcHashMap();
            case CONC_SKIP_LIST_MAP:
                return CollectionFactory.getConcSkipListMap();
            case SYNC_HASH_MAP:
                return CollectionFactory.getSyncHashMap();
            case SYNC_LINKED_HASH_MAP:
                return CollectionFactory.getSyncLinkedHashMap();
            case SYNC_TREE_MAP:
                return CollectionFactory.getSyncTreeMap();
            case SYNC_WEAK_HASH_MAP:
                return CollectionFactory.getSyncWeakHashMap();
            case CONC_HASH_MAP_V8:
                return CollectionFactory.getConcHashMapV8();
            case CONC_HASH_MAP_EC:
                return CollectionFactory.getConcHashMapEC();
            case SYNC_UNIFIED_MAP:
                return CollectionFactory.getSyncUnifiedMap();
            case STATIC_BUCKET_MAP:
                return CollectionFactory.getStaticBucketMap();
            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());
        }
    }

    private ACollection mapNotThreadSafe(EnumCollections nameCollection) {
        collectionsType = EnumCollections.Type.MAP;
        switch (nameCollection) {
            case HASH_MAP:
                return CollectionFactory.getHashMap();
            case LINKED_HASH_MAP:
                return CollectionFactory.getLinkedHashMap();
            case TREE_MAP:
                return CollectionFactory.getTreeMap();
            case WEAK_HASH_MAP:
                return CollectionFactory.getWeakHashMap();
            case UNIFIED_MAP:
                return CollectionFactory.getUnifiedMap();
            case HASHED_MAP:
                return CollectionFactory.getHashedMap();
            case ARRAY_MAP:
                return CollectionFactory.getArrayMap();
           // case SPARSE_ARRAY:
         //       return CollectionFactory.getSparseArray();
            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());
        }
    }

    private ACollection listThreadSafe(EnumCollections nameCollection) {
        Options.nThreads = Options.maxThreads;
        collectionsType = EnumCollections.Type.LIST;
        switch (nameCollection) {
            case VECTOR:
                return CollectionFactory.getVector();
            case COPY_WRITE_ARRAY_LIST:
                return CollectionFactory.getCopyWriteArrayList();
            case SYNC_LIST:
                return CollectionFactory.getSyncList();
            case SYNC_ARRAY_LIST:
                return CollectionFactory.getSyncArrayList();
            case SYNC_FAST_LIST:
                return CollectionFactory.getSyncFastList();
            default:
                throw new InvalidParameterException(
                        new StringBuilder("ERROR:\n no collection found for '")
                                .append(nameCollection.name())
                                .append("'").toString());
        }
    }

    private ACollection listNotThreadSafe(EnumCollections nameCollection) {
        collectionsType = EnumCollections.Type.LIST;
        switch (nameCollection) {
            case ARRAY_LIST:
                return CollectionFactory.getArrayList();
            case LINKED_LIST:
                return CollectionFactory.getLinkedList();
            case FAST_LIST:
                return CollectionFactory.getFastList();
            case TREE_LIST:
                return CollectionFactory.getTreeList();
            case NODE_CACHING_LINKED_LIST:
                return CollectionFactory.getNodeCachingLinkedList();
            case CURSORABLE_LINKED_LIST:
                return CollectionFactory.getCursorableLinkedList();
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
     * @param actionParam
     */

    public void selectAction(IActions actionParam) throws ActionNotImplementedException {
        Object obj = null;
         if(actionParam instanceof EnumCollections.Actions) {
            switch ((EnumCollections.Actions) actionParam) {
                case PUT: case PUT_BEG: case PUT_MID: case PUT_END:
                    selectedCollection.put((EnumCollections.Actions) actionParam);
                    break;

                case GET: case GET_ITERATOR: case GET_RANDOM:
                    obj = selectedCollection.get((EnumCollections.Actions) actionParam);
                    Printer.print(obj);
                    break;

                case REMOVE_BEG: case REMOVE_MID: case REMOVE_END: case REMOVE_OBJ:
                    selectedCollection.remove((EnumCollections.Actions) actionParam);
                    break;

                default:
                    throw new InvalidParameterException(
                            new StringBuilder("ERROR:\n no action found for '")
                                    .append(actionParam)
                                    .append("'").toString());
            }
        }

    }

    @Override
    public EnumCollections.Type getCollectionsType() {
        return collectionsType;
    }
}