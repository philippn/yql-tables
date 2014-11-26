/*
 * Copyright (C) 2014 Philipp Nanz
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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yql4j.YqlClient;
import org.yql4j.YqlClients;
import org.yql4j.YqlQueryBuilder;
import org.yql4j.YqlResult;
import org.yql4j.types.QueryResultType;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Philipp Nanz
 */
public class YahooFinanceComponentsTest {

	public static final String TABLE_DEFINTION = 
			"https://raw.githubusercontent.com/philippn/yql-tables/master/"
			+ "yahoo.finance.components.xml";

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
	public void testDax() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString(
				"use '" + TABLE_DEFINTION + "' as mytable; select * from mytable where symbol=@symbol");
		
		builder.withVariable("symbol", "^GDAXI");
		YqlResult result = client.query(builder.build());
		String[] components = result.getContentAsMappedObject(
				new TypeReference<QueryResultType<String[]>>() {}).getResults();
		assertEquals(30, components.length);
		assertTrue(asList(components).contains("ALV.DE"));
	}

	@Test
	public void testDaxLowerCase() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString(
				"use '" + TABLE_DEFINTION + "' as mytable; select * from mytable where symbol=@symbol");
		
		builder.withVariable("symbol", "^gdaxi");
		YqlResult result = client.query(builder.build());
		String[] components = result.getContentAsMappedObject(
				new TypeReference<QueryResultType<String[]>>() {}).getResults();
		assertEquals(30, components.length);
		assertTrue(asList(components).contains("ALV.DE"));
	}

	@Test
	public void testPagination() throws Exception {
		YqlQueryBuilder builder = YqlQueryBuilder.fromQueryString(
				"use '" + TABLE_DEFINTION + "' as mytable; select * from mytable(1000,100) where symbol=@symbol");
		
		builder.withVariable("symbol", "^IXIC");
		YqlResult result = client.query(builder.build());
		String[] components = result.getContentAsMappedObject(
				new TypeReference<QueryResultType<String[]>>() {}).getResults();
		assertEquals(100, components.length);
		// We are starting at offset 1000, so first entry should not start with A
		assertFalse(components[0].substring(0, 1).equalsIgnoreCase("A"));
	}
}
