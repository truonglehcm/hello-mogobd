package com.mogodb.demo.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mogodb.demo.entities.User;

interface UserRepository extends MongoRepository<User, Long> {
	Optional<User> findByName(String name);
	Optional<User> findByNameAndAge(String name, int age);
}
