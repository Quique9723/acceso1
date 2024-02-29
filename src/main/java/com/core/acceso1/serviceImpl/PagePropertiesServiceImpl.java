package com.core.acceso1.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import lombok.Getter;

@Service
@Getter
@PropertySource("classpath:properties/other.properties")
public class PagePropertiesServiceImpl {
	
	private List<Integer> pageSizeList;
	private Integer numPartials;
	
	public PagePropertiesServiceImpl(
			@Value("#{'${html.keypadGenericPage.page.size:1,4,6}'.split(',')}") List<Integer> pageSizeList,
			@Value("#{${html.keypadGenericPage.page.numPartials:7}}") Integer numPartials
			) {
		this.numPartials = numPartials;
		this.pageSizeList = pageSizeList;
	}
	
	
	

}
