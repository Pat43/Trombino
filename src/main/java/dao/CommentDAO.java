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
import model.User;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import util.HibernateUtil;

/**
 * @author Loic Teyssier
 *
 */
public class CommentDAO extends GenericDAO<Comment>{
	

	/**
	 */
	public CommentDAO() {
		super(Comment.class);
	}
	
	public Comment getById(int id, boolean loadFromUser, boolean loadToUser) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Comment comment = null;
		try {
			transaction = session.beginTransaction();
			comment = (Comment) session.get(Comment.class, id);
			if(loadFromUser)
				Hibernate.initialize(comment.getFromUser());
			if(loadToUser)
				Hibernate.initialize(comment.getToUser());
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
		return comment;
	}
	
	/**
	 * 
	 * @author loic
	 * @param commentList
	 * 
	 * Update les indexes des commentaires en fonction de l'ordre de la liste passée en paramètre.
	 * 
	 * @return
	 */
	public List<Comment> updateIndexes(List<Comment> commentList){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			for(int i=0; i<commentList.size();i++){
				commentList.get(i).setIndex(i);
				session.merge(commentList.get(i));
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
		return commentList;
	}
	
	public int getMemberCommentsCount(User member){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		int count = 0;
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Comment.class);
			criteria.add(Restrictions.eq("toUser", member));
			count = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
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
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getComments(User user, boolean loadFromUsers, boolean loadToUsers){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Comment> comments = new ArrayList<Comment>();
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Comment.class);
			criteria.add(Restrictions.eq("toUser", user));
			criteria.addOrder(Order.asc("index"));
			comments = criteria.list();
			if(loadFromUsers)
				for(Comment comment : comments)
					Hibernate.initialize(comment.getFromUser());
			if(loadToUsers)
				for(Comment comment : comments)
					Hibernate.initialize(comment.getToUser());
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
		return comments;
	}
	
	public List<Comment> getComments(User user){
		return this.getComments(user, false, false);
	}
	
	@SuppressWarnings("unchecked")
	public List<Comment> getCommentsLeft(User user, boolean loadFromUsers, boolean loadToUsers){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Comment> comments = new ArrayList<Comment>();
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Comment.class);
			criteria.add(Restrictions.eq("fromUser", user));
			comments = criteria.list();
			if(loadFromUsers)
				for(Comment comment : comments)
					Hibernate.initialize(comment.getFromUser());
			if(loadToUsers)
				for(Comment comment : comments)
					Hibernate.initialize(comment.getToUser());
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
		return comments;
	}
}
