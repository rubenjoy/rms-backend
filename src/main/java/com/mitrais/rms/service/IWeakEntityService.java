package com.mitrais.rms.service;

/**
 *  Service to create, delete upddate Association 
 *  between strong entity and weak entity
 **/
public interface IWeakEntityService<StrongT,WeakT>
{// TODO exception when violated 
	/**
	 *  Create association
	 *  @param strongEntity to be associated
	 *  @param weakEntity to be created
	 *  @return WeakT created weak entity
	 **/
	public WeakT createAssociation(StrongT strongEntity, WeakT weakEntity);
	/**
	 *  Delete weak entity
	 *  @param weakEntity to be deleted
	 **/
	public void deleteAssociation(WeakT weakEntity);
	/**
	 *  Update weak entity
	 *  @param weakEntity to be updated 
	 *  @return WeakT updated weak entity
	 **/
	public WeakT updateAssociation(WeakT weakEntity);	
}