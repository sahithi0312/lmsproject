package com.capgemini.librarymanagementsystem_jdbc.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;

import com.capgemini.librarymanagementsystem_jdbc.exception.LMSException;


public class dummy {
	public boolean returnBook(int bId,int uId,String status) {
		try(FileInputStream info = new FileInputStream("db.properties");){
			Properties pro = new Properties();
			pro.load(info);
			Class.forName(pro.getProperty("path"));
			try(Connection conn = DriverManager.getConnection(pro.getProperty("dburl"),pro);
					PreparedStatement pst = conn.prepareStatement("select * from bookbean where bid=?");) {
				pst.setInt(1, bId);
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					try(PreparedStatement pstmt = conn.prepareStatement("select * from book_issue_details where bid=? and uid=?");){
						pstmt.setInt(1, bId);
						pstmt.setInt(2, uId);
						rs = pstmt.executeQuery();
						if(rs.next()) {
							Date issueDate = rs.getDate("issueDate");
							Date returnDate = rs.getDate("returnDate");
							long difference = issueDate.getTime() - returnDate.getTime();
							float daysBetween = (difference / (1000*60*60*24));
							if(daysBetween>7) {
								float fine = daysBetween*5;
								System.out.println("The user has to pay the fine of the respective book of Rs:"+fine);
								if(status=="yes") {
									try(PreparedStatement pstmt1 = conn.prepareStatement("delete from book_issue_details where bid=? and uid=?");) {
										pstmt1.setInt(1,bId);
										pstmt1.setInt(2,uId);
										int count =  pstmt1.executeUpdate();
										if(count != 0) {
											try(PreparedStatement pstmt2 = conn.prepareStatement("delete from borrowed_books where bid=? and uid=?");) {
												pstmt2.setInt(1, bId);
												pstmt2.setInt(2, uId);
												int isReturned = pstmt2.executeUpdate();
												if(isReturned != 0 ) {
													try(PreparedStatement pstmt3 = conn.prepareStatement("delete from request_deatils where bid=? and uid=?");){
														pstmt3.setInt(1, bId);
														pstmt3.setInt(2, uId);
														int isRequestDeleted = pstmt3.executeUpdate();
														if(isRequestDeleted != 0) {
															return true;
														}else {
															return false;
														}
													}
												}else {
													return false;
												}
											}
										} else {
											return false;
										}
									}
								} else {
									throw new LMSException("The User has to pay fine for delaying book return");
								}
							}else {
								try(PreparedStatement pstmt1 = conn.prepareStatement("delete from book_issue_details where bid=? and uid=?");) {
									pstmt1.setInt(1,bId);
									pstmt1.setInt(2,uId);
									int count =  pstmt1.executeUpdate();
									if(count != 0) {
										try(PreparedStatement pstmt2 = conn.prepareStatement("delete from borrowed_books where bid=? and uid=?");) {
											pstmt2.setInt(1, bId);
											pstmt2.setInt(2, uId);
											int isReturned = pstmt2.executeUpdate();
											if(isReturned != 0 ) {
												try(PreparedStatement pstmt3 = conn.prepareStatement("delete from request_deatils where bid=? and uid=?");){
													pstmt3.setInt(1, bId);
													pstmt3.setInt(2, uId);
													int isRequestDeleted = pstmt3.executeUpdate();
													if(isRequestDeleted != 0) {
														return true;
													}else {
														return false;
													}
												}
											}else {
												return false;
											}
										}
									} else {
										return false;
									}
								}
							}
						}else {
							throw new LMSException("This respective user hasn't borrowed any book");
						}
					}

				}else {
					throw new LMSException("No book exist with bookId"+bId);
				}

			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
}





