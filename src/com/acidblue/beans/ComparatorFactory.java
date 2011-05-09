package com.acidblue.beans;

import java.util.Comparator;

/**
 * A simple factory for creating Comparators for beans based on their
 * properties.
 *
 * @author briggs <a href="mailto:acidbriggs@gmail.com">acidbriggs@gmail.com</a>
 * @since Aug 30, 2005 - 12:10:28 PM
 */
public class ComparatorFactory
{

    private ComparatorFactory()
    {

    }


    /**
     * Returns a comparator for a specified bean property.
     *
     * @return A comparator instance
     */
    public static Comparator getComparator(String propertyName)
    {
        if (propertyName == null)
        {
            throw new IllegalArgumentException("propertyName cannot be null");
        }
        return new BeanPropertyComparator(propertyName);
    }


    /**
     * Returns a comparator that will compare properties based on two inputs.
     *
     * @return a comparator instance
     */
    public static Comparator getComparator(String majorProperty,
                                           String minorProperty)
    {
        if (majorProperty == null)
        {
            throw new IllegalArgumentException("majorProperty cannot be null");
        }
        if (minorProperty == null)
        {
            throw new IllegalArgumentException("minor cannot be null");
        }

        BeanPropertyComparator bc1 = new BeanPropertyComparator(majorProperty);
        BeanPropertyComparator bc2 = new BeanPropertyComparator(minorProperty);

        return new CompositeComparator(bc1, bc2);
    }
}
