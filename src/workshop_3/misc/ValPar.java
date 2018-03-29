package workshop_3.misc;

public class ValPar {

	public static long longVar (String param, String message) {
		long result = 0;
		if (param != null && param != "") {
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
		int result = 0;
		if (param != null && param != "") {
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