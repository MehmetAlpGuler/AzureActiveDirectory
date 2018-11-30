/**
 *
 */
package com.microsoft.aad.Service;

import com.microsoft.aad.dto.User;

/**
 * @author MehmetAlpGuler
 */
public interface UserService {
    User findByUsername(String username);
    User findByMail(String email);
}
