package Services;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import DataModel.User;
import Exceptions.NoSuchUserException; 
import Management.UserManager;


@ApplicationPath("/")
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserServices extends Application{
	
	
	@GET
	@Path("/")
	public Response getUsers()
	{
		return Response.ok(UserManager.getAllUserIds()).build();
	}
	
	
	@GET
	@Path("/{id}")
	public Response getUserById(@PathParam("id") Long id)
	{
	
		try {User user = UserManager.getUserById(id);
			
			return Response.ok(user).build();
		}catch (NoSuchUserException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();}
	}
	
	
	@GET
	@Path("/{id}/accounts")
	public Response getUserAccounts(@PathParam("id") Long id)
	{
	
		try {User user = UserManager.getUserById(id);
			
			return Response.ok(user.getAccounts()).build();
		}catch (NoSuchUserException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();}
	}
	
	
	@GET
	@Path("/{id}/delete")
	public Response deleteUserById(@PathParam("id") Long id)
	{
		
		try {UserManager.deleteUserById(id);
			
			return Response.ok("User removed.").build();
		}catch (NoSuchUserException e) {return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();}
	}	
	
	@GET
	@Path("/create/{fname},{lname},{age},{telNum},{address}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(@PathParam("fname") String fname, @PathParam("lname") String lname, @PathParam("age") Integer age, @PathParam("telNum") Long telNum, @PathParam("address") String address)
	{
		
		Long usid = UserManager.addNewUser(fname, lname, age, telNum, address);
			
			return Response.ok(usid).build();
	}
		
		@GET
		@Path("/create/{fname}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response createUser(@PathParam("fname") String fname)
		{
			
			Long usid = UserManager.addNewUser(fname);
				
				return Response.ok(usid).build();
		}

		@GET
		@Path("/create")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response createUser()
		{
			
			Long usid = UserManager.addNewUser();
				
				return Response.ok().entity(usid).build();
		}

}
