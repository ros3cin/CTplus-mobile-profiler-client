package cin.ufpe.br.energyprofiler.enums;

/**
 * Created by welli on 04-Dec-17.
 */

public enum EnumSorts implements IActions {

    DEFAULT_SORT("def"),
    BUBBLE_SORT("bub"),
    QUICK_SORT("qui"),
    INSERT_SORT("ins"),
    HEAP_SORT("heap"),
    MERGE_SORT("mer"),
    SELECTION_SORT("sel"),
    SHAKE_SORT("sha"),
    SHELL_SORT("she"),
    ACTION_INSERT("action_insert"),
    ACTION_REMOVE("action_remove"),
    NOT_FOUND("notfound");      //control,

    private final String nameSorting;

    EnumSorts(String name) {
        nameSorting = name;
    }

    public String getName(){
        return this.name();
    }

    public String toString(){
        return nameSorting;
    }

    /**
     * Returns the EnumSorts for a given string representing
     * the value. If the value does not represent any EnumSorts,
     * returns NOT_FOUND
     *
     * @param value - value of EnumSorts (e.g., "put")
     * @return EnumSorts with value or NOT_FOUND
     */

    public static EnumSorts exist(String value){
        for(EnumSorts m : EnumSorts.values()){
            if(value.equals(m.toString())){
                return m;
            }
        } return EnumSorts.NOT_FOUND;
    }


}
