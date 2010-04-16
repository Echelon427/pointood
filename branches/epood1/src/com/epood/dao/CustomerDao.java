package com.epood.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.epood.model.data.customer.Customer;


public class CustomerDao {

    ResultSet CustomerAmount ;
    String sql ;
    Connection db ;	
    Statement  st ;
    ArrayList<Customer> customerList;
    Customer Current_Customer ;
    com.epood.dao.dbconnection dbconnection ;
	
    public ArrayList<Customer> getCustomersFromDB(){
    	
    	try {
			dbconnection = new dbconnection();
			this.db = dbconnection.getConnection();
			this.st = this.db.createStatement();
			
			sql = "select customer, first_name, last_name from customer order by customer";
			CustomerAmount = this.st.executeQuery(sql);
			customerList = new ArrayList<Customer>();
			
			while(CustomerAmount.next()){
				Current_Customer = new Customer();
				Current_Customer.setCustomer(CustomerAmount.getInt("customer"));
				Current_Customer.setFirstName(CustomerAmount.getString("first_name"));
				Current_Customer.setLastName(CustomerAmount.getString("last_name"));
				//System.out.println("Current customer: "+Current_Customer.getCustomer()+", name: "+
					//	Current_Customer.getFirstName()+" "+Current_Customer.getLastName());
				customerList.add(Current_Customer);
			}
			this.db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return customerList;
    }
    
    public Customer getCustomerFromDB(int customer_id){
		
    	for(Customer c : getCustomersFromDB()){
    		if(c.getCustomer()==customer_id)
    			Current_Customer = c;
    	}    	
    	return Current_Customer;
    }
    
    public void updateCustomerToDB(Customer updatedCustomer){
    	
    	try {
			dbconnection = new dbconnection();
			this.db = dbconnection.getConnection();
			this.st = this.db.createStatement();
			
			String sql = "update customer set first_name='"+updatedCustomer.getFirstName()
			+"', last_name='"+updatedCustomer.getLastName()
			+"' where customer="+Integer.toString(updatedCustomer.getCustomer());
			//System.out.println(sql);
			this.st.executeUpdate(sql);
			this.db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void finalize(){
    	   try {
    		   System.out.println("finalize");
    	   } catch(Exception ex) {
    		   System.out.println("CustomerDAO.finalize():" + ex.getMessage() );  
    	   }
    }
    
}
