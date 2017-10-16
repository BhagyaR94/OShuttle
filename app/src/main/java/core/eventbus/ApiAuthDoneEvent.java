package core.eventbus;

import application.entity.User;

/**
 * Reports result returned in the API call.
 * 
 * @author kthakur
 * 
 * @version 0.1
 *
 */
public class ApiAuthDoneEvent {

	User user;

	public ApiAuthDoneEvent(User user) {

		this.user = user;
	}

	public User getResponse() {
		
		return user;
	}
}
