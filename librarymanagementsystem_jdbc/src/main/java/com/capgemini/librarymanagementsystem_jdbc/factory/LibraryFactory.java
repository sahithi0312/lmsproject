package com.capgemini.librarymanagementsystem_jdbc.factory;

import com.capgemini.librarymanagementsystem_jdbc.dao.UsersDAO;
import com.capgemini.librarymanagementsystem_jdbc.dao.UsersDAOImplement;
import com.capgemini.librarymanagementsystem_jdbc.service.UsersService;
import com.capgemini.librarymanagementsystem_jdbc.service.UsersServiceImplement;


public class LibraryFactory {
	public static UsersDAO getUsersDao() {
		return new UsersDAOImplement();
	}
	public static UsersService getUsersService() {
		return new UsersServiceImplement();
	}
}
