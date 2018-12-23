package com.example.restaurant_details;

import com.example.restaurant_details.entity.RestaurantEntity;
import com.example.restaurant_details.mapper.RestaurantMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantMapperTest {

	@Autowired
	private RestaurantMapper restaurantMapper;

	@Test
	public void testInsert() throws Exception {
		restaurantMapper.insert(new RestaurantEntity("NN", "a123456"));
		restaurantMapper.insert(new RestaurantEntity("NNN", "b123456"));
		restaurantMapper.insert(new RestaurantEntity("MMM", "b123456"));

//		Assert.assertEquals(3, UserMapper.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<RestaurantEntity> users = restaurantMapper.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.toString());
		}
	}

	@Test
	public void testfindById() throws Exception {
		RestaurantEntity restaurantEntity= restaurantMapper.findById(Long.valueOf(2));
		if(restaurantEntity==null){
			System.out.println("is null");
		}else{
			System.out.println(restaurantEntity.toString());
		}
	}
//
//
//	@Test
//	public void testUpdate() throws Exception {
//		UserEntity user = UserMapper.getOne(6l);
//		System.out.println(user.toString());
//		user.setNickName("neo");
//		UserMapper.update(user);
//		Assert.assertTrue(("neo".equals(UserMapper.getOne(6l).getNickName())));
//	}

}