/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.DoFixture;
import fitlibrary.DomainObjectCheckFixture;
import fitlibrary.DomainObjectSetUpFixture;

public class DomainObjectUnderTest extends DoFixture {
    TestObject object = new TestObject();
    
    public DomainObjectSetUpFixture create() {
        return new DomainObjectSetUpFixture(object);
    }
    public DomainObjectCheckFixture checks() {
        return new DomainObjectCheckFixture(object);
    }
    public static class TestObject {
        private int a = 1;
        private double b = 3.5;
        private String c = "a string";
        
        public int getA() {
            return a ;
        }
        public double getB() {
            return b;
        }
        public String getC() {
             return c;
        }
        public void setA(int a) {
            this.a = a;
        }
        public void setB(double b) {
            this.b = b;
        }
        public void setC(String c) {
            this.c = c;
        }
        public D[] getDs() {
        	return new D[] {
        			new D()
        	};
        }
        public static class D {
        	public int getProp() {
        		return 4;
        	}
        }
    }
}
