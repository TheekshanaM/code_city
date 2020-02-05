package repositoryDataAnalyser;

import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class GetAllCommits {
	public ArrayList<String> getCommits() {
		try {
			Repository repository = Helper.openJGitCookbookRepository();
			ArrayList<String> commitList = new ArrayList<String>();
			try (Git git = new Git(repository)) {
                Iterable<RevCommit> commits = git.log().all().call();
                
                for (RevCommit commit : commits) {
                	String strCommit= commit.toString();
                	int index = strCommit.indexOf(' ');
                	String commitId = strCommit.substring(index+1, strCommit.indexOf(' ',index+1));
                	commitList.add(commitId);
                }
                git.getRepository().close();
                return commitList;
            }
        }catch (Exception e) {
			 return null;
		}
	}
}
