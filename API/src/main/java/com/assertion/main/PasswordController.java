package com.assertion.main;

import java.util.HashMap;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.assertion.model.DBOperations;
import com.assertion.model.Password;

@CrossOrigin
@RestController
public class PasswordController extends SpringBootServletInitializer {
DBOperations db = new DBOperations();
	@RequestMapping(value = "/getPassword", method = RequestMethod.POST)
	public HashMap<String, String> home() {
		System.out.println("**********Getting Passwords**********");
		return db.getPasswords();
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public boolean edit(@RequestBody Password pass) {
		System.out.println("**********Editing password for : " + pass.getWebsite() + "**********");
		return db.updatePassword(pass);
	}

	@RequestMapping(value = "/createPassword", method = RequestMethod.POST)
	public boolean save(@RequestBody Password pass) {
		System.out.println("**********Saving new password for : " + pass.getWebsite() + "**********");
		return db.createPassword(pass);
	}

	@RequestMapping(value = "/deletePassword", method = RequestMethod.POST)
	public boolean delete(@RequestBody String[] deletePasswords) {
		return db.deletePassword(deletePasswords);
	}

}