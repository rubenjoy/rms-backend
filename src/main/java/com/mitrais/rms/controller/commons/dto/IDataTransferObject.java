package com.mitrais.rms.controller.commons.dto;

public interface IDataTransferObject<EntityT>
{
	/**
	 *  create unpersisted Entity from DTO
	 *  @return EntityT
	 **/
	public EntityT createEntity();
	/**
	 *  create DTO from entity
	 *  @param entity
	 *  @return IDataTransferObject
	 **/
	// public static <EntityT> IDataTransferObject fromEntity(EntityT entity);
	/**
	 *  update Entity instance from DTO
	 *  @param entity
	 *  @return EntityT
	 **/
	public EntityT updateEntity(EntityT entity);
}