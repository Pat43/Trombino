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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table (name="tempuser")
public class TempUser {

	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column (name="id_tempuser") @Getter @Setter
	int id;		
	@Column (name="firstname_tempuser") @Getter @Setter
	String firstName;	
	@Column (name="lastName_tempuser") @Getter @Setter
	String lastName;	
	@Column (name="nickname_tempuser") @Getter @Setter
	String nickname;
	@Column (name="email_tempuser") @Getter @Setter
	String email;
	@Column (name="pass_tempuser") @Getter @Setter
	String hashedPassword;
	@Column (name="hide_email_tempuser") @Getter @Setter
	boolean hide_email;
}