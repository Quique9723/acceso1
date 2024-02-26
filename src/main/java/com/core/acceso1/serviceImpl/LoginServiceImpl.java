package com.core.acceso1.serviceImpl;

import org.springframework.stereotype.Service;

import com.core.acceso1.data.model.Login;
import com.core.acceso1.service.ILoginService;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Service
@Getter
@Setter
@ToString
public class LoginServiceImpl implements ILoginService {
	
	public Login newEntity() {
		return new Login();
	}

}
