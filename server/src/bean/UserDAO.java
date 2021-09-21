package bean;

import java.util.ArrayList;

/**
 *这个是模拟数据库的DAO层，如果用户和下面的phoneNumber(123456),password(abc123456)如果是一样的，就说明前端
 * 传过来的数据是一样的，这个以后拓展成访问数据库
 */
public class UserDAO {
	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO();
		System.out.println(userDAO.isLoginOK("123456", "abc123456"));
	}
	public ArrayList<User> users;
			
	public UserDAO() {
		//这个相当于从数据库中查数据，可以自己拓展连接数据库，然后根据sql语句进行增删改查
		// TODO Auto-generated constructor stub
		users = new ArrayList<User>();
		//这个相当于从数据库里面取到的数据，相当于之前注册了两个用户
		users.add(new User("123456","abc123456","codingma"));
		users.add(new User("654321","abcdf123","codemayq"));
	}


	/**
	 * 这个是验证登录信息是否正确的方法
	 * @param phoneNumber
	 * @param password
	 * @return
	 */
	public boolean isLoginOK(String phoneNumber,String password) {
		for (User user : users) {
			if (user.getPhoneNumber().equals(phoneNumber)) {
				if (user.getPassword().equals(password)) {
					return true;
				}
			}
		}
		return false;
	}
}
