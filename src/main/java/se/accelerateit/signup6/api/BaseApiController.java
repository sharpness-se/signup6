package se.accelerateit.signup6.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
/*
Of course, the implementation detail worth noting here is the use of the @CrossOrigin annotation. As the name implies, the annotation enables Cross-Origin Resource Sharing (CORS) on the server.

This step isn't always necessary, but since we're deploying our Angular frontend to http://localhost:4200, and our Boot backend to http://localhost:8080, the browser would otherwise deny requests from one to the other.
 */
public class BaseApiController {}
