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

import model.mailsHashedCodes.ResetPwdCode;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

/**
 * @author Loic Teyssier
 *
 */
public class ResetPwdCodeDAO extends GenericDAO<ResetPwdCode> {

	/**
	 */
	public ResetPwdCodeDAO() {
		super(ResetPwdCode.class);
	}
	
	public ResetPwdCode getById(String string) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		ResetPwdCode code = null;
		try {
			transaction = session.beginTransaction();
			code = (ResetPwdCode) session.get(ResetPwdCode.class, string);
			if(code != null)
				Hibernate.initialize(code.getUser());
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
		return code;
	}
	
}
