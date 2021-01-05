package com.ers.dao;

import java.util.List;

public interface GenericDAO <E> {
	
	public E selectByName(String name);
	public E selectById(int id);
	public void insert(E entity);
	public void update(E entity);
	public List<E> selectAll();

}
