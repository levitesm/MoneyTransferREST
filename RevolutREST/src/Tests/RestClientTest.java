package Tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.server.Server;

import Application.Start;
import DataModel.Account;
import DataModel.Balance;

public class RestClientTest {

	private static WebTarget webTarget;
	private static Client client = ClientBuilder.newClient();
	
	
	public static void main(String[] args) throws Exception {
		  
		
        webTarget = client.target("http://localhost:8086/Revolut/");
        Response response;
        
        //Users check
        response= webTarget.path("/users").request().get();
        assert(response.getStatus()==200);
        response = webTarget.path("/users/create").request().get();
        assert(response.getStatus()==200);
        response = webTarget.path("/users/create/Mark").request().get();
        assert(response.getStatus()==200);
        response = webTarget.path("/users/create/Alex,Levites,60,12345678,some-nice-address").request().get();
        assert(response.getStatus()==200);
        Long userid = response.readEntity(Long.class);
        assert(userid==1000000003);
        response= webTarget.path("/users/1").request().get();
        assert(response.getStatus()==404);
        response= webTarget.path("/users/1000000002").request().get();
        assert(response.getStatus()==200);
        response= webTarget.path("/users/1000000002/delete").request().get();
        assert(response.getStatus()==200);
        response= webTarget.path("/users/1000000002").request().get();
        assert(response.getStatus()==404);
         //Accounts check
        response= webTarget.path("/accounts/add/1000000003").request().get();
        assert(response.getStatus()==200);
        response= webTarget.path("/accounts/add/1000000003").request().get();
        assert(response.getStatus()==200);
        response= webTarget.path("/accounts/add/1000000003").request().get();
        assert(response.getStatus()==200);
        response= webTarget.path("/users/1000000003/accounts").request().get();
        assert(response.getStatus()==200);
        Long[] accountlist = response.readEntity(Long[].class);
        assert(accountlist.length==3);
        response= webTarget.path("/accounts/1000000003/delete").request().get();
        assert(response.getStatus()==404);
        response= webTarget.path("/accounts/200000003/delete").request().get();
        assert(response.getStatus()==200);
        response= webTarget.path("/users/1000000003/accounts").request().get();
        assert(response.getStatus()==200);
        accountlist = response.readEntity(Long[].class);
        assert(accountlist.length==2);
        response= webTarget.path("/accounts/add/1000000001").request().get();
        assert(response.getStatus()==200);
        
        //Money check
        response= webTarget.path("/transactions/accounts/200000008/putmoney/1000,USD").request().get();
        assert(response.getStatus()==404);
        response= webTarget.path("/transactions/accounts/200000001/putmoney/1000,USD").request().get();
        String msg = response.readEntity(String.class);
        assert(msg.equals("Money input successful."));
        response= webTarget.path("/transactions/accounts/200000001/putmoney/1000,US").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Currency not supported!"));
        response= webTarget.path("/transactions/accounts/200000001/withdrawmoney/2000,USD").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("User only has 1000.0 USD"));
        response= webTarget.path("/transactions/accounts/200000001/withdrawmoney/500,USD").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Money withdraw successful."));
        response= webTarget.path("/transactions/accounts/200000001/withdrawmoney/500,EUR").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("User does not have Currecny: EUR"));
        response= webTarget.path("/transactions/accounts/200000001").request().get();
        List translist = response.readEntity(List.class);
        assert(translist.size()==2);
        
        //TRANSFER MONEY
        response= webTarget.path("/transactions/accounts/200000001/transfermoney/300,USD,USD").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Meaningless money transfer!"));
        response= webTarget.path("/transactions/accounts/200000001/transfermoney/300,USD,RUB").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Money transfer successful."));
        response= webTarget.path("/accounts/200000001/").request().get();
        List balances = response.readEntity(List.class);
        assert(balances.size()==2);//Now account has USD and RUB balances
        BigDecimal bal = (BigDecimal)((HashMap)balances.get(1)).get("balace");
        assert(bal.longValue()==20000.0);//300 USD got converted to 20000 RUB
        response= webTarget.path("/transactions/accounts/200000001,200000004/transfermoney/300,USD,RUB").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("User only has 200.0 USD"));
        response= webTarget.path("/transactions/accounts/200000001,200000004/transfermoney/100,USD,RUB").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Money transfer successful."));
        response= webTarget.path("/transactions/accounts/200000001,200000004/transfermoney/100,EUR").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("User does not have Currecny: EUR"));
        response= webTarget.path("/transactions/accounts/200000001,200000004/transfermoney/-100,USD").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Money amount should be positive."));
        response= webTarget.path("/transactions/accounts/200000001,200000004/transfermoney/100,USD").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Money transfer successful."));
        response= webTarget.path("/transactions/accounts/200000004/transfermoney/50,USD,EUR").request().get();
        msg = response.readEntity(String.class);
        assert(msg.equals("Money transfer successful."));
        response= webTarget.path("/accounts/200000004/").request().get();
        balances = response.readEntity(List.class);
        assert(balances.size()==3);//Now account has USD, EUR and RUB balances
        response= webTarget.path("/transactions/").request().get();
        translist = response.readEntity(List.class);
        assert(translist.size()==6);//There have been 6 transactions over all.
        
        System.out.println("All right!");
        client.close();
       
		 
	 }

}
