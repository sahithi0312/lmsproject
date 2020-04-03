package com.capgemini.librarymanagementsystem_jdbc.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import com.capgemini.librarymanagementsystem_jdbc.exception.LMSException;

public class IssueDummy {
	public boolean issueBook(int bId,int uId) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pst = conn.prepareStatement("select * from bookbean where bid=?");){
				pst.setInt(1, bId);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					try(PreparedStatement pstmt = conn.prepareStatement("select * from request_details where uid=? and bid=? and email=(select email from users where uid=?)")) {
						pstmt.setInt(1, uId);
						pstmt.setInt(2, bId);
						pstmt.setInt(3, uId);
						rs = pstmt.executeQuery();
						if(rs.next()) {
							try(PreparedStatement pstmt1 = conn.prepareStatement("insert into book_issue_details values(?,?,?,?)");){
								pstmt1.setInt(1, bId);
								pstmt1.setInt(2, uId);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
								Calendar cal = Calendar.getInstance();
								String issueDate = sdf.format(cal.getTime());
								pstmt1.setDate(3, java.sql.Date.valueOf(issueDate));
								cal.add(Calendar.DAY_OF_MONTH, 7);
								String returnDate = sdf.format(cal.getTime());
								pstmt.setDate(4, java.sql.Date.valueOf(returnDate));
								int count=pstmt1.executeUpdate();
								if(count != 0) {	
									try(PreparedStatement pstmt2 = conn.prepareStatement("Insert into borrowed_books values(?,?,(select * from borrowed_books where uid=?))")){
										pstmt2.setInt(1, uId);
										pstmt2.setInt(2, bId);
										pstmt2.setInt(3, uId);
										int isBorrowed = pstmt2.executeUpdate();
										if(isBorrowed != 0) {
											return true;
										}else {
											return false;
										}
									}
								} else {
									throw new LMSException("Book Not issued");
								}					
							}
						} else {
							throw new LMSException("The respective user have not placed any request");
						}			
					}
				}else{
					throw new LMSException("There is no book exist with bookId"+bId);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}

/*
 * PreparedStatement pstmt =
 * conn.prepareStatement("insert into book_issue_details values(?,?,?,?)");
 * "select count(*) as uid from request_details where uid=?,bid=? and email=(select email from users where uid=?)"
 */