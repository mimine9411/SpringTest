package com.example.tutoperso.repositories;

import com.example.tutoperso.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long>
{
    Optional<User> findOneByUsername(String username);
}
