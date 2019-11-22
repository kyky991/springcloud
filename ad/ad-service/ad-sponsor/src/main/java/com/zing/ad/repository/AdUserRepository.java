package com.zing.ad.repository;

import com.zing.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Zing
 * @date 2019-11-22
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    AdUser findByUsername(String username);

}
