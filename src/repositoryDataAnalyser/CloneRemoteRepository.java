package repositoryDataAnalyser;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;

public class CloneRemoteRepository {
	private static final String REMOTE_URL = "https://github.com/github/testrepo.git";

    public void cloneRepo(String repositoryURL) {
        // prepare a new folder for the cloned repository
    	try {
//    		File localPath = File.createTempFile("gitrepo", "",new File("E:\\Document\\Theekshana\\Research"));
//            if(!localPath.delete()) {
//                throw new IOException("Could not delete temporary file " + localPath);
//            }

            File path = new File(Helper.localPath);
    	    if (path.exists()) {
    	        FileUtils.deleteDirectory(path);
    	    }
            // then clone
//            System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
            try (Git result = Git.cloneRepository()
                    .setURI(repositoryURL)
                    .setDirectory(path) // #1
                    .call();) {
            	result.getRepository().close();
    	        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
    	        System.out.println("Having repository: " + result.getRepository().getDirectory());
            }

            // clean up here to not keep using more and more disk-space for these samples
//            FileUtils.deleteDirectory(localPath);
    	}catch (Exception e) {
			System.out.println(e);
		}
    }
}
