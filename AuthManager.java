import java.util.Scanner;
import java.util.*;

public class AuthManager {
	
	private static List<UserDetails> userPool = new ArrayList<UserDetails>();
	
	static {
		userPool.add(new UserDetails("admin","admin", Role.ADMIN));
		userPool.add(new UserDetails("john","doe", Role.USER));
	}
	
	private UserDetails currentUser;
	
	public UserDetails getCurrentUser() {
		return currentUser;
	}
	
	public void login() {
		System.out.print("""
                    Пожалуйста залогиньтесь
                    >\s""");
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter userName: ");
            String userName = scan.nextLine();
			System.out.print("Enter password: ");
            String password = scan.nextLine();
			authenticate(userName, password);
	}
	
	public void authenticate(String userName, String password) {
		var userOpt = userPool.stream().filter(user -> user.getUserName().equals(userName) && user.getPassword().equals(password)).findFirst();
		if (userOpt.isPresent()) {
			currentUser = userOpt.get();
			System.out.println("Добро пожаловать, " + currentUser.getUserName());
		}else {
			currentUser = null;
			System.out.println("Такого юзера не существует");
			throw new IllegalArgumentException();
		}
	}
	
	public void logout() {
		currentUser = null;
		System.out.println("Вы успешно вышли из системы");
	}
	
}