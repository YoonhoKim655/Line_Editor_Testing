import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class Cmd_DriverTest extends TestCase
{
	String[] input = {"C:\\Users\\Yoonho\\eclipse-workspace\\csuedSource.zip_expanded\\sample.txt"};
	String[] input1 = {"C:\\Users\\Yoonho\\eclipse-workspace\\csuedSource.zip_expanded\\empty.txt"};
	
	File_Buffer F_B = new File_Buffer();
	File_Buffer F_B2 = new File_Buffer();
	
    Init_Exit I_E, I_E2;
    
    Cmd_Driver RunC;
    UserCmd CMD;
    
    boolean tf;
    private final PrintStream systemOut = System.out;
    private ByteArrayOutputStream T_output = new ByteArrayOutputStream();
 
    @Before
	protected void setUp() throws Exception 
    {
	    I_E = new Init_Exit(input, F_B);
	    I_E2 = new Init_Exit(input1, F_B2);
	    RunC = new Cmd_Driver();
	    System.setOut(new PrintStream(T_output));
	    super.setUp();
	}
    
    @After
    public void restoreStreams()
    {
    	System.setOut(systemOut);
    }
    
	@Test
	public void testCmd_Q() 
	{
		int num = F_B2.NumLins();

        F_B2.setUpdateFlag(true);
        	
        try {
            RunC.Cmd_D(F_B2, 1);
			I_E2.Do_Update(F_B2);	
			assertEquals(num-1, F_B2.NumLins());
			assertNotEquals("#this is moment!", F_B2.GetLine(1)); 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void testCmd_X() 
	{
		int num = F_B.NumLins();
		
		tf = true;
        F_B.setUpdateFlag(false);
        	
        try{
            RunC.Cmd_D(F_B, num);
			I_E.Do_Update(F_B);
			F_B = new File_Buffer();
			I_E = new Init_Exit(input,F_B);
			assertEquals("goodd", F_B.GetLine(num));
			assertEquals(num, F_B.NumLins());
		} catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	@Test
	public void testCmd_T() 
	{
		RunC.Cmd_T(F_B);
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_T2() 
	{
		RunC.Cmd_T(F_B2);
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_E() 
	{
		int num = F_B.NumLins();
		RunC.Cmd_E(F_B);
		assertEquals(num, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_E2() 
	{
		RunC.Cmd_E(F_B2);
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_N() 
	{
		int CLNnum = F_B.GetCLN();
		int nl = 1;
		
		RunC.Cmd_N(F_B, nl);
		
		assertEquals(nl+1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_N2() 
	{
		int CLNnum = F_B.GetCLN();
		int nl = 0;
		
		RunC.Cmd_N(F_B, nl);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_N3() 
	{
		int CLNnum = F_B2.GetCLN();
		int nl = 1;
		
		RunC.Cmd_N(F_B2, nl);
		
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_B() 
	{
		F_B.SetCLN(5);
		int nl = 1;
		
		RunC.Cmd_B(F_B, nl); 
		
		assertEquals(5-nl, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_B2() 
	{
		F_B.SetCLN(5);
		int nl = 0;
		
		RunC.Cmd_B(F_B, nl); 
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_B3() 
	{
		int nl = 1;
		
		RunC.Cmd_B(F_B2, nl); 
		
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_W()
	{
		RunC.Cmd_W(F_B);
		assertEquals("At Edit File Line "+F_B.GetCLN()+"\r\n",T_output.toString() );
	}
	
	@Test
	public void testCmd_W2()
	{
		RunC.Cmd_W(F_B2);
		assertEquals("At Edit File Line "+F_B2.GetCLN()+"\r\n",T_output.toString() );
	}
	
	@Test
	public void testCmd_C()
	{
		RunC.Cmd_C(F_B);
		assertEquals("Total Edit File Lines: "+F_B.NumLins()+"\r\n",T_output.toString() );
	}
	
	@Test
	public void testCmd_C2()
	{
		RunC.Cmd_C(F_B2);
		assertEquals("Total Edit File Lines: "+F_B2.NumLins()+"\r\n",T_output.toString() );
	}
	
	@Test
	public void testCmd_L() 
	{
		int CLNnum =F_B.GetCLN();
	    int nl=2;
	    String str = "";
	      
	    for(int i=CLNnum;i<=nl;i++)
	    {
	       str += F_B.GetLine(i)+"\r\n";
	    }
	      
	    RunC.Cmd_L(F_B,nl);
	    assertEquals(str,T_output.toString());
	}
	
	@Test
	public void testCmd_L2() 
	{
		int CLNnum =F_B.GetCLN();
	    int nl=0;
	      
	    RunC.Cmd_L(F_B2,nl);
	    assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n",T_output.toString());
	}
	
	@Test
	public void testCmd_L3() 
	{
		int CLNnum =F_B2.GetCLN();
	    int nl=2;
	      
	    RunC.Cmd_L(F_B2,nl);
	    assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_S()
	{
		int CLNnum = F_B.GetCLN();
		int nl = 2;
		
		String str = "";
	      
	    for(int i=CLNnum;i<=nl;i++) {
	       str += F_B.GetLine(i)+"\r\n";
	    }
	      
	    RunC.Cmd_S(F_B, nl);
	    
	    assertEquals(str,T_output.toString());
		assertEquals(CLNnum, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_S2()
	{
		int nl = 0;
		
	    RunC.Cmd_S(F_B, nl);
	    
	    assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_S3()
	{
		int nl = 2;
		
	    RunC.Cmd_S(F_B2, nl);
	    
	    assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_D()
	{
		int CLNnum = F_B.NumLins();
		int nl = 2;
		
		RunC.Cmd_D(F_B, nl);
		
		assertEquals(CLNnum - nl, F_B.NumLins());
	}
	
	@Test
	public void testCmd_D2()
	{
		int nl = 0;
		
		RunC.Cmd_D(F_B, nl);
		
		assertEquals(1, F_B.GetCLN());
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_D3()
	{
		int nl = 2;
		
		RunC.Cmd_D(F_B2, nl);
		
		assertEquals(0, F_B2.GetCLN());
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_A() {
		int CLNnum = F_B.GetCLN();
		System.out.println(F_B.GetCLN());
		int temp_num_line = F_B.NumLins();
		
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(("\r\n").getBytes());
			System.setIn(in);
			
			RunC.Cmd_A(F_B);
			assertEquals(CLNnum, F_B.GetCLN());
			assertEquals(temp_num_line, F_B.NumLins()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCmd_F() 
	{		
		RunC.Cmd_F(F_B, 3, "s");
		
		assertEquals(3, F_B.GetCLN());
		assertEquals("3: safasfnvzon\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_F2() 
	{		
		RunC.Cmd_F(F_B, 0, "s");
		
		assertEquals(1, F_B.GetCLN());
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_F3() 
	{		
		RunC.Cmd_F(F_B, 3, "");
		
		assertEquals(1, F_B.GetCLN());
		assertEquals("A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_F4() 
	{		
		RunC.Cmd_F(F_B, 3, "xxxx");
		
		assertEquals(3, F_B.GetCLN());
		assertEquals("", T_output.toString());
	}
	
	@Test
	public void testCmd_F5() 
	{		
		RunC.Cmd_F(F_B2, 3, "xxxx");
		
		assertEquals(0, F_B2.GetCLN());
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_R()
	{
		RunC.Cmd_R(F_B, 3, "s", "S");
		
		assertEquals("SafaSfnvzon\r\n", T_output.toString());
		assertEquals(3, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_R2()
	{
		RunC.Cmd_R(F_B, 0, "s", "S");
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_R3()
	{
		RunC.Cmd_R(F_B, 3, "", "S");
		
		assertEquals("A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken.\r\n", T_output.toString());
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_R4()
	{
		RunC.Cmd_R(F_B, 3, "b", "S");
		
		assertEquals("", T_output.toString());
		assertEquals(3, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_R5()
	{
		RunC.Cmd_R(F_B2, 3, "b", "S");
		
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_Y()
	{
		RunC.Cmd_Y(F_B, 2);
		assertEquals(2, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_Y2()
	{
		RunC.Cmd_Y(F_B, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_Y3()
	{
		RunC.Cmd_Y(F_B2, 1);
		
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_Z() 
	{
		int numLine = F_B.NumLins();
		
		RunC.Cmd_Z(F_B, 2);
		
		assertEquals(numLine - 2, F_B.NumLins());
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_Z2() 
	{	
		RunC.Cmd_Z(F_B, 0);
		
		assertEquals("NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n"
				+"NUMBER LINES VALUE MUST BE POSITIVE & NONZERO.  No action taken.\r\n", T_output.toString());
		assertEquals(1, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_Z3() 
	{	
		RunC.Cmd_Z(F_B2, 1);
		
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n"
				+"ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_P() 
	{
		int CLNnum = F_B.GetCLN();
		int n = 3;
		
		RunC.Cmd_Y(F_B,n);		
		RunC.Cmd_P(F_B);
		assertEquals((CLNnum+(n*2-1)), F_B.GetCLN());	
	}
	
	public void testCmd_P2() 
	{	
		RunC.Cmd_P(F_B);
		assertEquals("NO LINES IN YANK BUFFER TO PUT:  No action taken.\r\n", T_output.toString());
		assertEquals(1, F_B.GetCLN());
	}
	
	public void testCmd_P3() 
	{	
		RunC.Cmd_P(F_B2);
		assertEquals("NO LINES IN YANK BUFFER TO PUT:  No action taken.\r\n", T_output.toString());
		assertEquals(0, F_B2.GetCLN());
	}

	@Test
	public void testCmd_I()
	{
		int CLNnum = F_B.GetCLN();
		
		RunC.Cmd_I(F_B);
		
		assertEquals("",T_output.toString());
		assertEquals(CLNnum, F_B.GetCLN());
	}
	
	@Test
	public void testCmd_I2()
	{
		RunC.Cmd_I(F_B2);
		assertEquals("THERE ARE NO KEYWORDS AT TOP OF FILE TO INDEX:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_K()
	{
		RunC.Cmd_I(F_B);
		RunC.Cmd_K("keyword");
		
		assertEquals("1  5  6  8  \r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_K2()
	{
		RunC.Cmd_I(F_B);
		RunC.Cmd_K("abc");
		
		assertEquals("THIS KEYWORD DOES NOT EXIST:  No action taken.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_K3()
	{
		RunC.Cmd_I(F_B);
		RunC.Cmd_K("");
		
		assertEquals("A NULL (0 LENGTH) STRING HAS NO MEANING HERE:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_O()
	{
		RunC.Cmd_O(F_B, 2);

		assertEquals(2, F_B.GetCLN());
		
		F_B.SetCLN(1);
		RunC.Cmd_S(F_B, 100);
		
		assertEquals("@Key\r\n"
				+ "@keyword\r\n"
				+ "safasfnvzon\r\n"
				+ "Key\r\n"
				+ "keyword\r\n"
				+ "hhahaha keyword\r\n"
				+ "kjanv\r\n"
				+ "mamamakeyword\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_O2()
	{
		RunC.Cmd_O(F_B2, 2);
		
		assertEquals("ILLEGAL COMMAND WHEN NO EDIT FILE LINES EXIST:  No action taken.\r\n", T_output.toString());
	}

	@Test
	public void testCmd_M()
	{
		RunC.Cmd_M(1, 50);
		
		assertEquals("COMMAND NOT IMPLEMENTED (for F, R, O) YET.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_M2()
	{
		RunC.Cmd_M(3, 1);
		
		assertEquals("REVERSED OR BACKWARDS COLUMN RANGES ARE ILLEGAL:  No action taken.\r\n"
				+ "COMMAND NOT IMPLEMENTED (for F, R, O) YET.\r\n", T_output.toString());
	}
	
	@Test
	public void testCmd_M3()
	{
		RunC.Cmd_M(0, 50);
		
		assertEquals("COLUMN VALUES MUST BE POSITIVE & NONZERO:  No action taken.\r\n"
				+ "COMMAND NOT IMPLEMENTED (for F, R, O) YET.\r\n", T_output.toString());
	}
	
	@Test
	public void testEasterEgg() {
		int CLNnum = F_B.GetCLN();
		int n = 3;
		
		RunC.Cmd_O(F_B,n);
		
		assertEquals(CLNnum+2,F_B.GetCLN()); // 정렬이 된다고 가정하면, CLN은 마지막 정렬된 줄 번호를 가지고있으므로 예상결과 : true.
	}

}