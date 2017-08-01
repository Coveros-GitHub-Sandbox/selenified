package unit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coveros.selenified.services.HTTP;

public class HTTPTest {

	@Test
	public void useCredentialsEmptyTest() {
		HTTP http = new HTTP("Service");
		Assert.assertFalse(http.useCredentials());
	}
	
	@Test
	public void useCredentialsUserTest() {
		HTTP http = new HTTP("Service");
		http.setUser("User");
		Assert.assertFalse(http.useCredentials());
	}
	
	@Test
	public void useCredentialsPassTest() {
		HTTP http = new HTTP("Service");
		http.setPass("Pass");
		Assert.assertFalse(http.useCredentials());
	}
	
	@Test
	public void useCredentialsBothTest() {
		HTTP http = new HTTP("Service");
		http.setUser("User");
		http.setPass("Pass");
		Assert.assertTrue(http.useCredentials());
	}
	
	@Test
	public void useCredentialsBothCTest() {
		HTTP http = new HTTP("Service", "User", "Pass");
		Assert.assertTrue(http.useCredentials());
	}

}