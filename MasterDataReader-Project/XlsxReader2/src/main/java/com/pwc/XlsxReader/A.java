package com.pwc.XlsxReader;

public class A {
	
	public void m1() {
		System.out.println("A");
	}
	
	public static void main(String[] args) {
		C c=new C();
		c.m1();
	}
	
}

class B extends A{
	public void m1() {
		System.out.println("B");
	}
	
}

class C extends B{
	public void m1() {
		((A)this).m1();
		System.out.println("C");
	}
	
}


