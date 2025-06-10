package org.example.exception;


/*Key Concepts to Cover:
Exception hierarchy in Java (Checked vs Unchecked vs Errors)
Try-catch-finally blocks and multi-catch
Try-with-resources (Java 7+)
Custom exceptions (checked & unchecked)
Chained exceptions (using initCause() and constructors)
Exception propagation and stack trace analysis
Using throw vs throws
Best practices in exception handling (logging, wrapping exceptions)
Suppressed exceptions (try-with-resources)
Using Thread.UncaughtExceptionHandler*/


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Basic Try-Catch
class BasicTryCatch {
    public static void main(String[] args) {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e);
        }
    }
}

//Multiple Catch Blocks



class MultipleCatch {
    public static void main(String[] args) {
        try {
            String str = null;
            System.out.println(str.length());
        } catch (NullPointerException e) {
            System.out.println("NullPointer caught!");
        } catch (Exception e) {
            System.out.println("General exception caught!");
        }
    }
}

//3. Finally Block
class FinallyDemo {
    public static void main(String[] args) {
        try {
            int arr[] = new int[5];
            arr[10] = 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught: " + e);
        } finally {
            System.out.println("Finally always executes.");
        }
    }
}


//4. Nested Try-Catch
class NestedTry {
    public static void main(String[] args) {
        try {
            try {
                int a = 10 / 0;
                System.out.println("try block......");
            }
         catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e);
            }
        } catch (Exception e) {
            System.out.println("Outer catch: " + e);
        }
    }
}


//5. Throwing Custom Exception

class AgeException extends Exception {
    public AgeException(String message) {
        super(message);
    }

    public void main(){
        try {
            AgeException.main(new String[]{"12", "23"});
        } catch (AgeException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws AgeException {
        int age = 15;
        if (age < 18)
            throw new AgeException("Underage for driving");
        else
            System.out.println("Eligible");
    }

}

//6. Throws Keyword
class ThrowsDemo {
    static void checkFile() throws java.io.IOException {
        throw new java.io.IOException("File not found");
    }

    public static void main(String[] args) {
        try {
            checkFile();
        } catch (Exception e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}

//Try-with-Resources
class TryWithResources {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
            System.out.println(br.readLine());
        } catch (IOException e) {
            System.out.println("Caught: " + e);
        }
    }
}

//8. Exception Propagation
class ExceptionPropagation {
    void method1() {
        int a = 10 / 0;
    }

    void method2() {
        method1();
//        int a = 10 / 0;
    }

    public static void main(String[] args) {
        ExceptionPropagation obj = new ExceptionPropagation();
        try {
            obj.method2();
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e);
        }
    }
}

//9. Catch Multiple Exceptions (Java 7+)

class MultiCatch {
    public static void main(String[] args) {
        try {
            int a = 5 / 0;
            String s = null;
            s.length();
        } catch (ArithmeticException | NullPointerException e) {
            System.out.println("Caught: " + e);
        }
    }
}

//10. Re-throwing Exception
class RethrowDemo {
    public static void validate(int age) throws Exception{
        try {
            if (age < 18) throw new Exception("Underage");
        } catch (Exception e) {
            System.out.println("Logging: " + e.getMessage());
            throw e;  // Rethrow
        }
    }

    public static void main(String[] args) {
        try {
            validate(15);
        } catch (Exception e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
    }
}


//Custom Checked Exception
class InvalidAmountException extends Exception {
    public InvalidAmountException(String msg) {
        super(msg);
    }
}

class CheckedCustomException {
    public static void main(String[] args) throws InvalidAmountException {
        int amount = -100;
        if (amount < 0)
            throw new InvalidAmountException("Negative amount not allowed");
    }
}


//12. Custom Unchecked Exception
class InvalidNameRuntime extends RuntimeException {
    public InvalidNameRuntime(String msg) {
        super(msg);
    }
}

class UncheckedCustomException {
    public static void main(String[] args) {
        String name = "";
        if (name.isEmpty())
            throw new InvalidNameRuntime("Name can't be empty");
    }
}


//13. Handling NumberFormatException
class NumberFormat {
    public static void main(String[] args) {
        try {
            int num = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("Caught: " + e);
        }
    }
}

//14. Chained Exception
class ChainedExceptions {
    public static void main(String[] args) {
        try {
            Throwable cause = new NullPointerException("Root cause");
            throw new Exception("Top exception", cause);
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            System.out.println("Cause: " + e.getCause());
        }
    }
}

//15. Exception in Constructor
class ConstructorException {
    ConstructorException() {
        int[] arr = new int[2];
        System.out.println(arr[5]); // triggers exception
    }

    public static void main(String[] args) {
        try {
            ConstructorException constructorException = new ConstructorException();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught: " + e);
        }
    }
}


public class ExceptionHandling {


    public static void main(String[] args) {
    }




}
