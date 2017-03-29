package com.mitrais.rms.controller;

public interface IPostController<T>
{
	public T save(Integer rootId, T dto);
}