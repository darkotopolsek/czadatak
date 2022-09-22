package com.omicron.ctest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repo;

    public List<User> listAll() {
        return repo.findAll();
    }

    public void save(User user) {
        repo.save(user);
    }

    public User get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) throws IOException {
        String imagePath = this.get(id).getPhotosImagePath();
        //String imagePath = this.get(id).getPhoto();
        File f = new File(imagePath);
        System.out.println("Image path: " + imagePath);
        //System.out.println("Working dir: " + System.getProperty("user.dir") + f.getParent());

        File delFile = null;
        String workingdir = System.getProperty("user.dir");
        delFile = new File(workingdir + "\\user-photos\\" + String.valueOf(id));
        // probably not a good idea but it's not production
        if (!f.getPath().endsWith("photos")) {
            if (delFile.exists()) {
                FileUtils.cleanDirectory(delFile);
                FileUtils.deleteDirectory(delFile);
            }
            //System.out.println("deleting");
        }
        repo.deleteById(id);
    }
}
