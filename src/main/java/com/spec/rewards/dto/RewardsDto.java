package com.spec.rewards.dto;

import java.util.HashMap;
import java.util.Map;


public class RewardsDto {
	
	private String userName;
	private Map<String, Long> rewardsByMonth;
	private Long totalRewards;
	
	public RewardsDto(String userName) {
		this.userName = userName;
		this.rewardsByMonth =  new HashMap<>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Map<String, Long> getRewardsByMonth() {
		return rewardsByMonth;
	}

	public void setRewardsByMonth(Map<String, Long> rewardsByMonth) {
		this.rewardsByMonth = rewardsByMonth;
	}

	public Long getTotalRewards() {
		return totalRewards;
	}

	public void setTotalRewards(Long totalRewards) {
		this.totalRewards = totalRewards;
	}
}
