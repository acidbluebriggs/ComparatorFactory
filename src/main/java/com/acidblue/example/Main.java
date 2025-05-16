package com.acidblue.example;

import com.acidblue.beans.SortUtil;

import java.util.LinkedList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    final List<PersonBean> people = new LinkedList<>();

    people.add(new PersonBean("Benjamin", "Franklin"));
    people.add(new PersonBean("Ronald", "Reagan"));
    people.add(new PersonBean("George", "Bush"));
    people.add(new PersonBean("George", "Washington"));

    //sort by last name
    SortUtil.sort(people, "lastName");
    printPeople(people);
    // Output:
    // George, Bush
    // Benjamin, Franklin
    // Ronald, Reagan
    // George, Washington

    //sort by first name
    SortUtil.sort(people, "firstName");
    printPeople(people);
    // Output:
    // Benjamin, Franklin
    // George, Bush
    // George, Washington
    // Ronald, Reagan

    //sort by last name and first name
    SortUtil.sort(people, "lastName", "firstName");
    printPeople(people);
    // Output:
    // George, Bush
    // Benjamin, Franklin
    // Ronald, Reagan
    // George, Washington

    // And now records
    final List<PersonRecord> records = new LinkedList<>();

    records.add(new PersonRecord("Benjamin", "Franklin"));
    records.add(new PersonRecord("Ronald", "Reagan"));
    records.add(new PersonRecord("George", "Bush"));
    records.add(new PersonRecord("George", "Washington"));

    //sort by last name
    SortUtil.sort(records, "lastName");
    printRecords(records);
    // Output:
    // George, Bush
    // Benjamin, Franklin
    // Ronald, Reagan
    // George, Washington

    //sort by first name
    SortUtil.sort(records, "firstName");
    printRecords(records);
    // Output:
    // Benjamin, Franklin
    // George, Bush
    // George, Washington
    // Ronald, Reagan

    //sort by last name and first name
    SortUtil.sort(records, "lastName", "firstName");
    printRecords(records);
    // Output:
    // George, Bush
    // Benjamin, Franklin
    // Ronald, Reagan
    // George, Washington
  }

  private static void printRecords(List<PersonRecord> records) {
    for (var r : records) {
      System.out.println(r.firstName() + ", " + r.lastName());
    }
  }

  private static void printPeople(List<PersonBean> people) {
    for (var r : people) {
      System.out.println(r.getFirstName() + ", " + r.getLastName());
    }
  }
}

record PersonRecord(String firstName, String lastName) {}

class PersonBean {

  private final String firstName;
  private final String lastName;

  public PersonBean(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public final String getFirstName() {
    return this.firstName;
  }

  public final String getLastName() {
    return this.lastName;
  }
}


