package com.capgemini.librarymanagementsystem.database;

import java.util.LinkedList;

import com.capgemini.librarymanagementsystem.dto.Admin;
import com.capgemini.librarymanagementsystem.dto.Book;
import com.capgemini.librarymanagementsystem.dto.RequestBean;
import com.capgemini.librarymanagementsystem.dto.User;
public class LibraryDB {	

		public static final LinkedList<Book> BOOKS = new LinkedList<Book>(); 
		public static final LinkedList<Admin> ADMIN = new LinkedList<Admin>();
		public static final LinkedList<User> USER = new LinkedList<User>();
		public static final LinkedList<RequestBean> REQUEST = new LinkedList<RequestBean>();
	}


