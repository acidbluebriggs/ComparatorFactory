package com.acidblue.beans;

import java.util.Comparator;

/**
 * Allows two comparators to be merged together.
 * <p/>
 * Allows two comparators to be merged, so that if the major comparator
 * returns equal, the other comparator is used. This can then be used to form
 * composite comparisons.
 * <p/>
 * <P><B>Example</B> <PRE> java.util.Collections.sort(list, new
 * CompositeComparator( new BeanPropertyComparator("lastName"), new
 * BeanPropertycComparator("firstName") ) ); </PRE>
 * <p/>
 * The example above sorts items on last name, and then first name. Two beans
 * with the same last name will then be sorted on first name.
 *
 * @author briggs <i><a href="mailto:acidbriggs@gmail.com">acidbriggs@gmail.com</a></i>
 * @author Alex Blewitt <I><A href="mailto:Alex.Blewitt@ioshq.com">Alex.Blewitt@ioshq.com</A></I>&gt;
 * @version 2.0
 * @see BeanPropertyComparator
 */
public class CompositeComparator<T>
        implements Comparator<T> {
    /**
     * The major comparator.
     */
    private final Comparator<T> major;
    /**
     * The minor comparator.
     */
    private final Comparator<T> minor;


    /**
     * Create a new <code>CompositeComparator</code> using the given
     * comparators.
     *
     * @param major the most significant comparator
     * @param minor the least significant comparator
     */
    public CompositeComparator(final Comparator<T> major, final Comparator<T> minor) {

        if (major == null) {
            throw new NullPointerException("major was null");
        }

        if (minor == null) {
            throw new NullPointerException("minor was null");
        }

        this.major = major;
        this.minor = minor;
    }


    /**
     * Compare two objects using the comparators given.
     * <p/>
     * Compares using the most significant comparator first. If they are equal,
     * then returns the comparison with the least significant comparator.
     *
     * @param o1 the object to compare
     * @param o2 the object to compare to
     * @return <pre>
     *                 int result = major.compare(o1,o2);
     *                 if (result != 0) {
     *                   return result;
     *                 } else {
     *                   return minor.compare(o1,o2);
     *                 }
     *                 </pre>
     */
    public int compare(final T o1, final T o2) {

        final int result = major.compare(o1, o2);

        return result != 0 ? result : minor.compare(o1, o2);
    }

}
