package org.example;

class Organization {

    private String name;
    private String address;

    private static int floor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Organization(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Organization() {
        this.name = "decimal tech";
        this.address = "gurgaon";
    }

    public static Deparments builder(){
        return new Deparments();
    }

    public static class Deparments{
            private String[] deptname = new String[]{"HR","DEVELOPMENT"};
            private String name;
            private String address;

            public Deparments name(String name){
                this.name = name;
                floor=1;
                return this;
            }

            public Deparments address(String address){
                this.address = address;

                return this;
            }

            public Organization build(){
                return new Organization(name, address);
            }
    }

    public class Employee{

        private String empName;

        @Override
        public String toString() {
            return "Employee{" +
                    "empName='" + empName + '\'' +
                    '}';
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }
    }



}

public class Main {

    private static Main mystatic = new Main();  // same class non-static will throw stackoverflowerror
    private Organization myorg = new Organization();

    public static void main(String[] args) {
        Main m = new Main();

        System.out.println("=====================memeber variables, static, non-static, local variables");
        System.out.println(mystatic.toString());
        System.out.println(m.nonstaticm1());
        System.out.println(mystatic.nonstaticm1());

        System.out.println("=====================static(nested class) and inner class");
        m.testbuilder();
    }

    private void testbuilder(){
        Organization build1 = Organization.builder().name("decimal").address("gurgaon").build();
        Organization build2 = Organization.builder().name("genpact").address("noida").build();
        Organization.Deparments deparments = new Organization.Deparments();
        System.out.println(deparments.toString());
        System.out.println(build1);
        System.out.println(build2);
        Organization.Employee employee = build1.new Employee();
        employee.setEmpName("sanskar");
        System.out.println(employee.toString());
    }

    @Override
    public String toString() {
        return "Main{" +
                "myorg=" + myorg +
                '}';
    }

    public String nonstaticm1(){
        System.out.println(mystatic.toString());
        return myorg.toString();
    }

}