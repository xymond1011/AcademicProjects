// Made by Michael Justine R. Baladad
public class Citizen implements Comparable<Citizen>{
    private String fullName;
    private String email;
    private String address;
    private int age;
    private boolean resident;
    private int district;
    private char gender;

    public Citizen() {
        this.fullName = "";
        this.email = "";
        this.address = "";
        this.age = 18;
        this.resident = true;
        this.district = 0;
        this.gender = 'm';
    }

    public Citizen(String name, String email, String address, int age, boolean resident, int district, char gender) {
        this.fullName = name;
        this.email = email;
        this.address = address;
        this.age = age;
        this.resident = resident;
        this.district = district;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public boolean isResident() {
        return resident;
    }
    public String getResidency() {
        String value;
         if(isResident()) {
            value = "Resident";
        } else {
            value = "Non- Resident";
        }

         return value;
    }

    public int getDistrict() {
        return district;
    }

    public char getGender() {
        return gender;
    }

    @Override
    public int compareTo(Citizen other) {
        return this.fullName.compareTo(other.getFullName());
    }

    @Override
    public String toString() {
        return String.format("%-25s%-65s%-45s%-15d%-20s%-15d%-10s%n",fullName,email,address,age,getResidency(),district,gender);
    }
}
