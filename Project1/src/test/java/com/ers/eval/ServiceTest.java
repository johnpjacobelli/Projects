package com.ers.eval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ers.dao.ReimbursementDAO;
import com.ers.dao.UserDAO;
import com.ers.model.Reimbursement;
import com.ers.model.User;
import com.ers.model.UserRole;
import com.ers.service.ReimbursementService;
import com.ers.service.UserService;
import com.ers.util.HibernateUtil;

public class ServiceTest {
	
	@Mock
	private HibernateUtil hUtil;
	
	@Mock
	private ReimbursementDAO rDAO;
	
	@Mock
	private UserDAO uDAO;
	
	@Mock
	private List<Reimbursement> rList;
	
	private UserRole testUserRole;
	private User testEmployee;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		
	}
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		testUserRole = new UserRole(10, "Employee");
		testEmployee = new User("user1", "p", "John", "Jacobelli", "jj@gmail.com", testUserRole);
	}
	
	@After
	public void tearDown() throws Exception{
		
	}

	
	// ========================= Reimbursement Service testing =========================
	@Test
	public void testReimbursementServiceFilter() {
		when(rDAO.selectAllWhereXY(anyString(), anyInt())).thenReturn(rList);
		when(rList.size()).thenReturn(1);
		
		assertEquals(new ReimbursementService(rDAO).getByFilter("reimb_status_id", 1), rList);
	}
	// =================================================================================
	
	
	// ========================= Reimbursement Service testing =========================
	@Test
	public void testUserServiceUsername() {
		when(uDAO.selectByName(anyString())).thenReturn(testEmployee);
		
		assertEquals(new UserService(uDAO).getByUsername("user1"), testEmployee);
	}
	
	@Test
	public void testUserServiceID() {
		when(uDAO.selectById(anyInt())).thenReturn(testEmployee);
		
		assertEquals(new UserService(uDAO).getById(1), testEmployee);
	}
	
	@Test
	public void testUserServiceCredentials() {
		when(uDAO.selectByName(anyString())).thenReturn(testEmployee);
		
		assertTrue(new UserService(uDAO).verifyCredentials("user1", "p"));
	}
	// =================================================================================
}
	
