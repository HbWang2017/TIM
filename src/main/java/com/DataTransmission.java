package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataTransmission{
	static Connection connect = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	static TicketInfo TI = null;
	static TicketCollection Result = new TicketCollection();
	private static String ip_port = "jdbc:mysql://localhost:3306/TIM";
	private static String user = "root";
	private static String password = "gexiaodong123";
	//JDBC连接数据库
	DataTransmission() {		
	}
	//连接ip:port为ip_port的数据库
	DataTransmission(String ip_port,String user,String password) throws SQLException{		
    	try {
			connect = DriverManager.getConnection(ip_port,user,password);
			stmt = connect.createStatement();
	/*		String sql = "select * from TicketInfo";
			PreparedStatement ps = connect.prepareStatement(sql);
			rs = ps.executeQuery(); 
			while(rs.next()) {
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("scity"));
				System.out.println(rs.getString("tcity"));
				System.out.println(rs.getString("date"));
				System.out.println(rs.getInt("num"));
				System.out.println(rs.getInt("price"));
				TI = new TicketInfo(rs.getString("id"),rs.getString("scity"),
						rs.getString("tcity"),rs.getString("date"),rs.getInt("num"),rs.getInt("price"));
				Result.add(TI);	
			}*/
    	}
    	catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}    	
	}
	
	//断开连接
	public static void close() throws SQLException {
		if(stmt!= null) 
			stmt.close(); 		
		if(connect!= null) 
			connect.close(); 
	}
	
	public static TicketCollection getTickets(String SCity,String TCity,String date) throws SQLException {
		new DataTransmission(ip_port,user,password);
		String sql = "select * from TicketInfo  where scity = ? AND tcity = ? AND date = ?";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, SCity);
		ps.setString(2, TCity);
		ps.setString(3, date);
		rs = ps.executeQuery();
		while(rs.next()) {	
	/*		System.out.println(rs.getString("id"));
			System.out.println(rs.getString("scity"));
			System.out.println(rs.getString("tcity"));
			System.out.println(rs.getString("date"));
			System.out.println(rs.getInt("num"));
			System.out.println(rs.getInt("price"));*/
			TI = new TicketInfo(rs.getString("id"),rs.getString("scity"),
					rs.getString("tcity"),rs.getString("date"),rs.getInt("num"),rs.getInt("price"));
	/*		System.out.println(TI.getID());
			System.out.println(TI.getSCity());
			System.out.println(TI.getTCity());
			System.out.println(TI.getDate());
			System.out.println(TI.getNum());
			System.out.println(TI.getPrice());*/
			Result.add(TI);	
		}
		close();
		/*for (TicketInfo ptd:Result) {
			System.out.println(ptd.getID());
			System.out.println(ptd.getSCity());
			System.out.println(ptd.getTCity());
			System.out.println(ptd.getDate());
			System.out.println(ptd.getNum());
			System.out.println(ptd.getPrice());
		}
		*/
		return Result;
	}
	
	//始发/终点站包含 City的车票
	public static TicketCollection getTicket(String City) throws SQLException {
		new DataTransmission(ip_port,user,password);
		String sql = "select * from TicketInfo where scity = ? OR tcity = ?";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, City);
		ps.setString(2, City);
		rs = ps.executeQuery();
		while(rs.next()) {
			TI = new TicketInfo(rs.getString("id"),rs.getString("scity"),
					rs.getString("tcity"),rs.getString("date"),rs.getInt("num"),rs.getInt("price"));
			Result.add(TI);		
		}
		close();
		return Result;
	}
	
	public static TicketInfo getTicket(String ID,String date) throws SQLException {
		new DataTransmission(ip_port,user,password);
		String sql = "select * from TicketInfo where id = ? AND date = ?";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, ID);
		ps.setString(2, date);
		rs = ps.executeQuery();
		while(rs.next()) {
			TI = new TicketInfo(rs.getString("id"),rs.getString("scity"),
				rs.getString("tcity"),rs.getString("date"),rs.getInt("num"),rs.getInt("price"));
			Result.add(TI);	
		}
		close();
		return new TicketInfo();
	}
	//余票-1
	public static void updateTicketNum(String ID) throws SQLException {
		new DataTransmission(ip_port,user,password);
		String sql = "update TicketInfo set num = num-1 where id = ?";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, ID);
		ps.executeUpdate();
		close();
	}
	
	public static void purchaseRecord(String ID,String date,String pay,String money) throws SQLException {
		new DataTransmission(ip_port,user,password);
		String sql = "insert into Purchase values (?,?,?,?)";
		PreparedStatement ps = connect.prepareStatement(sql);
		ps.setString(1, ID);
		ps.setString(2, date);
		ps.setString(3, pay);
		ps.setString(4, money);
		ps.executeUpdate();
		close();
	}
	
	public static void main(String[] args) throws SQLException {
		getTickets("Chongqing","Xian","2018/12/04"); 
	}
}
