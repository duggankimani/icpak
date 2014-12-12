package com.icpak.rest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.icpak.rest.models.base.PO;


public class BaseDao {
	
	@Inject Provider<EntityManager> provider;
	public EntityManager getEntityManager(){
		EntityManager em = provider.get();
		return em;
	}
	
	public void save(PO po){
		getEntityManager().persist(po);
	}
	
	public void delete(PO po){
		getEntityManager().remove(po);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSingleResultOrNull(Query query){
		T value = null;
		try{
			value = (T)query.getSingleResult();
		}catch(Exception e){
			if(!(e instanceof NoResultException)){
				e.printStackTrace();
			}
			
		}
		
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Query query,Integer offSet, Integer limit){
		List<T> values = null;
		
		if(limit==null || offSet==null){
			values = query.getResultList();
		}else{
			values = query.setFirstResult(offSet).setMaxResults(limit).getResultList();
		}
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Query query){
		List<T> values = null;
		values = query.getResultList();
		return values;
	}
	
	public <T> T getById(Class<T> clazz, long id){
		
		return getEntityManager().find(clazz, id);
	}

}
