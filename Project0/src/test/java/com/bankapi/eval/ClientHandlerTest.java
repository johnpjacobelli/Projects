package com.bankapi.eval;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bankapi.bankinfo.Account;
import com.bankapi.bankinfo.Client;
import com.bankapi.dao.AccountDAO;
import com.bankapi.dao.ClientDAO;
import com.bankapi.dao.DAOConnection;

public class ClientHandlerTest {
	
	private Client testClient;
	private Account testAccount;
	private ClientDAO cDAO;
	private AccountDAO aDAO;
	
	@Mock
	private DAOConnection dbc;
	
	@Mock
	private PreparedStatement ps;
	
	@Mock
	private CallableStatement cs;
	
	@Mock
	private ResultSet rs;
	
	@Mock
	private Connection c;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		
	}
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		cDAO = new ClientDAO(dbc);
		aDAO = new AccountDAO(dbc);
		
		when(dbc.getDBConnection()).thenReturn(c);
		when(c.prepareStatement(any(String.class))).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true);
		
		testClient = new Client(1, "John", "Jacobelli");
		testAccount = new Account(1, 1, 5000);
		
		when(new ClientDAO().getClientByID(1)).thenReturn(testClient);
		when(new AccountDAO().getAccountByID(1, 1)).thenReturn(testAccount);
	}
	
	@After
	public void tearDown() throws Exception{
		
	}
	
	@Test
	public void testFindByNameSuccess() {
		assertEquals(new ClientDAO(dbc).getClientByID(1).getFirstName(), testClient.getFirstName());
	}
	
	@Test
	public void testGetClientByIDSuccess() {
		assertEquals(new ClientDAO(dbc).getClientByID(1).getClientID(), testClient.getClientID());
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetClientByIDFailure() {
		assertEquals(new ClientDAO(dbc).getClientByID(2).getClientID(), testClient.getClientID());
	}
	
	@Test
	public void testGetAccountByIDSuccess() {
		assertEquals(new AccountDAO(dbc).getAccountByID(1, 1).getAccountID(), testAccount.getAccountID());
	}
	
	@Test (expected = NullPointerException.class)
	public void testGetAccountByIDFailure() {
		assertEquals(new AccountDAO(dbc).getAccountByID(1, 2).getAccountID(), testAccount.getAccountID());
	}
	

}
