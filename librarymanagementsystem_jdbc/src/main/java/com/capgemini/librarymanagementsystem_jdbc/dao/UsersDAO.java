package com.capgemini.librarymanagementsystem_jdbc.dao;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem_jdbc.dto.BookBean;
import com.capgemini.librarymanagementsystem_jdbc.dto.BookIssueDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.BorrowedBooks;
import com.capgemini.librarymanagementsystem_jdbc.dto.RequestDetails;
import com.capgemini.librarymanagementsystem_jdbc.dto.UsersBean;

public interface UsersDAO {
	boolean register(UsersBean user);
	UsersBean login(String email,String password);
	boolean addBook(BookBean book);
	boolean removeBook(int bId);
	boolean updateBook(BookBean book);
	boolean issueBook(int bId,int uId);
	boolean request(int uId, int bId);
	List<BorrowedBooks> borrowedBook(int uId);
	LinkedList<BookBean> searchBookById(int bId);
	LinkedList<BookBean> searchBookByTitle(String bookName);
	LinkedList<BookBean> searchBookByAuthor(String author);
	LinkedList<BookBean> getBooksInfo();
	boolean returnBook(int bId,int uId,String status);
	LinkedList<BookIssueDetails> bookHistoryDetails(int uId);
	LinkedList<RequestDetails> showRequests();
	LinkedList<BookIssueDetails> showIssuedBooks();
	LinkedList<UsersBean> showUsers();
	boolean updatePassword(String email,String password,String newPassword,String role);
}
