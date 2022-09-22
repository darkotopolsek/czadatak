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
        File f = new File(imagePath);
        //System.out.println(imagePath);
        //System.out.println("Working dir: " + System.getProperty("user.dir") + f.getParent());
        
        String workingdir = System.getProperty("user.dir");
        f = new File(workingdir + f.getParent());
        // probably not a good idea but it's not production
        if(!f.getPath().endsWith("photos")) {
            FileUtils.cleanDirectory(f);
            FileUtils.deleteDirectory(f);
            //System.out.println("deleting");
        } 
        repo.deleteById(id);
    }
}
