package com.capgemini.librarymanagementsystem.factory;


import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dao.AdminDAOImplement;
import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.dao.UserDAOImplement;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.AdminServiceImplement;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.service.UserServiceImplement;

public class LibraryFactory {

			public static AdminDAO getAdminDao() {
			return new AdminDAOImplement();
		}
		public static AdminService getAdminService() {
			return new AdminServiceImplement();
		}
		public static UserDAO getUserDao() {
			return new UserDAOImplement();
		}
		public static UserService getUserService() {
			return new UserServiceImplement();
		}
	}
