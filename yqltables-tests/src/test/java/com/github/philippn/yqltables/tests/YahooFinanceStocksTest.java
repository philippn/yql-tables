/*
 * Copyright (C) 2015 Philipp Nanz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.github.philippn.yqltables.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yql4j.YqlClient;
import org.yql4j.YqlClients;
import org.yql4j.YqlQueryBuilder;
import org.yql4j.YqlResult;
import org.yql4j.types.QueryResultType;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.philippn.yqltables.tests.type.StockArrayType;
import com.github.philippn.yqltables.tests.type.StockType;

/**
 * @author Philipp Nanz
 */
public class YahooFinanceStocksTest {

	public static final String TABLE_DEFINTION = 
			"https://raw.githubusercontent.com/philippn/yql-tables/master/"
			+ "yahoo.finance.stocks.xml";

	private YqlClient client;

	@Before
	public void setUp() {
		client = YqlClients.createDefault();
	}

	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	public void testAlvDe() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString(
				"use '" + TABLE_DEFINTION + "' as mytable; select * from mytable where symbol=@symbol");
		
		builder.withVariable("symbol", "ALV.DE");
		YqlResult result = client.query(builder.build());
		QueryResultType<StockArrayType> mappedResult = 
				result.getContentAsMappedObject(
						new TypeReference<QueryResultType<StockArrayType>>() {});
		assertEquals(1, mappedResult.getCount());
		assertNotNull(mappedResult.getResults());
		StockType stock = mappedResult.getResults().getStock()[0];
		assertEquals("Allianz SE", stock.getCompanyName());
		assertEquals("XETRA", stock.getMarket());
		assertEquals("Financial", stock.getSector());
		assertEquals("Property & Casualty Insurance", stock.getIndustry());
		//assertTrue(stock.getFullTimeEmployees() > 0);
	}
}
