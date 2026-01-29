import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revconnect.dao.UserDAO;
import com.revconnect.model.User;

public class UserService {

	private UserDAO userDAO = new UserDAO();
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public boolean register(User user) {
        logger.info("Attempting user registration: " + user.getEmail());

        boolean result = userDAO.registerUser(user);

        if (result) {
            logger.info("User registered successfully: " + user.getEmail());
        } else {
            logger.error("User registration failed: " + user.getEmail());
        }

        return result;
    }
}
