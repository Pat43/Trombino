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
@Table (name="comment")
public class Comment{
	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	@Column (name="id_comment") @Getter @Setter
	int id;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="from_user_id_comment")
	User fromUser;
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name="to_user_id_comment") @Getter
	User toUser;
	@Column (name="text_comment") @Getter @Setter
	String text;
	@Column (name="index_comment") @Getter @Setter
	int index;
	
	public void setToUser(User toUser){
		this.toUser = toUser;
		toUser.getComments().add(this);
	}
	public void setFromUser(User fromUser){
		this.fromUser = fromUser;
		fromUser.getCommentsLeft().add(this);
	}
	
	public User getFromUser(){
		return this.fromUser;
	}
	
}
