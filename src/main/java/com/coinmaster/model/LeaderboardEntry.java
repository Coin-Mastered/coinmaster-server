package com.coinmaster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LeaderboardEntry {
	
	private User user;
	private double value;
	
}
