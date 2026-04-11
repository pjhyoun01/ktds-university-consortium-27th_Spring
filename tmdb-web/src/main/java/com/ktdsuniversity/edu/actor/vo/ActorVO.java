package com.ktdsuniversity.edu.actor.vo;

import java.util.List;

import com.ktdsuniversity.edu.appearance.vo.AppearanceVO;

public class ActorVO {
	private String actorId;
	private String actorName;
	private String actorProfileUrl;
	private String fileGroupId;

	private List<AppearanceVO> appearanceVO;

	public String getActorId() {
		return this.actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getActorName() {
		return this.actorName;
	}
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}
	public String getActorProfileUrl() {
		return this.actorProfileUrl;
	}
	public void setActorProfileUrl(String actorProfileUrl) {
		this.actorProfileUrl = actorProfileUrl;
	}
	public String getFileGroupId() {
		return this.fileGroupId;
	}
	public void setFileGroupId(String fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public List<AppearanceVO> getAppearanceVO() {
		return this.appearanceVO;
	}
	public void setAppearanceVO(List<AppearanceVO> appearanceVO) {
		this.appearanceVO = appearanceVO;
	}
}
