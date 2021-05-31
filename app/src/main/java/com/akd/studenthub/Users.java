package com.akd.studenthub;

public class Users {



    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    private String Phone;
    private String Course;
    private String Email;
    private String Experties;
    private String Interest;
    private String Student_Occupation;
    private String Username;
    private String ProfileUrl;
    private String Year;
    private String status;
    private String Search_Username;
    private String Search_Interest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users(String id) {
        this.id = id;
    }

    private String id;

    public String getSearch_Username() {
        return Search_Username;
    }

    public void setSearch_Username(String search_Username) {
        Search_Username = search_Username;
    }

    public String getSearch_Interest() {
        return Search_Interest;
    }

    public void setSearch_Interest(String search_Interest) {
        Search_Interest = search_Interest;
    }

    public Users() {}


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users(String Search_Username, String Search_Interest, String profileUrl, String course, String email, String experties, String interest, String student_Occupation, String username, String year, String Phone, String status) {
        ProfileUrl = profileUrl;
        Course = course;
        Email = email;
        Experties = experties;
        Interest = interest;
        Student_Occupation = student_Occupation;
        Username = username;
        Year = year;
        this.status = status;
    }

    public String getProfileUrl() {
        return ProfileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        ProfileUrl = profileUrl;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getExperties() {
        return Experties;
    }

    public void setExperties(String experties) {
        Experties = experties;
    }

    public String getInterest() {
        return Interest;
    }

    public void setInterest(String interest) {
        Interest = interest;
    }

    public String getStudent_Occupation() {
        return Student_Occupation;
    }

    public void setStudent_Occupation(String student_Occupation) {
        Student_Occupation = student_Occupation;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }
}
