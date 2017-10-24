package com.mogodb.demo.repo;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mogodb.demo.entities.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloMogobdApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	
    @Before
    public void setUp() throws Exception {
        User user1= new User();
        user1.setName("truong");
        user1.setAge(28);
        User user2= new User();
        user2.setName("Whois");
        user2.setAge(0);
        //save user, verify has ID value after save
        assertNull(user1.getId());
        assertNull(user2.getId()); //null before save
        
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        
        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
    }
    
    @Test
    public void testFetchData() {
    	
        /*Test data retrieval*/
    	Optional<User> userA = userRepository.findByName("truong");
        assertTrue(userA.isPresent());
        userA.ifPresent(u -> assertEquals(28, u.getAge()));
        
        /*Get all users, list should only have two*/
        int numUser = Optional.ofNullable(userRepository.findAll()).map(List::size).orElse(0);
        assertEquals(numUser, 2);
    }
 
    @Test
    public void testDataUpdate(){
        /*Test update*/
    	Optional<User> userB = userRepository.findByName("truong");
    	
    	userB.ifPresent(u -> {
    		u.setAge(20);
    		userRepository.save(u);
    	});
    	
    	Optional<User> userC= userRepository.findByName("truong");
        assertTrue(userC.isPresent());
        userC.ifPresent(u -> assertEquals(20, u.getAge()));
    }
 
    @After
    public void tearDown() throws Exception {
      this.userRepository.deleteAll();
    }
}
