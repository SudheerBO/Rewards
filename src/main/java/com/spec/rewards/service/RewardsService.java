package com.spec.rewards.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spec.rewards.dto.RewardsDto;
import com.spec.rewards.dto.UserTransDto;

@Service
public class RewardsService {
	
	@Resource
	private ObjectMapper objectMapper;
	
	public List<UserTransDto> getAllUserTrans() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		return objectMapper.readValue(
				RewardsService.class.getClassLoader().getResourceAsStream("transdata.json"), new TypeReference<List<UserTransDto>>() { });
	}
	
	public List<RewardsDto> getRewardPoints() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		//Find All User Transactions
		List<UserTransDto> userTrans = getAllUserTrans();
		
		//Find Distinct User List
		Set<String> userNames =  userTrans.parallelStream().map(t -> t.getUserName()).collect(Collectors.toSet());
		
		//Intialize RewardDto for each user
		List<RewardsDto> rewardsDtoList = userNames.parallelStream().map(s -> new RewardsDto(s)).collect(Collectors.toList());
		
		//Calculate Rewards
		rewardsDtoList.parallelStream().forEach(r -> {
			calculateUserRewards(r, 
					userTrans.parallelStream().filter(
							u -> u.getUserName().equalsIgnoreCase(r.getUserName())).collect(Collectors.toList()));
		});
		
		return rewardsDtoList;
	}
	
	public static final String HYPHEN = "-";
	private void calculateUserRewards(RewardsDto userDto, List<UserTransDto> userTrans) {
		
		if(!userTrans.isEmpty()) {
			userTrans.stream().forEach(ut -> {
				
				Long rp = Double.valueOf(Math.ceil(getRewardPointsByAmt(ut.getAmt()))).longValue();
				
				String monthYearKey = String.join(HYPHEN, 
						Integer.valueOf(ut.getTransDate().getMonthValue()).toString(),
									Integer.valueOf(ut.getTransDate().getYear()).toString());
				
				if(!userDto.getRewardsByMonth().containsKey(monthYearKey)) {
					userDto.getRewardsByMonth().put(monthYearKey, rp);
				} else {
					userDto.getRewardsByMonth().put(monthYearKey, userDto.getRewardsByMonth().get(monthYearKey) + rp);
				}
			});
		}
		userDto.setTotalRewards(userDto.getRewardsByMonth().values().stream().mapToLong(Long::valueOf).sum());
	}
	
	//Rewards Points Logic
	private Double getRewardPointsByAmt(Double amt) {
		if(amt > 100) {
			return (2 *(amt - 100))  +(1 * 50);
		} else if(amt > 50) {
			return (1 *(amt - 50));
		}
		return 0d;
	}
}
