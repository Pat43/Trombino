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

import model.Comment;
import model.TempUser;
import model.User;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

/**
 * @author Loic Teyssier
 *
 */
public class UserDAO extends GenericDAO<User> {
	
	/**
	 * 
	 */
	public UserDAO() {
		super(User.class);
	}
	
	public User getById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		try {
			transaction = session.beginTransaction();
			user = (User) session.get(User.class, id);
			if(user != null){
				Hibernate.initialize(user.getComments());
				Hibernate.initialize(user.getCommentsLeft());
			}
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
	
	public User getByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			user = (User) criteria.uniqueResult();
			if(user != null){
				Hibernate.initialize(user.getComments());
				Hibernate.initialize(user.getCommentsLeft());
			}
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
	
	public User connectUser(String login, String password) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User user = null;
		try {
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", login));
			user = (User) criteria.uniqueResult();
		   
			if( user == null || ! user.getHashedPassword().equals(util.Encryption.crypt(password)))
				return null;
			
			Hibernate.initialize(user.getComments());
			Hibernate.initialize(user.getCommentsLeft());

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
	
	@SuppressWarnings("unchecked")
	public List<User> filterUsers(String search){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<User> members = null;
		try {
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.disjunction()
			.add(Restrictions.like("firstName", "%"+search+"%"))
			.add(Restrictions.like("lastName", "%"+search+"%"))
			.add(Restrictions.like("nickname", "%"+search+"%")));
		//	criteria.add(Restrictions.eq("admin", (byte)0));
			
			members = criteria.list();
			
			for(User user : members){
				Hibernate.initialize(user.getComments());
				Hibernate.initialize(user.getCommentsLeft());
			}

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
		return members;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getMembersWithoutUserComment(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<User> members = new ArrayList<User>();
		try {
			transaction = session.beginTransaction();
			
			List<User> membersWithUsercomments = new ArrayList<User>();
			Criteria criteria1 = session.createCriteria(Comment.class);
			criteria1.add(Restrictions.eq("fromUser", user));
			criteria1.setProjection(Projections.property("toUser"));
			membersWithUsercomments = criteria1.list();
			
			Criteria criteria2 = session.createCriteria(User.class);
			criteria2.add(Restrictions.ne("id", user.getId()));
			for(User member : membersWithUsercomments)
				criteria2.add(Restrictions.ne("id", member.getId()));
			members = criteria2.list();
			
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
		return members;
	}
	
	public boolean isEmailAvailable(String email, User user){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		boolean available=false;
		try {
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("email", email));
			if(user!=null)
				criteria.add(Restrictions.ne("id", user.getId()));
			User u = (User) criteria.uniqueResult();

			if(u==null){
		
				criteria = session.createCriteria(TempUser.class);
				criteria.add(Restrictions.eq("email", email));
				if(user!=null)
					criteria.add(Restrictions.ne("id", user.getId()));
				TempUser tu = (TempUser) criteria.uniqueResult();
				
				if(tu==null)
					available=true;
			}
			
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
		return available;
	}
}
