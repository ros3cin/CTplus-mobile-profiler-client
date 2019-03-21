package cin.ufpe.br.energyprofiler.enums;

import cin.ufpe.br.energyprofiler.IpAddress;
import cin.ufpe.br.energyprofiler.Options;

/**
 * Created by welli on 08-Dec-17.
 */

public enum EnumDashboard {


    WHAT_NOW ("/what_now"),
    FINISHING_COLLECTIONS("/collections"),
    FINISHING_SORTING("/sorting"),
    CLEAN_BATTERY ("/cleanbattery"),
    LOG_DATA("/logdata");

    private final String nameAction;

    EnumDashboard(String name) {
        nameAction = name;
    }

    public String getName(){
        return this.name();
    }

    public String toString(){
        return "http://" + IpAddress.ip + ":" + IpAddress.port + nameAction;
    }

}
