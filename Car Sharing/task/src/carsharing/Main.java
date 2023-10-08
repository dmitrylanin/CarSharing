package carsharing;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        HashMap<String, String> paramMap = new HashMap();
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg != null) {
                    if (arg.startsWith("-")) {
                        paramMap.put(arg, args[i+1]);
                    }else{
                        continue;
                    }
                }
            }
        }

        DataProcessing dataProcessing = new DataProcessing(paramMap.get("-databaseFileName"));
        Menu menu = new Menu(true, dataProcessing);
        menu.engine();
    }
}