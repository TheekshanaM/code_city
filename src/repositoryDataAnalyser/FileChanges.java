package repositoryDataAnalyser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileChanges {
	
	public ArrayList<String> getChangedFile(String newCommit,String oldCommit) {
		try {
			try (Repository repository = Helper.openJGitCookbookRepository()) {
	            // the diff works on TreeIterators, we prepare two for the two branches
	            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, oldCommit);
	            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, newCommit);
	            
	            DiffFormatter diffFormatter = new DiffFormatter( DisabledOutputStream.INSTANCE ); 
	            diffFormatter.setRepository( repository );
	            	 
	            Git git = new Git(repository);
	            List<DiffEntry> diffEntries = git.diff().
	                       setOldTree(oldTreeParser).
	                       setNewTree(newTreeParser).
	                       call();
	            
	            ArrayList<String> fileList = new ArrayList<String>();
	            if(diffEntries.size() !=0) {
	            	for (DiffEntry entry : diffEntries) {
	                    fileList.add(entry.getNewPath());
//	                    System.out.println(entry.getOldPath()+"  "+ entry.getNewPath());
	                }
	            	diffFormatter.close();
		            git.close();
		            return fileList;
	            }else {
					return null;
				}
	            
	            
			}
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public String getFileDiff(String classPath, String newCommit,String oldCommit,int lastLine) {
        
		try {
			try (Repository repository = Helper.openJGitCookbookRepository()) {
	            // the diff works on TreeIterators, we prepare two for the two branches
	            AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, oldCommit);
	            AbstractTreeIterator newTreeParser = prepareTreeParser(repository, newCommit);
	            
	            try( DiffFormatter diffFormatter = new DiffFormatter( DisabledOutputStream.INSTANCE ) ) {
	            	 diffFormatter.setRepository( repository );
	            	 
	            	 Git git = new Git(repository);
	            	 List<DiffEntry> diffEntries = git.diff().
	                       setOldTree(oldTreeParser).
	                       setNewTree(newTreeParser).
	                       setPathFilter(PathFilter.create(classPath)).
	                       call();
//	            	 FileHeader fileHeader = diffFormatter.toFileHeader( diffEntries.get( 0 ) );
//	            	 EditList x=fileHeader.toEditList();
	            	 if(diffEntries.size()==0) {
	            		 String strObj = "{\"obj\":[]}";
	            		 return strObj;
	            	 }
	            	 
	            	 int lastIndex=0;
	            	 int beginA;
	            	 int endA;
	            	 int beginB;
	            	 int endB;
	            	 String insert = "{\"name\": \"Insert\",\"data\": [";
	            	 String delete = "{\"name\": \"Delete\",\"data\": [";
	            	 String notChanged = "{\"name\": \"Not Changed\",\"data\": [";
	            	 boolean insertStatus=false,deleteStatus=false,notChangeStatus=false;
	            	 
	            	 for (DiffEntry diff : diffEntries) {
	            		 for (Edit edit : diffFormatter.toFileHeader(diff).toEditList()) {
	            			 if(edit.getType().toString()=="INSERT") {
	           		    	  beginB = edit.getBeginB();
	           		    	  endB = edit.getEndB();
	           		    	  insert += "{\"x\": \"Current version\",\"y\": ["+beginB+","+ endB+"]},";
	           		    	  insertStatus=true;
	           		    	  if(beginB !=lastIndex) {
	           		    		  notChanged += "{\"x\": \"Current version\",\"y\": ["+lastIndex+","+ beginB+"]},";
	           		    		  notChangeStatus=true;
	           		    	  }
	           		    	  lastIndex=endB;
	           		      }
	           		      if(edit.getType().toString()=="DELETE") {
	           		    	  beginA = edit.getBeginA();
	           		    	  endA = edit.getEndA();
	           		    	  delete += "{\"x\": \"Older version\",\"y\": ["+beginA+","+ endA+"]},";
	           		    	  deleteStatus=true;
	           		    	  if(beginA !=lastIndex) {
	           		    		  notChanged += "{\"x\": \"Current version\",\"y\": ["+lastIndex+","+ beginA+"]},";
	           		    		  notChangeStatus=true;
	           		    	  }
	           		    	  lastIndex=endA;
	           		      }
	           		      if(edit.getType().toString()=="REPLACE") {
	           		    	  beginA = edit.getBeginA();
	           		    	  endA = edit.getEndA();
	           		    	  beginB = edit.getBeginB();
	           		    	  endB = edit.getEndB();
	           		    	  delete += "{\"x\": \"Older version\",\"y\": ["+beginA+","+ endA+"]},";
	           		    	  insert += "{\"x\": \"Current version\",\"y\": ["+beginB+","+ endB+"]},";
	           		    	  insertStatus=true;
	           		    	  deleteStatus=true;
	           		    	  if(beginB !=lastIndex) {
	           		    		  notChanged += "{\"x\": \"Current version\",\"y\": ["+lastIndex+","+ beginB+"]},";
	           		    		  notChangeStatus=true;
	           		    	  }
	           		    	  lastIndex=endB;
	           		      }
	            		 }
	            	 }

	            	 if(lastIndex < lastLine && lastIndex != 0) {
	            		 notChanged += "{\"x\": \"Current version\",\"y\": ["+lastIndex+","+ lastLine+"]},";
	            		 notChangeStatus=true;
	            	 }
	            	 if(insertStatus) {
	            		 insert = insert.substring(0,(insert.length()-1));
	            	 }
	            	 insert += "]}";
	            	 if(deleteStatus) {
	            		 delete = delete.substring(0,(delete.length()-1));
	            	 }
	            	 delete += "]}";
	            	 if(notChangeStatus) {
	            		 notChanged = notChanged.substring(0,(notChanged.length()-1));
	            	 }
	            	 notChanged += "]}";
	            	 
	            	 String strObj = "{\"obj\":["+insert+","+notChanged+","+delete+"]}";
//	            	 System.out.println(x);
	            	 
	            	 return strObj;
	            }
	            
	        }
		} catch (Exception e) {
			return null;
		}
    }
	private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }
}

