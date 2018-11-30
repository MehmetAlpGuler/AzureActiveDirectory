/**
 *
 */
package com.microsoft.aad.Service;

import com.microsoft.aad.dto.User;
import com.microsoft.aad.util.GetPropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MehmetAlpGuler
 */
@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private GetPropertyValues getPropertyValues;

    @Override
    public User findByUsername(String username) {
        /*return (User) sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("username", username))
            .uniqueResult();*/
        if(username.equals(getPropertyValues.getProperties("username"))){
            return getUser();
        }
        return null;
    }

    @Override
    public User findByMail(String email) {
        /*return (User) sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email))
                .uniqueResult();*/
        if(email.equals(getPropertyValues.getProperties("azure.active.directory.example.return.mail"))){
            return getUser();
        }
        return null;
    }


    private User getUser(){
        User user = new User();
            user.setId(1);
            user.setUsername(getPropertyValues.getProperties("username"));
            //password MD5 = 5f4dcc3b5aa765d61d8327deb882cf99
            user.setPassword(getPropertyValues.getProperties("password.md5"));
            user.setConfirmPassword(getPropertyValues.getProperties("password.md5"));
            user.setEmail(getPropertyValues.getProperties("azure.active.directory.example.return.mail"));
            user.setFirstName("Mehmet alp");
            user.setLastName("Guler");
            user.setEnabled(true);


            List<String> list = new ArrayList<String>();
            list.add("ROLE_ADMIN");
            list.add("ROLE_USER");
            user.setRoles(list);
        return user;
    }

}
