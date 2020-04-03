package com.capgemini.librarymanagementsystem.dao;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem.database.LibraryDB;
import com.capgemini.librarymanagementsystem.dto.Admin;
import com.capgemini.librarymanagementsystem.dto.Book;
import com.capgemini.librarymanagementsystem.dto.RequestBean;
import com.capgemini.librarymanagementsystem.dto.User;
import com.capgemini.librarymanagementsystem.exception.LMSException;


public class AdminDAOImplement implements AdminDAO{

	
		@Override
		public boolean registerAdmin(Admin admin) {
			for(Admin ad : LibraryDB.ADMIN) {
				if(ad.getEmail().equals(admin.getEmail())) {
					return false;
				}
			}
			LibraryDB.ADMIN.add(admin);
			return true;
		}

		@Override
		public Admin loginAdmin(String email, String password) {
			for(Admin admin : LibraryDB.ADMIN) {
				if(admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
					return admin;
				}
			}
			throw new LMSException("Invalid credentials");

		}

		@Override
		public boolean addBook(Book book) {
			for(Book b : LibraryDB.BOOKS) {
				if(b.getId()==book.getId()) {
					return false;
				}
			}
			LibraryDB.BOOKS.add(book);
			return true;
		}

		@Override
		public boolean removeBook(int id) {
			boolean removeStatus=false;
			for(int i=0;i<=LibraryDB.BOOKS.size()-1;i++)
			{
				Book retrievedBook=LibraryDB.BOOKS.get(i);
				int retrievedId=retrievedBook.getId();
				if(id==retrievedId)
				{
					removeStatus=true;
					LibraryDB.BOOKS.remove(i);
					break;
				}
			}
			return removeStatus;
		}

		@Override
		public int updateBook(int id) {
			int updateStatus=0;
			for(int i=0;i<=LibraryDB.BOOKS.size()-1;i++)
			{
				Book retrievedBook=LibraryDB.BOOKS.get(i);
				int retrievedId=retrievedBook.getId();
				if(id==retrievedId)
				{
					updateStatus++;
					break;
				}
			}
			throw new LMSException("Book not updated");
		}

		@Override
		public LinkedList<Book> searchBookByTitle(String bookName) {
			LinkedList<Book> searchList=new LinkedList<Book>();
			for(int i=0;i<=LibraryDB.BOOKS.size()-1;i++)
			{
				Book retrievedBook=LibraryDB.BOOKS.get(i);
				String retrievedBookName=retrievedBook.getBookName();
				if(bookName.equals(retrievedBookName))
				{
					searchList.add(retrievedBook);	
					return searchList;
				}
			}
			if(searchList.size()==0)
			{
				throw new LMSException("Book not found");
			}
			else
			{
				return searchList;
			}

		}

		@Override
		public LinkedList<Book> searchBookByAuthor(String author) {
			LinkedList<Book> searchList=new LinkedList<Book>();
			for(int i=0;i<=LibraryDB.BOOKS.size()-1;i++)
			{
				Book retrievedBook=LibraryDB.BOOKS.get(i);
				String retrievedBookAuthor=retrievedBook.getAuthor();
				if(author.equals(retrievedBookAuthor))
				{
					searchList.add(retrievedBook);	
				}
			}
			if(searchList.size()==0)
			{
				throw new LMSException("Book not found");
			}
			else
			{
				return searchList;
			}		

		}

		@Override
		public LinkedList<Book> getBooksInfo() {
			return LibraryDB.BOOKS;
		}

		@Override
		public LinkedList<Book> searchBookByCategory(String category) {
			LinkedList<Book> searchList=new LinkedList<Book>();
			for(int i=0;i<=LibraryDB.BOOKS.size()-1;i++)
			{
				Book retrievedBook=LibraryDB.BOOKS.get(i);
				String retrievedCategory=retrievedBook.getCategory();
				if(category.equals(retrievedCategory))
				{
					searchList.add(retrievedBook);	
				}
			}
			if(searchList.size()==0)
			{
				throw new LMSException("Book not found");
			}
			else
			{
				return searchList;
			}	
		}

		@Override
		public List<User> showUsers() {
			List<User> usersList = new LinkedList<User>();
			for (User userBean : LibraryDB.USER) {

				userBean.getId();
				userBean.getName();
				userBean.getEmail();
				userBean.getBooksBorrowed();
				usersList.add(userBean);

			}
			return usersList;
		}

		@Override
		public List<RequestBean> showRequests() {
			List<RequestBean> info = new LinkedList<RequestBean>();
			for (RequestBean requestInfo : LibraryDB.REQUEST) {
				requestInfo.getBookInfo();
				requestInfo.getUserInfo();
				requestInfo.isIssued();
				requestInfo.isReturned();
				info.add(requestInfo);
			}
			return info;
		}

		@Override
		public boolean bookIssue(User user, Book book) {
			boolean isValid = false;

			RequestBean requestInfo = new RequestBean();

			int noOfBooksBorrowed = user.getBooksBorrowed();
			for (RequestBean info : LibraryDB.REQUEST) {
				if (info.getUserInfo().getId() == user.getId()) {
					if (info.getBookInfo().getId() == book.getId()) {
						requestInfo = info;

						isValid = true;

					}
				}
			}

			if (isValid)
			{
				for (Book info2 : LibraryDB.BOOKS) {
					if (info2.getId() == book.getId()) {
						book = info2;
					}
				}

				for (User userInfo : LibraryDB.USER) {
					if (userInfo.getId() == user.getId()) {
						user = userInfo;
						noOfBooksBorrowed = user.getBooksBorrowed();

					}
				}

				if (noOfBooksBorrowed < 3) {

					boolean isRemoved = LibraryDB.BOOKS.remove(book);
					if (isRemoved) {

						noOfBooksBorrowed++;
						System.out.println(noOfBooksBorrowed);
						user.setBooksBorrowed(noOfBooksBorrowed);
						// DataBase.REQUESTDB.remove(requestInfo);
						requestInfo.setIssued(true);
						return true;
					} else {
						throw new LMSException("Book can't be borrowed");
					}

				} else {
					throw new LMSException("Student Exceeds maximum limit");
				}

			} else {
				throw new LMSException("Book data or Student data is incorrect");
			}
		}

		@Override
		public boolean isBookReceived(User user, Book book) {
			boolean isValid = false;
			RequestBean requestInfo1 = new RequestBean();
			for (RequestBean requestInfo : LibraryDB.REQUEST) {

				if (requestInfo.getBookInfo().getId() == book.getId()
						&& requestInfo.getUserInfo().getId() == user.getId() 
						&& requestInfo.isReturned() == true) {
					isValid = true;
					requestInfo1 = requestInfo;
				}
			}
			if (isValid) {

				book.setAuthor(requestInfo1.getBookInfo().getAuthor());
				book.setBookName(requestInfo1.getBookInfo().getBookName());
				LibraryDB.BOOKS.add(book);
				LibraryDB.REQUEST.remove(requestInfo1);


				for (User userInfo2 : LibraryDB.USER) {
					if (userInfo2.getId() == user.getId()) {
						user = userInfo2;
					}
				}
				int noOfBooksBorrowed = user.getBooksBorrowed();
				noOfBooksBorrowed--;
				user.setBooksBorrowed(noOfBooksBorrowed);
				return true;
			}
			return false;
		}
	}
