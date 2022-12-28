package com.pinquiry.api;

import com.pinquiry.api.model.User;
import com.pinquiry.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"com.pinquiry.api.model"})
public class PinquiryApiApplication  {

	@Autowired
	UserRepository repository;


	public static void main(String[] args) {

		SpringApplication.run(PinquiryApiApplication.class, args);

	}


	@Bean
	public CommandLineRunner CommandLineRunnerBean() {


		return (args) -> {
			System.out.println("Started and In CommandLineRunnerImpl ");
			String username = null;
			String password = null;

			for (String arg : args) {
				System.out.println(arg);
				String [] s =  arg.split("=");
				List<String> a = new ArrayList<>(Arrays.asList(s));
				for(int i=0; i<a.size() ; i++){
					if(Objects.equals(a.get(i), "--admin-username")){
						username = a.get(i+1);
					}
					else if(Objects.equals(a.get(i), "--admin-password")){
						password = a.get(i+1);
						break;
					}
				}
			}
			if(username != null && password != null) {

				Pageable firstPageWithOneElement = PageRequest.of(0, 1);

				try{
					Page<User> pu = repository.findByUsername(username, Pageable.unpaged());
					if(pu.getTotalElements() == 0){
						User u = new User();
						u.setUsername(username);
						MessageDigest md = MessageDigest.getInstance("MD5");
						md.update(password.getBytes());
						byte[] digest = md.digest();
						String myHash = DatatypeConverter
								.printHexBinary(digest).toLowerCase();
						System.out.println(myHash);
						u.setPassword(myHash);
						u.setRole(User.UserRole.ADMIN);
						repository.save(u);
					}
				}catch (Exception e){
					System.out.println("Could not create admin " + e.getMessage());
				}

			}




		};
	}
}
