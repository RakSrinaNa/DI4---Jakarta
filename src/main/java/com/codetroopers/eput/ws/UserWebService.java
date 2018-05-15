/*
 * Copyright 2016 Code-Troopers.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codetroopers.eput.ws;

import com.codetroopers.eput.domain.entities.User;
import com.codetroopers.eput.services.UserService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sample REST WebService, it will be under "ws" app path
 */
//tag::class[]
@ApplicationPath("ws") // <1>
@Path("/users") // <2>
@Produces(MediaType.APPLICATION_JSON) // <3>
public class UserWebService extends Application
{
	@Inject
	UserService userService;
	
	class RESTUser
	{
		private final Long id;
		private final String name;
		
		public RESTUser(User user)
		{
			this.id = user.id;
			this.name = user.name;
		}
		
		public Long getId()
		{
			return id;
		}
		
		public String getName()
		{
			return name;
		}
	}
	
	@GET // <4>
	public List<RESTUser> users()
	{
		return userService.all().stream().map(RESTUser::new).collect(Collectors.toList());
	}
	
	@POST // <4>
	@Consumes({MediaType.APPLICATION_JSON})
	public RESTUser create(@QueryParam("name") String name, // <5>
			@QueryParam(value = "email") String email)
	{
		User user = new User(name, email);
		user.password = "<FROMWS>";
		return new RESTUser(userService.create(user));
	}
}
//end::class[]