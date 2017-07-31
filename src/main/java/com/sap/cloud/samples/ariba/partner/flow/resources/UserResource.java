package com.sap.cloud.samples.ariba.partner.flow.resources;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.samples.ariba.partner.flow.authentication.AuthenticationProvider;
import com.sap.cloud.samples.ariba.partner.flow.dtos.UserDto;
import com.sap.security.auth.login.LoginContextFactory;
import com.sap.security.um.user.PersistenceException;
import com.sap.security.um.user.User;

@Path("/user")
public class UserResource {

	private static final String DEBUG_RETRIEVING_USER_DETAILS = "Retrieving user details...";
	private static final String DEBUG_RETRIEVED_USER_DETAILS_FOR_USER = "Retrieved user details for user [{}].";

	private static final String ERROR_PROBLEM_OCCURED_WHILE_RETRIEVING_USER_DETAILS = "Problem occured while retrieving user details.";

	private final static Logger logger = LoggerFactory.getLogger(UserResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response retrieveUserDetails() {
		logger.debug(DEBUG_RETRIEVING_USER_DETAILS);

		Response response = null;
		try {
			User currentUser = AuthenticationProvider.retrieveUserProvider().getCurrentUser();
			UserDto userDto = new UserDto();
			userDto.setName(currentUser.getName());
			
			logger.debug(DEBUG_RETRIEVED_USER_DETAILS_FOR_USER, currentUser.getName());
			response = Response.status(Response.Status.OK).entity(userDto).build();
		} catch (PersistenceException e) {
			logger.error(ERROR_PROBLEM_OCCURED_WHILE_RETRIEVING_USER_DETAILS, e);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

	@POST
	@Path("logout")
	public Response logoutUser() {
		logger.debug("Logging out current user...");
		
		try {
			LoginContext loginContext = LoginContextFactory.createLoginContext();
			loginContext.logout();

			return Response.status(Response.Status.OK).build();
		} catch (LoginException e) {
			logger.error("Problem occured while logging out the current user.", e);
			throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
