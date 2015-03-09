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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.mailsHashedCodes.RegisterCode;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Entity
@Table (name="user")
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column (name="id_user") @Getter @Setter
	int id;		
	@Column (name="firstname_user") @Getter @Setter
	String firstName;	
	@Column (name="lastName_user") @Getter @Setter
	String lastName;	
	@Column (name="nickname_user") @Getter @Setter
	String nickname;	
	@Column (name="department_user") @Getter @Setter
	String department;	
	@Column (name="specialisation_user") @Getter @Setter
	String specialisation;	
	@Column (name="second_specialisation_user") @Getter @Setter
	String secondSpecialisation;	
	@Column (name="email_user") @Getter @Setter
	String email;	
	@Column (name="hashedPassword_user") @Getter @Setter
	String hashedPassword;	
	@Column (name="birthday_user") @Getter @Setter
	Date birthday;	
	@Column (name="phone_user") @Getter @Setter
	String phone;	
	@Column (name="godfathers_user") @Getter @Setter
	String godfathers;	
	@Column (name="godsons_user") @Getter @Setter
	String godsons;	
	@Column (name="personalPhrase_user") @Getter @Setter
	String personalPhrase;	
	@Column (name="hide_email_user") @Getter @Setter
	boolean hide_email;	
	@Column (name="admin_user") @Getter @Setter
	boolean admin;	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fromUser")
	@Getter
	@OnDelete(action=OnDeleteAction.CASCADE)
	List<Comment> commentsLeft;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "toUser")
	@Getter
	@OnDelete(action=OnDeleteAction.CASCADE)
	List<Comment> comments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@Getter
	List<UserAsso> userAssos;
	
	@OneToOne (fetch = FetchType.LAZY, mappedBy = "user")
	RegisterCode registerCode;
	

	public User(TempUser tempUser){

		this.firstName = tempUser.getFirstName();
		this.lastName = tempUser.getLastName();
		this.nickname = tempUser.getNickname();
		this.email = tempUser.getEmail();
		this.hashedPassword = tempUser.getHashedPassword();
		this.hide_email = tempUser.isHide_email();
	}

	public String getNicknameOrFirstName() {
		if(nickname != null && !nickname.equals(""))
			return nickname;
		else
			return firstName;
	}
	public String getNicknameOrFullName() {
		if(nickname != null && !nickname.equals(""))
			return nickname;
		else
			return firstName+" "+lastName;
	}
	public String getBirthdayAsString() {
		if(getBirthday() == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(birthday);
	}
	public String getAllSpecialisations(){
		String allSpecs = getSpecialisation();
		if(getSecondSpecialisation() != null && !getSecondSpecialisation().equals(""))
			allSpecs += " - "+getSecondSpecialisation();
		
		return allSpecs;
	}
	
}