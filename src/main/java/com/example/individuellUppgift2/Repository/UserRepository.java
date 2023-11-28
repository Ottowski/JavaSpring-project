
// UserRepository interface extends JpaRepository for user database operations.
package com.example.individuellUppgift2.Repository;

import com.example.individuellUppgift2.AppEntity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    // Find a user by their username.
    Optional<AppUser> findByUsername(String username);
}
