package pl.pjm77.misc;

/* This class contains two methods to validate number contained in string
* parameter. Message parameter is the message displayed in case of error.
* In case of success number returned is 0 or greater.
* -2 is returned in case of NumberFormatException and message is displayed
* -1 is returned when string parameter is null or empty and message is also
* displayed
* longVar returns long
* intVar returns integer */
public class ValPar {

    public static long longVar (String param, String message) {
        long result;
        if (param != null && !param.equals("")) {
            try {
                result = Long.parseLong(param);
            } catch (NumberFormatException e) {
                System.out.println(message);
                e.printStackTrace();
                return -2;
            }
        }else {
            System.out.println(message);
            return -1;
        }
        return result;
    }

    public static int intVar (String param, String message) {
        int result;
        if (param != null && !param.equals("")) {
            try {
                result = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                System.out.println(message);
                e.printStackTrace();
                return -2;
            }
        }else {
            System.out.println(message);
            return -1;
        }
        return result;
    }
}