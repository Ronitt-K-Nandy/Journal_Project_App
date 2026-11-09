package com.newspaper.journal.config;
import com.newspaper.journal.entities.UserEntity;
import com.newspaper.journal.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.User;

@Slf4j
@Component
public class UserDetailServicesImpl implements UserDetailsService{

//	@Autowired
	private UserRepository userRepository;

	public UserDetailServicesImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity =userRepository.findUserByUsername(username);
		
		if(userEntity ==null)
			throw new UsernameNotFoundException("User Not Found ! !");


		User userDetails;
		userDetails = new User(userEntity.getUsername(),
								userEntity.getPassword(),
								userEntity.getRoles().stream()
										.map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + role)).toList());
		log.info("userDetails ----->>>>",userDetails.getUsername(),",,,,,,,,,,,,,,",userDetails.getPassword(),",,,,,,,,,,,,,,",userDetails.getAuthorities());
		return userDetails;
	}

}
