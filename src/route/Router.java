package route;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import dataExtractor.ClassDetails;
//import dataExtractor.JsonClass;
import jcity.JCity;
import jcity.Runner;
//import handler.Hadler;
import positionService.Node;
import repositoryDataAnalyser.CloneRemoteRepository;
import repositoryDataAnalyser.FileChanges;
import repositoryDataAnalyser.GetAllCommits;
import repositoryDataAnalyser.Helper;

@Path("/load")
public class Router {
//	String repoName="";
//	String link ="";
	@GET
	@Path("/loadcity/{rOwner}/{repo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadCodeCity(@PathParam("rOwner") String ROwner,@PathParam("repo") String repository) {
//		repoName = repository;
		CloneRemoteRepository repo = new CloneRemoteRepository();
		String url = "https://github.com/"+ROwner+"/"+repository;
//		link =url;
		repo.cloneRepo(url);
		
		HashMap<String, JCity> extractedData = new HashMap<String, JCity>();
		Runner runner = new Runner();
		extractedData = runner.runMethod(repository);
		Node node = new Node();
		Node.NodeInfo cityObj = node.create(extractedData, url, "master");
		
		ArrayList<String> commitList = new GetAllCommits().getCommits();
		cityObj.commits = commitList;
		
		Gson gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .create();
		System.out.println(gson.toJson(cityObj));
		return Response
	            .status(200)
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .entity(gson.toJson(cityObj))
	            .build();
	}
	
	@GET
	@Path("/changecity/{rOwner}/{repo}/{CommitId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeCodeCity(@PathParam("rOwner") String ROwner,@PathParam("repo") String repository, @PathParam("CommitId") String comitId) {
		
		try {
			String url = "https://github.com/"+ROwner+"/"+repository;
			Repository sorceFolder = Helper.openJGitCookbookRepository();
    		Git git = new Git(sorceFolder);
        	git.checkout().setName( comitId).call();
        	git.getRepository().close();
        	
        	HashMap<String, JCity> extractedData = new HashMap<String, JCity>();
    		Runner runner = new Runner();
    		extractedData = runner.runMethod(repository);
    		Node node = new Node();
    		Node.NodeInfo cityObj = node.create(extractedData, url, "master");
    		
    		Gson gson = new GsonBuilder()
    		        .excludeFieldsWithoutExposeAnnotation()
    		        .create();
    		
    		return Response
    	            .status(200)
    	            .header("Access-Control-Allow-Origin", "*")
    	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    	            .header("Access-Control-Allow-Credentials", "true")
    	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    	            .header("Access-Control-Max-Age", "1209600")
    	            .entity(gson.toJson(cityObj))
    	            .build();
    	}catch (Exception e) {
    		return Response
    	            .status(200)
    	            .header("Access-Control-Allow-Origin", "*")
    	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    	            .header("Access-Control-Allow-Credentials", "true")
    	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    	            .header("Access-Control-Max-Age", "1209600")
    	            .entity(null)
    	            .build();
		}
		
	}
	
	@GET
	@Path("/filedifferent/{class}/{newcommit}/{oldcommit}/{loc}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFileDifferent(@PathParam("class") String className,@PathParam("newcommit") String newCommit,@PathParam("oldcommit") String oldCommit, @PathParam("loc") int linesOfCode) {
		className=className.replaceAll(">", "/");
		FileChanges diffObj = new FileChanges();
		String diff = diffObj.getFileDiff(className, newCommit, oldCommit, linesOfCode);
		
		JSONParser parser = new JSONParser();
	   	 try {
				JSONObject json = (JSONObject) parser.parse(diff);
				return Response
	    	            .status(200)
	    	            .header("Access-Control-Allow-Origin", "*")
	    	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	    	            .header("Access-Control-Allow-Credentials", "true")
	    	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	    	            .header("Access-Control-Max-Age", "1209600")
	    	            .entity(json)
	    	            .build();
	   	 } catch (ParseException e) {
	   		return Response
    	            .status(200)
    	            .header("Access-Control-Allow-Origin", "*")
    	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
    	            .header("Access-Control-Allow-Credentials", "true")
    	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
    	            .header("Access-Control-Max-Age", "1209600")
    	            .entity(null)
    	            .build();
	   	 }
	}
}
