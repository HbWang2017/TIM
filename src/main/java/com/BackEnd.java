/**
 *@author
 *Hongbo Wang
 *ID: 16130120125
 *e_mail: hbwang_2016@stu.xidian.edu.cn
 */


package com;

import java.sql.SQLException;

public class BackEnd {
	private static String ip_port = "jdbc:mysql://localhost:3306/TIM";
	private static String user = "root";
	private static String password = "gexiaodong123";
    private DataTransmission dataTrans = null;
    BackEnd() throws SQLException{
        dataTrans = new DataTransmission(ip_port,user,password);
    }
    public TicketCollection getTicketCollection(String SCity, String TCity, String date) throws SQLException{
    	TicketCollection ttc=DataTransmission.getTickets(SCity, TCity, date);
    	
    /*	for (TicketInfo ptd:ttc) {
    		System.out.println("Backend:");
    		
			System.out.println(ptd.getID());
			System.out.println(ptd.getSCity());
			System.out.println(ptd.getTCity());
			System.out.println(ptd.getDate());
			System.out.println(ptd.getNum());
			System.out.println(ptd.getPrice());
		}*/
    	return DataTransmission.getTickets(SCity, TCity, date);
    }
    public int checkPayment(String price, String pay){
        return Integer.parseInt(pay)-Integer.parseInt(price);
    }
    public int checkCity(String City) throws SQLException{
    	TicketCollection List = DataTransmission.getTicket(City);
        if (List.isEmpty()) {
        	return -1;
        } 
        else
        	return List.size();
    }
    public void purchase(String ID, String date,String pay,String change) throws SQLException{
    	DataTransmission.updateTicketNum(ID);
    	DataTransmission.purchaseRecord(ID, date, pay, change);
    }
    public boolean checkNum(String ID, String date) throws SQLException{
    	TicketInfo ticket = DataTransmission.getTicket(ID, date);
    	if (Integer.valueOf(ticket.getNum())>0)
    		return true;
    	else
    		System.out.println("false");
    		return true;
    }
}
