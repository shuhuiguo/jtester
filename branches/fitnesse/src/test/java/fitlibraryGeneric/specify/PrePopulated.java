/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibraryGeneric.specify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrePopulated {
    private int anInt = 3;
    private String aValueObject = "my value";
    private Colour colour = new Colour("yellow");
    private Colour colour2 = new Colour("blue-green");
    private Colour colour3 = new Colour("gray");
    private MyPoint point2 = new MyPoint(12,34);
    private String aString = "a string";    

    private Colour[] anArray = new Colour[] { new Colour("red"), new Colour("green") };
    private int[] anIntArray = {1,2,4};
    private int[][] a2DArray = {anIntArray,anIntArray};
    
    private List<Colour> aList = new ArrayList<Colour>();
    private List<List<Colour>> aListOfLists = new ArrayList<List<Colour>>();
    
    private Set<Colour> aSet = new HashSet<Colour>();
    private Set<Set<Colour>> aSetOfSets = new HashSet<Set<Colour>>();
    
    private Map<String,String> aMap = new HashMap<String,String>();
    private Map<String,Colour> aNestedMap = new HashMap<String,Colour>();
    
    private List<Set<Colour>> aListOfSets = new ArrayList<Set<Colour>>();
    private Set<List<Colour>> aSetOfLists = new HashSet<List<Colour>>();
    private Set<List<Colour>> anEmptySetOfLists = new HashSet<List<Colour>>();
    private List<Set<Colour>> anEmptyListOfSets = new ArrayList<Set<Colour>>();
    
    private InterfaceType subtypeProperty = new ConcreteClassTwo();
    private List<InterfaceType> aListOfAttribute = new ArrayList<InterfaceType>();
    private Set<InterfaceType> aSetOfAttribute = new HashSet<InterfaceType>();
    
//    private List<Colour>[] anArrayOfGenericType;

    public PrePopulated() {        
        aList.add(new Colour("red"));
        aList.add(new Colour("green"));
//        aList.add(new Colour("blue"));
        
//        anIntArray = new int {1,2,3};
//        anArrayOfGenericType = new List<Colour>[2];
//        List<Colour>[] anArrayOfGenericType2 = { aList, aList };
        
        aListOfLists.add(aList);
//        aListOfLists.add(aList);

        aSet.add(new Colour("red"));
        aSet.add(new Colour("green"));
        
        Set<Colour> aSet2 = new HashSet<Colour>();
        aSet2.add(new Colour("green"));

        aSetOfSets.add(aSet);
//        aSetOfSets.add(aSet2);
        
        aListOfSets.add(aSet);
        
        aSetOfLists.add(aList);
        
        aMap.put("red","green");
        aMap.put("white","black");
        aMap.put("blue","yellow");

        aNestedMap.put("red",new Colour("green"));
        aNestedMap.put("white",new Colour("black"));
        aNestedMap.put("blue",new Colour("yellow"));
    }
    public int getAnInt() {
        return anInt;
    }
    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
    public String getAValueObject() {
        return aValueObject;
    }
    public void setAValueObject(String valueObject) {
        aValueObject = valueObject;
    }
    public String getAnException() {
        throw new RuntimeException("an exception");
    }
    public Colour[] getAnArray() {
        return anArray;
    }
    public void setAnArray(Colour[] anArray) {
        this.anArray = anArray;
    }
    public List<Colour> getAList() {
        return aList;
    }
    public void setAList(List<Colour> list) {
        aList = list;
    }
    public Set<Colour> getASet() {
        return aSet;
    }
    public void setASet(Set<Colour> set) {
        aSet = set;
    }
    public Map<String, String> getAMap() {
        return aMap;
    }
    public void setAMap(Map<String, String> map) {
        aMap = map;
    }
    public Colour getColour() {
        return colour;
    }
    public void setColour(Colour colour) {
        this.colour = colour;
    }
    public static class Colour {
        private String colour;
        private static int COUNT = 0;
        private int count = COUNT++;
        private MyPoint point = new MyPoint(3,4);
        private List<Colour> subColours = new ArrayList<Colour>();

        public Colour() {
        	//
        }
        public Colour(String colour) {
            this.colour = colour;
        }
        public String getColour() {
            return colour;
        }
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public MyPoint getPoint() {
            return point;
        }
        public void setPoint(MyPoint point) {
            this.point = point;
        }
        public void setColour(String colour) {
            this.colour = colour;
        }
        public List<Colour> getSubColours() {
            return subColours;
        }
        public void setSubColours(List<Colour> subColours) {
            this.subColours = subColours;
        }
        @Override
		public String toString() {
            return "MyColour["+colour+"]";
        }
    }
    public static class MyPoint {
        private int x, y;

        public MyPoint() {
        	//
        }
        public MyPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return x;
        }
        public void setX(int x) {
            this.x = x;
        }
        public int getY() {
            return y;
        }
        public void setY(int y) {
            this.y = y;
        }      
    }
    public Map<String, Colour> getANestedMap() {
        return aNestedMap;
    }
    public void setANestedMap(Map<String, Colour> map) {
        aNestedMap = map;
    }
    public int[] getAnIntArray() {
        return anIntArray;
    }
    public void setAnIntArray(int[] anIntArray) {
        this.anIntArray = anIntArray;
    }
    public List<List<Colour>> getAListOfLists() {
        return aListOfLists;
    }
    public void setAListOfLists(List<List<Colour>> listOfLists) {
        aListOfLists = listOfLists;
    }
    public Set<Set<Colour>> getASetOfSets() {
        return aSetOfSets;
    }
    public void setASetOfSets(Set<Set<Colour>> setOfSets) {
        aSetOfSets = setOfSets;
    }
    public List<Set<Colour>> getAListOfSets() {
        return aListOfSets;
    }
    public void setAListOfSets(List<Set<Colour>> listOfSets) {
        aListOfSets = listOfSets;
    }
    public Set<List<Colour>> getASetOfLists() {
        return aSetOfLists;
    }
    public void setASetOfLists(Set<List<Colour>> setOfLists) {
        aSetOfLists = setOfLists;
    }
    public int[][] getA2DArray() {
        return a2DArray;
    }
    public void setA2DArray(int[][] array) {
        a2DArray = array;
    }
    public Set<List<Colour>> getAnEmptySetOfLists() {
        return anEmptySetOfLists;
    }
    public void setAnEmptySetOfLists(Set<List<Colour>> anEmptySetOfLists) {
        this.anEmptySetOfLists = anEmptySetOfLists;
    }
    public List<Set<Colour>> getAnEmptyListOfSets() {
        return anEmptyListOfSets;
    }
    public void setAnEmptyListOfSets(List<Set<Colour>> anEmptyListOfSets) {
        this.anEmptyListOfSets = anEmptyListOfSets;
    }
    public InterfaceType getSubtypeProperty() {
        return subtypeProperty;
    }
    public void setSubtypeProperty(InterfaceType subtypeProperty) {
        this.subtypeProperty = subtypeProperty;
    }
    public List<InterfaceType> getAListOfAttribute() {
        return aListOfAttribute;
    }
    
    public void setAListOfAttribute(List<InterfaceType> listOfAttribute) {
        aListOfAttribute = listOfAttribute;
    }
    public Set<InterfaceType> getASetOfAttribute() {
        return aSetOfAttribute;
    }
    public void setASetOfAttribute(Set<InterfaceType> setOfAttribute) {
        aSetOfAttribute = setOfAttribute;
    }
    public Colour getColour2() {
        return colour2;
    }
    public void setColour2(Colour colour2) {
        this.colour2 = colour2;
    }
    public MyPoint getPoint2() {
        return point2;
    }
    public void setPoint2(MyPoint point2) {
        this.point2 = point2;
    }
    public Colour getColour3() {
        return colour3;
    }
    public void setColour3(Colour colour3) {
        this.colour3 = colour3;
    }
    public String getAString() {
        return aString;
    }
    public void setAString(String string) {
        this.aString = string;
    }
}
