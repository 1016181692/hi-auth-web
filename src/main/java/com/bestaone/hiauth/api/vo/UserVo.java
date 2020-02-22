package com.bestaone.hiauth.api.vo;

import com.bestaone.hiauth.utils.JsonLongSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@ApiModel
public class UserVo {
	
	public interface UserSimpleView {};
	public interface UserDetailView extends UserSimpleView {};

	@JsonSerialize(using = JsonLongSerializer.class)
	@JsonView(UserSimpleView.class)
	@ApiModelProperty(value = "主键")
	private Long id;

	@JsonView(UserSimpleView.class)
	@ApiModelProperty(value = "姓名")
	private String name;

	@JsonView(UserSimpleView.class)
	@ApiModelProperty(value = "性别")
	private String gender;

	@JsonView(UserSimpleView.class)
	@ApiModelProperty(value = "用户名")
	private String username;

	@JsonView(UserDetailView.class)
	@ApiModelProperty(value = "密码")
	@NotBlank(message = "密码不能为空")
	private String password;

	@JsonView(UserDetailView.class)
	@ApiModelProperty(value = "手机号码")
	private String tel;

	@JsonView(UserDetailView.class)
	@ApiModelProperty(value = "创建时间")
	@Past(message = "创建时间")
	private Date createTime;

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
