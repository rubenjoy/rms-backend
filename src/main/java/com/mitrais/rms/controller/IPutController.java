package com.mitrais.rms.controller;

public interface IPutController<T>
{
	public T save(Integer rootId, Integer nestedId, T dto);
}