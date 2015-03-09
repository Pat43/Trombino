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
package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Loic Teyssier
 *
 */

@NoArgsConstructor
@Entity
@Table (name="userasso")
public class UserAsso implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column (name="id_userasso") @Getter @Setter
	int id;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="user_userasso") @Getter
	User user;
	@Column (name="asso_userasso") @Getter @Setter
	int asso;
	@Column (name="role_userasso") @Getter @Setter
	String role;
	

	public UserAsso(User user, int asso, String role) {
		this.user = user;
		this.asso = asso;
		this.role = role;
	}

	
	public void setUser(User user){
		this.user = user;
		user.getUserAssos().add(this);
	}
}
