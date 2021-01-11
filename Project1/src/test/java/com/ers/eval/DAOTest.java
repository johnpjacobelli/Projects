package com.ers.eval;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ers.dao.ReimbursementDAO;
import com.ers.dao.ReimbursementStatusDAO;
import com.ers.dao.ReimbursementTypeDAO;
import com.ers.dao.UserDAO;
import com.ers.dao.UserRoleDAO;
import com.ers.model.Reimbursement;
import com.ers.model.ReimbursementStatus;
import com.ers.model.ReimbursementType;
import com.ers.model.User;
import com.ers.model.UserRole;
import com.ers.util.HibernateUtil;

public class DAOTest {
	
	
	@Mock
	private HibernateUtil hUtil;
	
	@Mock
	private Session ses;
	
	@Mock
	private Transaction tx;
	
	@Mock
	Query<Reimbursement> rQ;
	
	@Mock
	Query<ReimbursementStatus> rsQ;
	
	@Mock
	Query<ReimbursementType> rtQ;
	
	@Mock
	Query<UserRole> urQ;
	
	@Mock
	Query<User> uQ;
	
	@Mock 
	Serializable serial;
	
	@Mock
	private List<Reimbursement> rList;
	
	@Mock
	private List<ReimbursementStatus> rsList;
	
	@Mock
	private List<ReimbursementType> rtList;
	
	@Mock
	private List<UserRole> urList;
	
	@Mock
	private List<User> uList;
	
	private Reimbursement testReim;
	private ReimbursementStatus testReimStatus;
	private ReimbursementType testReimType;
	private UserRole testUserRole;
	private User testManager;
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
		testManager = new User("user2", "p", "Mister", "Manager", "mm@gmail.com", testUserRole);
		testReimType = new ReimbursementType(2, "Lodging");
		testReimStatus = new ReimbursementStatus(1, "Pending");
		testReim = new Reimbursement(500, 10_000, new Timestamp(System.currentTimeMillis() - 10000), new Timestamp(System.currentTimeMillis()), 
												   "Test", testEmployee, testManager, testReimStatus, testReimType);
		
		when(hUtil.getSession()).thenReturn(ses);
		
		
	}
	
	@After
	public void tearDown() throws Exception{
		
	}

	
	// ========================= Reimbursement DAO testing =========================
	@Test
	public void testReimbursementDAOSelectByID() {
		when(ses.get(eq(Reimbursement.class), anyInt())).thenReturn(testReim);
		
		assertEquals(new ReimbursementDAO(hUtil).selectById(500).getReimID(), testReim.getReimID());
	}
	
	@Test
	public void testReimbursementDAOSelectByName() {
		when(ses.createQuery(anyString(), eq(Reimbursement.class))).thenReturn(rQ);
		when(rQ.setParameter(anyString(), anyString())).thenReturn(rQ);
		when(rList.size()).thenReturn(1);
		when(rQ.list()).thenReturn(rList);
		when(rList.get(0)).thenReturn(testReim);
		
		assertEquals(new ReimbursementDAO(hUtil).selectByName("Pending").getReimID(), testReim.getReimID());
	}
	
	@Test
	public void testReimbursementDAOSelectAll() {
		when(ses.createQuery(anyString(), eq(Reimbursement.class))).thenReturn(rQ);
		when(rQ.list()).thenReturn(rList);
		
		assertEquals(new ReimbursementDAO(hUtil).selectAll(), rList);
	}
	
	@Test
	public void testReimbursementDAOSelectAllWhereXY() {
		when(ses.createQuery(anyString(), eq(Reimbursement.class))).thenReturn(rQ);
		when(rQ.setParameter(anyString(), anyString())).thenReturn(rQ);
		when(rQ.list()).thenReturn(rList);
		
		assertEquals(new ReimbursementDAO(hUtil).selectAll(), rList);
	}
	// =============================================================================
	
	
	// =================== ReimbursementStatus DAO testing =========================
	@Test
	public void testReimbursementStatusDAOSelectByID() {
		when(ses.get(eq(ReimbursementStatus.class), anyInt())).thenReturn(testReimStatus);
		
		assertEquals(new ReimbursementStatusDAO(hUtil).selectById(1).getReimStatusID(), testReimStatus.getReimStatusID());
	}
	
	@Test
	public void testReimbursementStatusDAOSelectByName() {
		when(ses.createQuery(anyString(), eq(ReimbursementStatus.class))).thenReturn(rsQ);
		when(rsQ.setParameter(anyString(), anyString())).thenReturn(rsQ);
		when(rsList.size()).thenReturn(1);
		when(rsQ.list()).thenReturn(rsList);
		when(rsList.get(0)).thenReturn(testReimStatus);
		
		assertEquals(new ReimbursementStatusDAO(hUtil).selectByName("Pending").getReimStatusID(), testReimStatus.getReimStatusID());
	}
	
	@Test
	public void testReimbursementStatusDAOSelectAll() {
		when(ses.createQuery(anyString(), eq(ReimbursementStatus.class))).thenReturn(rsQ);
		when(rsQ.list()).thenReturn(rsList);
		
		assertEquals(new ReimbursementStatusDAO(hUtil).selectAll(), rsList);
	}
	// =============================================================================
	
	
	// ======================= ReimbursementType DAO testing =======================
	@Test
	public void testReimbursementTypeDAOSelectByID() {
		when(ses.get(eq(ReimbursementType.class), anyInt())).thenReturn(testReimType);
		
		assertEquals(new ReimbursementTypeDAO(hUtil).selectById(500).getReimTypeID(), testReimType.getReimTypeID());
	}
	
	@Test
	public void testReimbursementTypeDAOSelectByName() {
		when(ses.createQuery(anyString(), eq(ReimbursementType.class))).thenReturn(rtQ);
		when(rtQ.setParameter(anyString(), anyString())).thenReturn(rtQ);
		when(rtList.size()).thenReturn(1);
		when(rtQ.list()).thenReturn(rtList);
		when(rtList.get(0)).thenReturn(testReimType);
		
		assertEquals(new ReimbursementTypeDAO(hUtil).selectByName("Pending").getReimTypeID(), testReimType.getReimTypeID());
	}
	
	@Test
	public void testReimbursementTypeDAOSelectAll() {
		when(ses.createQuery(anyString(), eq(ReimbursementType.class))).thenReturn(rtQ);
		when(rtQ.list()).thenReturn(rtList);
		
		assertEquals(new ReimbursementTypeDAO(hUtil).selectAll(), rtList);
	}
	
	// =============================================================================
	
	
	// =========================== UserRole DAO testing ============================
	@Test
	public void testUserRoleDAOSelectByID() {
		when(ses.get(eq(UserRole.class), anyInt())).thenReturn(testUserRole);
		
		assertEquals(new UserRoleDAO(hUtil).selectById(1).getUserRoleID(), testUserRole.getUserRoleID());
	}
	
	@Test
	public void testUserRoleDAOSelectByName() {
		when(ses.createQuery(anyString(), eq(UserRole.class))).thenReturn(urQ);
		when(urQ.setParameter(anyString(), anyString())).thenReturn(urQ);
		when(urList.size()).thenReturn(1);
		when(urQ.list()).thenReturn(urList);
		when(urList.get(0)).thenReturn(testUserRole);
		
		assertEquals(new UserRoleDAO(hUtil).selectByName("Employee").getUserRoleID(), testUserRole.getUserRoleID());
	}
	
	@Test
	public void testUserRoleDAOSelectAll() {
		when(ses.createQuery(anyString(), eq(UserRole.class))).thenReturn(urQ);
		when(urQ.list()).thenReturn(urList);
		
		assertEquals(new UserRoleDAO(hUtil).selectAll(), urList);
	}
	
	// =============================================================================
	
	
	// =========================== UserRole DAO testing ============================
	@Test
	public void testUserDAOSelectByID() {
		when(ses.get(eq(User.class), anyInt())).thenReturn(testEmployee);
		
		assertEquals(new UserDAO(hUtil).selectById(1).getUserID(), testEmployee.getUserID());
	}
	
	@Test
	public void testUserDAOSelectByName() {
		when(ses.createQuery(anyString(), eq(User.class))).thenReturn(uQ);
		when(uQ.setParameter(anyString(), anyString())).thenReturn(uQ);
		when(uList.size()).thenReturn(1);
		when(uQ.list()).thenReturn(uList);
		when(uList.get(0)).thenReturn(testEmployee);
		
		assertEquals(new UserDAO(hUtil).selectByName("Employee").getUserID(), testEmployee.getUserID());
	}
	
	@Test
	public void testUserDAOSelectAll() {
		when(ses.createQuery(anyString(), eq(User.class))).thenReturn(uQ);
		when(uQ.list()).thenReturn(uList);
		
		assertEquals(new UserDAO(hUtil).selectAll(), uList);
	}
	
	// =============================================================================


}
