package com.acidblue.beans;

import java.util.Comparator;

/**
 * Compares two objects by comparing a named JavaBeans property.
 * <p/>
 * Comparators only allow two objects to be compared in entierity. This provides
 * a generic way of comparing two JavaBeans by specifying their property name
 * and the appropriate values are computed dynamically at run time.
 * <p/>
 * Example of use:
 * <p/>
 * <pre>
 *      //example bean
 *      public class PersonBean {
 *          private String name;
 *          public PersonBean(String name) {
 *              this.name = name;
 *          }
 *          public String getName() {
 *              return name; }
 *          }
 * <p/>
 *      Comparator c = new BeanPropertyComparator("name");
 *      // returns -1
 *      c.compare(new PersonBean("Alex"), new PersonBean("Bob"));
 * <p/>
 *      // returns 0
 *      c.compare(new PersonBean("Alex"), new PersonBean("Alex));
 * <p/>
 *      // returns 1
 *      c.compare(new PersonBean("Bob"), new PersonBean("Alex"));
 * </pre>
 * <p/>
 * <b>Note</b> the <i>property</i> is the name of a JavaBean's property, and
 * according to the JavaBeans specification must start with a lower case letter.
 * So, the property <code>name</code> is translated to the method
 * <code>getName()</code> using the standard {@link java.beans.Introspector}
 * process.
 * <p/>
 * <b>Note</b> this uses the {@link java.beans.BeanInfo} class to obtain the
 * accessor method, so that provided a {@link java.beans.BeanInfo} is given, it
 * will work regardless of the name of the read method.
 *
 * @author Alex Blewitt &lt;<I><A href="mailto:Alex.Blewitt@ioshq.com">Alex.Blewitt@ioshq.com</A></I>&gt;
 * @version 1.0
 */
public class BeanPropertyComparator<T>
        implements Comparator<T> {

    /**
     * The property name.
     * <p/>
     * Must start with a lower-case letter as per the JavaBeans specification.
     */
    private String property;

    /**
     * The alternative comparator to use.
     * <p/>
     * Allows properties to be compared with different comparators. If this is not
     * provided, uses the object's own {@link Comparable} interface if it exists.
     */
    private Comparator<T> comparator;


    /**
     * Creates a new Comparator using the property defined.
     * <p/>
     * Property names must start with a lower-case letter as per the JavaBeans
     * specification. This uses {@link java.beans.Introspector introspection} to
     * obtain the property dynamically from its access method.
     * <p/>
     * The properties are compared assuming that the bean implements {@link
     * java.lang.Comparable}.
     *
     * @param property the property name to use (starts with a lower case letter)
     */
    public BeanPropertyComparator(final String property) {

        this(property, null);
    }


    /**
     * Creates a new Comparator using the property defined.
     * <p/>
     * Property names must start with a lower-case letter as per the JavaBeans
     * specification. This uses {@link java.beans.Introspector introspection} to
     * obtain the property dynamically from its access method.
     * <p/>
     * The properties are compared using the given comparator.
     *
     * @param property   the property name to use (starts with a lower case
     *                   letter)
     * @param comparator the comparator to compare properties
     */
    public BeanPropertyComparator(final String property, final Comparator<T> comparator) {

        this.property = property;
        this.comparator = comparator;
    }


    /**
     * Compares the two objects using either the given
     * {@link java.util.Comparator} or using the {@link java.lang.Comparable}
     * interface.
     * <p/>
     * If no {@link java.util.Comparator} is given during construction,
     * and either <CODE>o1</CODE> or <CODE>o2</CODE> implements
     * {@link java.lang.Comparable}, then it is cast to
     * {@link java.lang.Comparable} and compared with the other.
     * <p/>
     * If no {@link java.util.Comparator} is given, and neither <CODE>o1</CODE>
     * or <CODE>o2</CODE> implement {@link java.lang.Comparable} then the
     * objects are converted to a <CODE>String</CODE> using the
     * {@link java.lang.String#valueOf} method (which calls
     * {@link java.lang.Object#toString}), and standard <CODE>String</CODE>
     * comparison is performed.
     *
     * @param o1 the object to compare
     * @param o2 the object to compare
     * @return <DL>
     *         <DT>-1</DT><DD>if <CODE>o1</CODE> &lt; <CODE>o2</CODE></DD>
     *         <DT>0</DT><DD>if <CODE>o1</CODE> = <CODE>o2</CODE></DD>
     *         <DT>1</DT><DD>if <CODE>o1</CODE> &gt; <CODE>o2</CODE></DD>
     *         </DL>
     * @throws IllegalArgumentException if there is no property named
     *                                  <I>property</I> or there is a problem accessing it with the
     *                                  <CODE>PropertyDescriptor</CODE>
     * @see com.statcom.util.beans.BeanPropertyUtil
     */
    @SuppressWarnings("unchecked")
    public int compare(final T o1, final T o2) throws IllegalArgumentException {

        // Get the value of the properties
        final T p1 = (T) BeanPropertyUtil.getProperty(property, o1);
        final T p2 =  (T) BeanPropertyUtil.getProperty(property, o2);

        final int value;

        if (comparator == null) {
            // try to find p1 or p2 that implements Comparator
            if (p1 instanceof Comparable) {
                value = ((Comparable<T>) p1).compareTo(p2);
            } else if (p2 instanceof Comparable) {
                value = ((Comparable<T>) p2).compareTo(p1);
            } else {
                // we have no comparable instances; try String comparison
                final String s1 = String.valueOf(p1); // calls toString safely
                final String s2 = String.valueOf(p2);
                value = s1.compareTo(s2); // String implements comparable
            }
        } else {
            value = comparator.compare(p1, p2);
        }
        
        return value;
    }
}