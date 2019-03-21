package cin.ufpe.br.energyprofiler;

/**
 * Created by welli on 14-Oct-17.
 */

public class Printer {


    private static int index;

    public static void printTimeExec(String collection, Long timeEnd, Long timeInit) {
        System.out.println(
                new StringBuilder("time exec in ")
                        .append(collection)
                        .append(": ")
                        .append((timeEnd - timeInit))
                        .append("ms"));
    }

    public static void printEndAction(String action, int size){
       System.out.println(
               new StringBuilder("end ")
                       .append(action)
                       .append(" size:")
                       .append(size));

    }

    public static void print(Object obj) {
        System.out.println(obj);
    }
    public static void print(Object obj,int forEach) {
        if(index%forEach==0){
            System.out.println(obj);
        }
        index++;
    }
}
