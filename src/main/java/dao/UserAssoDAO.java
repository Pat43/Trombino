/*
 * Copyright (C) 2014, Teyssier Loic

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

 */
package dao;


import java.util.ArrayList;
import java.util.List;

import model.User;
import model.UserAsso;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

/**
 * @author Loic Teyssier
 *
 */
public class UserAssoDAO extends GenericDAO<UserAsso>{
	
	
	/**
	 */
	public UserAssoDAO() {
		super(UserAsso.class);
	}

	@SuppressWarnings("unchecked")
	public List<UserAsso> getUserAssos(User user){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<UserAsso> userAssos = new ArrayList<UserAsso> ();
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(UserAsso.class);
			criteria.add(Restrictions.eq("user", user));
			userAssos = criteria.list();
			transaction.commit();
		}catch(HibernateException he) {
			he.printStackTrace();
		}finally {
			if(session != null) {
				try { 
					session.close(); 
				} catch(HibernateException he){
					he.printStackTrace();
					}
			}
		}
		
		return userAssos;
	}
	
	public User editUserAssos(User user, List<UserAsso> userAssos){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		try {
			transaction = session.beginTransaction();
			List<UserAsso> userAssosToDelete = getUserAssos(user);
			for(UserAsso userAsso : userAssosToDelete)
				session.delete(userAsso);
			
			for(UserAsso userAsso : userAssos)
				session.persist(userAsso);
			transaction.commit();
		}catch(HibernateException he) {
			he.printStackTrace();
		}finally {
			if(session != null) {
				try { 
					session.close(); 
				} catch(HibernateException he){
					he.printStackTrace();
					}
			}
		}
		
		return user;
		
	}

}
