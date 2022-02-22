package application;

import java.util.regex.Pattern;

public class InputValidation {

	public static boolean usernameValid(String username) {
		//regex:
		//Deve iniziare con un carattere [A-Za-z].
		//Tutti gli altri caratteri possono essere caratteri, numeri o underscore [A-Za-z0-9_].
		//Poichè il vincolo di lunghezza è 7-15 e abbiamo già corretto il primo, diamo {6,14}.
		//Usiamo ^ e $ per specificare l'inizio e la fine della corrispondenza
		String regex = "^[A-Za-z][A-Za-z0-9_]{6,14}$";
		if(Pattern.matches(regex, username))
			return true;
		return false;
	}
}
