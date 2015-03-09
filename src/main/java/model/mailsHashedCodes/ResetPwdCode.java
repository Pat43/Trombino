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
package model.mailsHashedCodes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.User;


/**
 * @author Loic Teyssier
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name="resetpwdcode")
public class ResetPwdCode {

	@Id
	@Column (name="code_resetPwdCode") @Getter @Setter
	String code;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id_resetPwdCode") @Getter @Setter
	@OnDelete (action=OnDeleteAction.CASCADE)
	User user;
}
