package com.acidblue.beans;

import java.util.Comparator;

/**
 * Allows two comparators to be merged together.
 * <p/>
 * Allows two compatarators to be merged, so that if the major comparator
 * returns equal, the other comparator is used. This can then be used to form
 * composite comparaisons.
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
public class CompositeComparator
        implements Comparator
{
    /**
     * The major comparator.
     */
    private Comparator major;
    /**
     * The minor comparator.
     */
    private Comparator minor;


    /**
     * Create a new <code>CompositeComparator</code> using the given
     * comparators.
     *
     * @param major the most significant comparator
     * @param minor the least significant comparator
     */
    public CompositeComparator(Comparator major, Comparator minor)
    {
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
     *
     * @return <pre>
     *         int result = major.compare(o1,o2);
     *         if (result != 0) {
     *           return result;
     *         } else {
     *           return minor.compare(o1,o2);
     *         }
     *         </pre>
     */
    public int compare(Object o1, Object o2)
    {
        int result = major.compare(o1, o2);
        if (result != 0)
        {
            return result;
        }
        else
        {
            return minor.compare(o1, o2);
        }
    }

}
