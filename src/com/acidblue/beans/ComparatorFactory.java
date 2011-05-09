package com.acidblue.beans;

import java.util.Comparator;

/**
 * A simple factory for creating Comparators for beans based on their
 * properties.
 *
 * @author briggs <a href="mailto:acidbriggs@gmail.com">acidbriggs@gmail.com</a>
 * @since Aug 30, 2005 - 12:10:28 PM
 * @see CompositeComparator
 */
public final class ComparatorFactory {

    /**
     * Private constructor due to this class being a singleton/utility
     */
    private ComparatorFactory() {

    }


    /**
     * Returns a comparator for a specified bean property.
     *
     * @param propertyName property name the comparator will compare to
     * @return A comparator instance
     */
    public static <T> Comparator<T> create(final String propertyName) {

        if (propertyName == null) {
            throw new IllegalArgumentException("propertyName cannot be null");
        }

        return new BeanPropertyComparator<T>(propertyName);
    }


    /**
     * Returns a comparator that will compare properties based on two inputs.
     *
     * @param first the first property to for the sort
     * @param next the secondary sort property
     * @param rest any other comparators to be added (order is preserved)
     * @return a comparator instance
     */
    public static <T> Comparator<T> create(final String first, final String next,
                                           final String... rest) {

        if (first == null) {
            throw new NullPointerException("first was null");
        }

        if (next == null) {
            throw new NullPointerException("second was null");
        }

        Comparator<T> temp = new CompositeComparator<T>(
                new BeanPropertyComparator<T>(first),
                new BeanPropertyComparator<T>(next));

        for (final String str : rest) {
            temp = new CompositeComparator<T>(temp, new BeanPropertyComparator<T>(str));
        }

        return temp;
    }

    /**
     * A method which combines comparators and returns a composite of all the comparators in the order
     * they are provided. At least two comparator must be provided.
     *
     * @param first the first comparator
     * @param next  the second comparator
     * @param rest  any other comparators to be added (order is preserved)
     * @param <T>   The type in the comparator
     * @return A new composite comparator
     */
    public static <T> Comparator<T> create(final Comparator<T> first, final Comparator<T> next,
                                           final Comparator<T>... rest) {

        if (first == null) {
            throw new NullPointerException("first was null");
        }

        if (next == null) {
            throw new NullPointerException("next was null");
        }

        Comparator<T> temp = new CompositeComparator<T>(first, next);

        for (final Comparator<T> comp : rest) {
            temp = new CompositeComparator<T>(temp, comp);
        }

        return temp;
    }
}