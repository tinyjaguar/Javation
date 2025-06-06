package org.example.Bonus;

import java.util.Date;

//    Strings are immutable.
//    s1.concat(" World") returns a new object, but it’s not assigned.
       /* public static void main(String[] args) {
            String s1 = "Hello";
            s1.concat(" World");

            System.out.println(s1); // Output: Hello
        }*/


//2. Immutable String
//Original s remains unchanged.
//    New string s2 is created.


    /*heap memory - str
    scp(String constant pool, hello) String constant pool

    String s = "hello";
    String str = new String("hello");*/



class ImmutableExample {
    public static void main(String[] args) {
        String s = "Java";
        String scpstring = "Java";
        String s2 = s.concat(" World");
        String s4 = s2.intern();
        String s3 = "Java World";

        System.out.println(s);   // Java
        System.out.println(s2);  // Java World
        System.out.println(s3==s4);
    }
}


//3. String Comparison
/*== compares references.
.equals() compares values.*/
class StringComparison {
    public static void main(String[] args) {
        String a = "Java";
        String b = "Java";
        String c = new String("Java");

        System.out.println(a == b);       // true (same pool reference)
        System.out.println(a == c);       // false (new object)
        System.out.println(a.equals(c));  // true (value comparison)
    }
}


//4. StringBuffer – Mutable, Thread-safe
/*    Safe in multithreaded environments
    Synchronized methods*/
class StringBufferExample {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("Hello");
        sb.append(" World");

        System.out.println(sb); // Output: Hello World
    }
}

/*5. StringBuilder – Mutable, Not Thread-safe (Faster)
     Faster than StringBuffer in single-threaded cases*/
class StringBuilderExample {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");

        System.out.println(sb); // Output: Hello World
    }
}

/*

    Feature	:               String	                    StringBuffer	                StringBuilder
    Mutability	            Immutable	                Mutable	                        Mutable
    Thread Safety	        Safe (immutable)	        Thread-safe (synchronized)	    Not thread-safe
    Performance	            Medium	                    Slower (due to sync)	        Faster
    Package	                java.lang.String	        java.lang.StringBuffer	        java.lang.StringBuilder
    Use Case	            Constants, config values	Multi-threaded                   mutable strings	Fast, single-threaded operations
*/


//Example Comparing All Three

class ComparisonExample {
    public static void main(String[] args) {
        // String
        String str = "Java";
        str.concat(" World");
        System.out.println("String: " + str);  // Java

        // StringBuffer
        StringBuffer sb = new StringBuffer("Java");
        sb.append(" World");
        System.out.println("StringBuffer: " + sb);  // Java World

        // StringBuilder
        StringBuilder sb2 = new StringBuilder("Java");
        sb2.append(" World");
        System.out.println("StringBuilder: " + sb2); // Java World
    }
}



//What Makes a Class Immutable?

/*
    To make a class immutable:

    Declare the class as final (cannot be extended).

    Make all fields private and final.

    Do not provide setters.

    Only allow initialization via constructor.

    If any field is mutable (like a list or date), return a defensive copy in the getter. or cloning
*/

//    Example: Person Immutable Class

final class Person {

    final private String name;
    private final int age;
    private final Date birthDate; // Mutable object


    // -------------------------
    // 1. Normal Constructor
    // -------------------------
    public Person(String name, int age, Date birthDate) {
        this.name = name;
        this.age = age;
        this.birthDate = new Date(birthDate.getTime()); // Defensive copy
    }

    // -------------------------
    // 2. Copy Constructor
    // -------------------------
    public Person(Person other) {
        this.name = other.name;
        this.age = other.age;
        this.birthDate = new Date(other.birthDate.getTime()); // Defensive copy
    }

    // -------------------------
    // 3. Getters (no setters!)
    // -------------------------
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Date getBirthDate() {
        return new Date(birthDate.getTime()); // Defensive copy
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + ", birthDate=" + birthDate + '}';
    }
}




public class Bonus {

    public static void main(String[] args) {
        Bonus t1 = new Bonus();
        t1.extracted();
    }


    private void extracted() {
        Date birth = new Date();
        Person original = new Person("Alice", 30, birth);

        System.out.println("Original: " + original);

        // Attempt to mutate original date object
        birth.setTime(0);
        System.out.println("After birth date modified: " + original);

        // Copy constructor
        Person copy = new Person(original);
        System.out.println("Copy: " + copy);
    }




}
