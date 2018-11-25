package workshop_3.misc;

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