package com.loyalty.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.loyalty.model.Customer;
import com.loyalty.model.Points;
import com.loyalty.model.Transaction;
import com.loyalty.util.CustomerData;
import com.loyalty.util.TransactionData;

@Service
public class LoyaltyPointServiceImpl implements LoyaltyPointService {


	@Override
	public 	Customer getCustomerByCustomerId(int customerId) {
		List<Customer> customers=CustomerData.getAllCustomers();
		if(customers.size()<customerId)
			return null;
		return customers.get(customerId-1);
	}

	@Override
	public Points getPointsByCustomerId(Long customerId) {

		Timestamp lastMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(30));
		Timestamp lastSecondMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(60));
		Timestamp lastThirdMonthTimestamp = Timestamp.valueOf(LocalDateTime.now().minusDays(90));

		List<Transaction> lastMonthTransactions = getDataByCustomerIdAndTransactionDateBetween(customerId, lastMonthTimestamp, Timestamp.from(Instant.now()));
		List<Transaction> lastSecondMonthTransactions = getDataByCustomerIdAndTransactionDateBetween(customerId, lastSecondMonthTimestamp, lastMonthTimestamp);
		List<Transaction> lastThirdMonthTransactions = getDataByCustomerIdAndTransactionDateBetween(customerId, lastThirdMonthTimestamp,lastSecondMonthTimestamp);

		Long lastMonthRewardPoints = getPointsPerMonth(lastMonthTransactions);
		Long lastSecondMonthRewardPoints = getPointsPerMonth(lastSecondMonthTransactions);
		Long lastThirdMonthRewardPoints = getPointsPerMonth(lastThirdMonthTransactions);

		double total=lastMonthRewardPoints + lastSecondMonthRewardPoints + lastThirdMonthRewardPoints;
		Points points = new Points(customerId,lastMonthRewardPoints,lastSecondMonthRewardPoints,lastThirdMonthRewardPoints,total);
		return points;

	}

	private List<Transaction> getDataByCustomerIdAndTransactionDateBetween(Long customerId,
			Timestamp lastMonthTimestamp, Timestamp from) {

		List<Transaction> transactionsList =TransactionData.getAllTransactionData();
		List<Transaction> tList=new ArrayList<Transaction>();
		for (Transaction transaction : transactionsList) {
			if(transaction.getCustomerId()==customerId&&(transaction.getTransactionDate().getTime()>=lastMonthTimestamp.getTime()&&transaction.getTransactionDate().getTime()<=from.getTime()))

			{
				tList.add(transaction);
			}
		}

		return tList;
	}



	public Long getPointsPerMonth(List<Transaction> transactions) {
		return transactions.stream().map(transaction -> calculatePoints(transaction.getTransactionAmount())).collect(Collectors.summingLong(r -> r.longValue()));
	}

	public static long calculatePoints(long total) {
		if (total <= 50) {
			return 0l;
		} else if (total <= 100) {
			return total - 50;
		} else {
			Long dblPts = total - 100;
			return 50 + 2 * dblPts;
		}
	}
	
	}
