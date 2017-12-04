package club.javalearn.crm.repository;

import club.javalearn.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-29
 **/
@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
