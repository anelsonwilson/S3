package com.swl.s3.poc;

import org.aspectj.lang.JoinPoint;

public class TrackOperation
{  
    public void myadvice(JoinPoint jp)//it is advice  
    { 
        System.out.println("additional concern: myadvice");  
        //System.out.println("Method Signature: "  + jp.getSignature());  
    }  
    
    public void myadviceAfter(JoinPoint jp)//it is advice  
    {
        System.out.println("additional concern: myadviceAfter");  
    }
    
    public void myadviceAfterReturning(JoinPoint jp)//it is advice  
    {
        System.out.println("additional concern: myadviceAfterReturning");  
    }
    
    /*//issue when un-commented
    public void myadviceAround(JoinPoint jp)//it is advice  
    {
        System.out.println("additional concern: myadviceAround");  
        //System.out.println("Method Signature: "  + jp.getSignature());  
    }
    */
    
    public void myadviceAfterThrowing(JoinPoint jp)//it is advice  
    {
        System.out.println("additional concern: myadviceAfterThrowing");  
    }
    
}  
