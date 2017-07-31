package com.sap.cloud.samples.ariba.partner.flow.dtos;

import com.google.gson.annotations.SerializedName;

/**
 * User DTO.
 *
 */
public class UserDto {

	@SerializedName("name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
