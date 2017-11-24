package com.roof.demo.activity.entity;

import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author 模版生成 <br/>
 *         表名： v_activity <br/>
 *         描述：活动 <br/>
 */
@ApiModel(value = "v_activity", description = "活动")
public class Activity implements Serializable {
	// 需要手动添加非默认的serialVersionUID
	@ApiModelProperty(value = "id")
	protected Long id;// id
	@ApiModelProperty(value = "活动名称")
	protected String name;// 活动名称
	@ApiModelProperty(value = "活动编号")
	protected String code;// 活动编号
	@ApiModelProperty(value = "活动描述")
	protected String remark;// 活动描述
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "报名开始时间")
	protected Date applyStartTime;// 报名开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "报名结束时间")
	protected Date applyEndTime;// 报名结束时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "投票开始时间")
	protected Date voteStartTime;// 投票开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "投票结束时间")
	protected Date voteEndTime;// 投票结束时间
	@ApiModelProperty(value = "活动状态")
	protected String status;// 活动状态
	@ApiModelProperty(value = "投票规则")
	protected String voteRule;// 投票规则
	@ApiModelProperty(value = "每人每天可投票数")
	protected Long voteLimit;// 每人每天可投票数
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建时间")
	protected Date createDate;// 创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
	protected Date updateDate;// 更新时间

	public Activity() {
		super();
	}

	public Activity(Long id) {
		super();
		this.id = id;
	}
	
	@Id// 主键
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
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getApplyStartTime() {
		return applyStartTime;
	}
	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
	}
	
	public Date getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	
	public Date getVoteStartTime() {
		return voteStartTime;
	}
	public void setVoteStartTime(Date voteStartTime) {
		this.voteStartTime = voteStartTime;
	}
	
	public Date getVoteEndTime() {
		return voteEndTime;
	}
	public void setVoteEndTime(Date voteEndTime) {
		this.voteEndTime = voteEndTime;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVoteRule() {
		return voteRule;
	}
	public void setVoteRule(String voteRule) {
		this.voteRule = voteRule;
	}
	
	public Long getVoteLimit() {
		return voteLimit;
	}
	public void setVoteLimit(Long voteLimit) {
		this.voteLimit = voteLimit;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
