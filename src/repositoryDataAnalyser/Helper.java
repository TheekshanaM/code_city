package repositoryDataAnalyser;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class Helper {
	public static Repository openJGitCookbookRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        return builder
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir(new File("E:\\Document\\Theekshana\\Research\\gitrepo") )// scan up the file system tree
                .build();
    }
	
	public static Repository createNewRepository() throws IOException {
        // prepare a new folder
        File localPath = File.createTempFile("gitrepo", "",new File("E:\\Document\\Theekshana\\Research"));
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

        return repository;
    }
}
