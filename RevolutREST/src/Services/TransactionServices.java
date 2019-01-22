package Services;


import javax.ws.rs.*;
import javax.ws.rs.core.*;


import Exceptions.NoSuchAccountException;
import Exceptions.NoSuchCerrencyException;
import Exceptions.NotEnoughMoneyException;
import Exceptions.WrongAmountException;
import Management.TransactionManager;
import Types.Currency;


@ApplicationPath("/")
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionServices extends Application{
	
	
	@GET
	@Path("/transactions")
	public Response getTransactions()
	{
		return Response.ok(TransactionManager.getTransactionList()).build();
	}
	
	
	@GET
	@Path("/transactions/accounts/{id}")
	public Response getTransactionsByAccountId(@PathParam("id") Long id)
	{
	
		try {
			
			return Response.ok(TransactionManager.getTransactionList(id)).build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();}
	}
		
	@GET
	@Path("/transactions/accounts/{id}/putmoney/{sum},{cur}")
	public Response putMoneyByAccoundId(@PathParam("id") Long id, @PathParam("sum") Double sum, @PathParam("cur") String cur)
	{
		boolean curFlag=false;
		Currency currency=Currency.USD;
		for(Currency c:Currency.values()) if (c.name().equals(cur)) {curFlag=true;currency=c;break;}
		
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
		
		try {TransactionManager.putMoney(id, sum, currency);
			
			return Response.ok("Money input successful.").build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();} 
		 catch (WrongAmountException e) {
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}	
	
	
	@GET
	@Path("/transactions/accounts/{id}/withdrawmoney/{sum},{cur}")
	public Response withdrawMoneyByAccoundId(@PathParam("id") Long id, @PathParam("sum") Double sum, @PathParam("cur") String cur)
	{
		boolean curFlag=false;
		Currency currency=Currency.USD;
		for(Currency c:Currency.values()) if (c.name().equals(cur)) {curFlag=true;currency=c;break;}
		
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
		
		try {TransactionManager.withdrawMoney(id, sum, currency);
			
			return Response.ok("Money withdraw successful.").build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();} 
		 catch (WrongAmountException | NotEnoughMoneyException | NoSuchCerrencyException e) {
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}	
	
	
	@GET
	@Path("/transactions/accounts/{id}/transfermoney/{sum},{cur1},{cur2}")
	public Response transferMoneyByAccoundId(@PathParam("id") Long id, @PathParam("sum") Double sum, @PathParam("cur1") String cur1, @PathParam("cur2") String cur2)
	{
		boolean curFlag=false;
		Currency currency1=Currency.USD;
		for(Currency c:Currency.values()) if (c.name().equals(cur1)) {curFlag=true;currency1=c;break;}
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
		
		Currency currency2=Currency.USD;
		curFlag=false;
		for(Currency c:Currency.values()) if (c.name().equals(cur2)) {curFlag=true;currency2=c;break;}
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
				
		try {TransactionManager.transferMoney(id, sum, currency1, currency2);
			
			return Response.ok("Money transfer successful.").build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();} 
		 catch (WrongAmountException | NotEnoughMoneyException | NoSuchCerrencyException e) {
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}	
	
	
	@GET
	@Path("/transactions/accounts/{id1},{id2}/transfermoney/{sum},{cur1},{cur2}")
	public Response transferMoneyByAccoundId(@PathParam("id1") Long id1,@PathParam("id2") Long id2, @PathParam("sum") Double sum, @PathParam("cur1") String cur1, @PathParam("cur2") String cur2)
	{
		boolean curFlag=false;
		Currency currency1=Currency.USD;
		for(Currency c:Currency.values()) if (c.name().equals(cur1)) {curFlag=true;currency1=c;break;}
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
		
		Currency currency2=Currency.USD;
		curFlag=false;
		for(Currency c:Currency.values()) if (c.name().equals(cur2)) {curFlag=true;currency2=c;break;}
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
				
		try {TransactionManager.transferMoney(id1,id2, sum, currency1, currency2);
			
			return Response.ok("Money transfer successful.").build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();} 
		 catch (WrongAmountException | NotEnoughMoneyException | NoSuchCerrencyException e) {
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}	
	
	
	@GET
	@Path("/transactions/accounts/{id1},{id2}/transfermoney/{sum},{cur}")
	public Response transferMoneyByAccoundId(@PathParam("id1") Long id1,@PathParam("id2") Long id2, @PathParam("sum") Double sum, @PathParam("cur") String cur)
	{
		boolean curFlag=false;
		Currency currency=Currency.USD;
		for(Currency c:Currency.values()) if (c.name().equals(cur)) {curFlag=true;currency=c;break;}
		if(!curFlag)return Response.status(Response.Status.BAD_REQUEST).entity("Currency not supported!").build();
		
		try {TransactionManager.transferMoney(id1,id2, sum, currency);
			
			return Response.ok("Money transfer successful.").build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();} 
		 catch (WrongAmountException | NotEnoughMoneyException | NoSuchCerrencyException e) {
			 return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		 }
	}	
	
	
}
