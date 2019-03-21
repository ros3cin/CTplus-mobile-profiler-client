package cin.ufpe.br.energyprofiler.enums;

import java.security.InvalidParameterException;
import java.util.Arrays;

import cin.ufpe.br.energyprofiler.Printer;

/**
 * Created by welli on 09-Oct-17.
 */

public enum EnumCollections {

    //lists
    ARRAY_LIST("al"),                  //not thread-safe
    LINKED_LIST("ll"),                 //not thread-safe
    FAST_LIST("fl"),                   //not thread-safe (from EclipseCollections)
    TREE_LIST("tl"),                   //not thread-safe (from ApacheCommonsCollections)
    NODE_CACHING_LINKED_LIST("ncll"),  //not thread-safe (from ApacheCommonsCollections)
    CURSORABLE_LINKED_LIST("cll"),     //not thread-safe (from ApacheCommonsCollections)
    VECTOR("vec"),                     //thread-safe
    COPY_WRITE_ARRAY_LIST("cwal"),     //thread-safe
    SYNC_LIST("sl"),                   //thread-safe
    SYNC_ARRAY_LIST("sal"),            //thread-safe
    SYNC_FAST_LIST("sfl"),             //thread-safe (from EclipseCollections)

    //maps
    HASH_MAP("hm"),                    //not thread-safe
    LINKED_HASH_MAP("lhm"),            //not thread-safe
    TREE_MAP("tm"),                    //not thread-safe
    WEAK_HASH_MAP("whm"),              //not thread-safe
    UNIFIED_MAP("um"),                 //not thread-safe (from EclipseCollections)
    HASHED_MAP("hsdm"),                //not thread-safe (from ApacheCommonsCollections)
    ARRAY_MAP("amap"),                 //not thread-safe
   // SPARSE_ARRAY("spar"),              //not thread-safe
    HASH_TABLE("ht"),                  //thread-safe
    CONC_HASH_MAP("chm"),              //thread-safe
    CONC_SKIP_LIST_MAP("cslm"),        //thread-safe
    SYNC_HASH_MAP("shm"),              //thread-safe
    SYNC_LINKED_HASH_MAP("slhm"),      //thread-safe
    SYNC_TREE_MAP("stm"),              //thread-safe
    SYNC_WEAK_HASH_MAP("swhm"),        //thread-safe
    CONC_HASH_MAP_V8("chmv8"),         //thread-safe (from jsr166e)
    CONC_HASH_MAP_EC("chmec"),         //thread-safe (from EclipseCollections)
    SYNC_UNIFIED_MAP("sum"),           //thread-safe (from EclipseCollections)
    STATIC_BUCKET_MAP("sbm"),          //thread-safe (from ApacheCommonsCollections)

    //sets
    HASH_SET("hs"),                    //not thread-safe
    LINKED_HASH_SET("lhs"),            //not thread-safe
    TREE_SET("ts"),                    //not thread-safe
    UNIFIED_SET("us"),                 //not thread-safe (from EclipseCollections)
    TREE_SORTED_SET("tss"),            //not thread-safe (from EclipseCollections)
    SYNC_HASH_SET("shs"),              //thread-safe
    SYNC_LINKED_HASH_SET("slhs"),      //thread-safe
    SYNC_TREE_SET("sts"),              //thread-safe
    COPY_WRITE_ARRAY_SET("cwas"),      //thread-safe
    CONC_SKIP_LIST_SET("csls"),        //thread-safe
    SET_CONC_HASH_MAP("schm"),         //thread-safe
    SET_CONC_HASH_MAP_V8("schmv8"),    //thread-safe (the underlying map comes from jsr166e)
    SYNC_UNIFIED_SET("sus"),           //thread-safe (from EclipseCollections)
    SYNC_TREE_SORTED_SET("stss"),      //thread-safe (from EclipseCollections)

    NOT_FOUND("notfound");             //control

    private final String nameCollec;

    EnumCollections(String name) {
        nameCollec = name;
    }

    public String getName(){
        return this.name();
    }

    public String toString(){
        return nameCollec;
    }

    /**
     * Returns the NameCollection for a given string representing
     * the value. If the value does not represent any NameCollection,
     * returns NOT_FOUND
     *
     * @param value - value of NameCollection (e.g., "al")
     * @return NameCollection with value or NOT_FOUND
     */

    public static EnumCollections exist(String value) {
        for (EnumCollections m : EnumCollections.values()) {
            if (value.equals(m.toString())) {
                return m;
            }
        } return EnumCollections.NOT_FOUND;
    }



    public enum Type {

        //lists
        LIST("list"),
        SET("set"),
        MAP("map"),

        NOT_FOUND("notfound");             //control

        private final String nameCollecType;

        Type(String name) {
            nameCollecType = name;
        }

        public String getName(){
            return this.name();
        }

        public String toString(){
            return nameCollecType;
        }

        /**
         * Returns the NameCollection for a given string representing
         * the value. If the value does not represent any NameCollection,
         * returns NOT_FOUND
         *
         * @param value - value of NameCollection (e.g., "al")
         * @return NameCollection with value or NOT_FOUND
         */

        public static Type exist(String value) {
            for (Type m : Type.values()) {
                if (value.equals(m.toString())) {
                    return m;
                }
            } return Type.NOT_FOUND;
        }
    }

    public enum Actions implements IActions{

        PUT_BEG("put-beg"),        //
        REMOVE_BEG("rem-beg"),     //THIS ORDER(PUT then ACTION_REMOVE) IS
        PUT_MID("put-mid"),        //IMPORTANT BECAUSE IT REDUCES THE
        REMOVE_MID("rem-mid"),     //GARBAGE COLLECTOR FREQUENCY
        PUT_END("put-end"),        //
        REMOVE_END("rem-end"),     //

        PUT("put"),
        GET("get"),
        GET_ITERATOR("get-ite"),
        GET_RANDOM("get-rdm"),
        REMOVE_OBJ("rem-obj");


        private final String nameAction;

        Actions(String name) {
            nameAction = name;
        }

        public String getName(){
            return this.name();
        }

        public String toString(){
            return nameAction;
        }

        public static Actions[] seqActionsCollec(EnumCollections.Type type){
            switch (type) {
                case LIST:
                    return EnumCollections.Actions.values();

                case SET:
                    return new EnumCollections.Actions[]{
                            EnumCollections.Actions.PUT_BEG,
                            EnumCollections.Actions.GET_ITERATOR,
                            EnumCollections.Actions.REMOVE_BEG};
                case MAP:
                    return new EnumCollections.Actions[]{
                            EnumCollections.Actions.PUT_BEG,
                            EnumCollections.Actions.GET,
                            EnumCollections.Actions.GET_ITERATOR,
                            EnumCollections.Actions.REMOVE_BEG};
            }
            return null;
        }
    }

    public enum Workload{

        //lists
        LIST(300000),
        SET(4500000),
        MAP(4500000);

        private final int load;

        Workload(int name) {
            load = name;
        }

        public String getName(){
            return this.name();
        }

        public int getValue(){
            return load;
        }
    }


}

