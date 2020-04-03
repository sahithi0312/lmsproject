package com.capgemini.librarymanagementsystem_jdbc.service;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem_jdbc.dao.UsersDAO;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookBean;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BorrowedBooks;
import com.capgemini.librarymanagementsystem_jdbc.dto.RequestDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.UsersBean;
import com.capgemini.librarymanagementsystem_jdbc.factory.LibraryFactory;

public class UsersServiceImplement implements UsersService{

	private UsersDAO dao = LibraryFactory.getUsersDao();
	@Override
	public boolean register(UsersBean user) {
		return dao.register(user);
	}

	@Override
	public UsersBean login(String email, String password) {
		return dao.login(email, password);
	}

	@Override
	public boolean addBook(BookBean book) {
		return dao.addBook(book);
	}

	@Override
	public boolean removeBook(int bId) {
		return dao.removeBook(bId);
	}

	@Override
	public boolean updateBook(BookBean book) {
		return dao.updateBook(book);
	}

	@Override
	public boolean issueBook(int bId,int uId) {
		return dao.issueBook(bId,uId);
	}

	@Override
	public boolean request(int uId, int bId) {
		return dao.request(uId, bId);
	}

	@Override
	public LinkedList<BookBean> searchBookByTitle(String bookName) {
		return dao.searchBookByTitle(bookName);
	}

	@Override
	public LinkedList<BookBean> searchBookByAuthor(String author) {
		return dao.searchBookByAuthor(author);
	}

	@Override
	public LinkedList<BookBean> getBooksInfo() {
		return dao.getBooksInfo();
	}

	@Override
	public boolean returnBook(int bId,int uId,String status) {
		return dao.returnBook(bId,uId,status);
	}

	@Override
	public LinkedList<BookIssueDetails> bookHistoryDetails(int uId) {
		return dao.bookHistoryDetails(uId);
	}

	@Override
	public List<BorrowedBooks> borrowedBook(int uId) {
		return dao.borrowedBook(uId);
	}

	@Override
	public LinkedList<BookBean> searchBookById(int bId) {
		return dao.searchBookById(bId);
	}

	@Override
	public LinkedList<RequestDetails> showRequests() {
		return dao.showRequests();
	}

	@Override
	public LinkedList<BookIssueDetails> showIssuedBooks() {
		return dao.showIssuedBooks();
	}

	@Override
	public LinkedList<UsersBean> showUsers() {
		return dao.showUsers();
	}

	@Override
	public boolean updatePassword(String email, String password, String newPassword, String role) {
		return dao.updatePassword(email, password, newPassword, role);
	}

}
