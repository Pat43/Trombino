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

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;
import model.mailsHashedCodes.RegisterCode;

/**
 * @author Loic Teyssier
 *
 */
public class RegisterCodeDAO extends GenericDAO<RegisterCode> {

	/**
	 */
	public RegisterCodeDAO() {
		super(RegisterCode.class);
	}
	
	public RegisterCode getById(String string) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		RegisterCode code = null;
		try {
			transaction = session.beginTransaction();
			code = (RegisterCode) session.get(RegisterCode.class, string);
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
