package cin.ufpe.br.energyprofiler.enums;

/**
 * Created by welli on 04-Dec-17.
 */

public enum EnumModes {

    COLLECTIONS("Collections"),
    SORTING("Sorting"), NOT_FOUND("notfound");

    private final String nameMode;

    EnumModes(String name) {
        nameMode = name;
    }

    public String toString(){
        return nameMode;
    }
}
