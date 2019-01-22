package Services;


import javax.ws.rs.*;
import javax.ws.rs.core.*;

import DataModel.Account;
import Exceptions.NoSuchAccountException;
import Exceptions.NoSuchUserException;
import Management.AccountManager;


@ApplicationPath("/")
@Path("accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountServices extends Application{
	
	
	@GET
	@Path("/")
	public Response getAccounts()
	{
		return Response.ok(AccountManager.getAllAccountIds()).build();
	}
	
	
	@GET
	@Path("/{id}")
	public Response getAccountBalancesById(@PathParam("id") Long id)
	{
	
		try {Account account = AccountManager.getAccountById(id);
			
			return Response.ok(account.getBalances()).build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();}
	}
		
	@GET
	@Path("/{id}/delete")
	public Response deleteAccountById(@PathParam("id") Long id)
	{
		
		try {AccountManager.deleteAccountById(id);
			
			return Response.ok("Account removed.").build();
		}catch (NoSuchAccountException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();}
	}	
	
		@GET
		@Path("/add/{userId}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response createAccountForUser(@PathParam("userId") Long userId)
		{
			
			Long accId;
			try {
				accId = AccountManager.addNewAccount(userId);
				
				return Response.ok(accId).build();
			} catch (NoSuchUserException e) {
				return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
			}
				
				
		}

		
}
