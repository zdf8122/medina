package com.maninman.experiment;

import java.util.Date;

public  class TestSuper extends Date{   
    /**
	 * 
	 */
	private static final long serialVersionUID = -2424187822772854796L;

	public static void main(String[] args) {   
		try{
			System.out.println(TestSuper.test2());  
			
			throw new Exception("e");
        }catch(Exception e){
        	e.printStackTrace();
        }
        
        System.out.println("continue.");
		
    }   
      
    public void test(){   
        System.out.println(getClass().getSuperclass().getName());   
    }   
    
    static int test2()   
    {   
            int x = 1;   
        try{   
            return x;   
        }   
        finally{   
            ++x;  
            System.out.println("finally:" + x);
        }   
    } 
} 