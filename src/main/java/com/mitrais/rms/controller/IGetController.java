package com.mitrais.rms.controller;

import java.util.List;

public interface IGetController<T>
{
	public T findByEmployeeIdAndId(Integer rootId, Integer nestedId); 
	public List<T> findByEmployeeId(Integer rootId);
}