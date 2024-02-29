package com.core.acceso1.tools;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;

@Getter
@Setter
@ToString
public class FormGenericPiece<T> implements Serializable{
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private List<T> itemList;
	private Class<T> realType;
	
	

}
