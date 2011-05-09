package com.acidblue.beans;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple utility class for sorting collections based on its contained beans'
 * properties.
 *
 * <pre>
 *      //example bean
 *      public class PersonBean {
 *          private String firstName;
 *          private String lastName;
 *
 *          public PersonBean(String firstName, String lastName) {
 *              this.firstName = firstName;
 *              this.lastName  = lastName;
 *
 *          }
 *
 *          public final String getFirstName() {
 *              return this.firstName;
 *          }
 *
 *          public final String getLastName() {
 *              return this.lastName;
 *          }
 * <p/>
 *      List people = new LinkedList();
 *      people.add(new PersonBean("Benjamin", "Franklin"));
 *      people.add(new PersonBean("Ronald", "Reagan"));
 *      people.add(new PersonBean("George", "Bush"));
 *      people.add(new PersonBean("George", "Washington"));
 *
 *      //sort by last name
 *      SortUtil.sort(people, "lastName");
 *
 *      //sort by first name
 *      SortUtil.sort(people, "firstName");
 *
 *      //sort by last name and first name
 *      SortUtil.sort(people, "lastname", "firstName");
 * </pre>
 * <p/>
 * @author briggs <a href="mailto:acidbriggs@gmail.com">acidbriggs@gmail.com</a>
 * @version 1.0
 */
public class SortUtil
{

    /**
     * Sorts a List based on a JavaBean property common to the elements within
     * the Collection.
     *
     * @param target       The list ot be sorted
     * @param propertyName the property to sort by
     */
    public static void sort(final List target, String propertyName)
    {
        final Comparator comparator =
                ComparatorFactory.getComparator(propertyName);
        Collections.sort(target, comparator);
    }


    /**
     * Sorts a List based on a JavaBean property common to the elements within
     * the Collection.
     *
     * @param target        The list ot be sorted
     * @param majorProperty the first property to sort by
     * @param minorProperty the second property to sort by
     */
    public static void sort(final List target, String majorProperty,
                            String minorProperty)
    {
        final Comparator composite =
                ComparatorFactory.getComparator(majorProperty, minorProperty);
        Collections.sort(target, composite);
    }
}
