package com.spec.rewards.api;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spec.rewards.dto.RewardsDto;
import com.spec.rewards.dto.UserTransDto;
import com.spec.rewards.service.RewardsService;

@RestController
public class RewardsAPI {
	
	private Logger log = LoggerFactory.getLogger(RewardsAPI.class);
	
	@Resource
	private RewardsService rewardsService;
	
	@RequestMapping(value = "/listtrans", method = RequestMethod.GET)
	public ResponseEntity<List<UserTransDto>> getAllUserTrans() {
		try {
			return new ResponseEntity<>(rewardsService.getAllUserTrans(), HttpStatus.OK);
		} catch(Exception e) {
			log.error("Error in getting All Transactions {}", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/getRewardPoints", method = RequestMethod.GET)
	public ResponseEntity<List<RewardsDto>> getRewardPoints() {
		try {
			return new ResponseEntity<>(rewardsService.getRewardPoints(), HttpStatus.OK);
		} catch(Exception e) {
			log.error("Error in getting reward points {}", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
