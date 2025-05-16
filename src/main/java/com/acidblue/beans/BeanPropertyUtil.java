package com.acidblue.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Provides utility methods for accessing JavaBeans.
 *
 * @author briggs <a href="mailto:acidbriggs@gmail.com">acidbriggs@gmail.com</a>
 * @author Alex Blewitt <i><a href="mailto:Alex.Blewitt@ioshq.com">Alex.Blewitt@ioshq.com</a></i>
 * @version 1.1
 */
public final class BeanPropertyUtil {
    /**
     * Implements a static utility class; cannot be instantiated
     */
    private BeanPropertyUtil() {
    }


    /**
     * Stores the list of class/property names with associated read methods
     */
    private static final Map<String, Method> map = new WeakHashMap<>();


    /**
     * Return the value of the JavaBean <CODE>property</CODE> from <CODE>instance</CODE>.
     * <p/>
     * Uses {@link java.beans.Introspector} to obtain the appropriate {@link java.beans.PropertyDescriptor}, and then
     * the accessor method is invoked dynamically using {@link java.lang.reflect.Method}.
     *
     * @param name     the property name to look up
     * @param instance the bean instance to use
     * @return Object value of the property. Primitive types are wrapped automatically by the reflection package.
     * @throws IllegalArgumentException if the <I>name</I> does not exist in <I>instance</I> or there is an exception
     *                                  dynamically invoking the accessor method .
     * @see java.beans.Introspector
     * @see java.beans.PropertyDescriptor
     * @see java.beans.PropertyDescriptor#getReadMethod

     */
    public static Object getProperty(final String name,
                                     final Object instance)
            throws IllegalArgumentException {

        try {
            final Method read = getReadMethod(name, instance);
            
            if (read == null) {
                throw new IllegalArgumentException(String.format("Cannot find instance with property '%s'", name));
            }
            
            return read.invoke(instance);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException(String.format("Problem accessing property '%s': %s",
                    name, exception.getMessage()), exception);

        }
    }


    /**
     * Obtain the read method for property <em>name</em> associated with <em>instance</em>. Once the method is located,
     * it is stored in {@link #map} using a format <code>fully.qualified.Class#name</code> so that it is obtained
     * quicker on subsequent requests. As the map is a {@link java.util.WeakHashMap} then memory requests may cause
     * parts of this cache to be lost.
     *
     * @param name     the property name
     * @param instance the instance to query
     * @return the method of the underlying property
     * @throws IntrospectionException   if this method attempt to read an unreadable property
     * @throws IllegalArgumentException if the property given cannot be found in the given instance
     */
//    private static Method getReadMethod(final String name,
//                                        final Object instance)
//            throws IllegalArgumentException, IntrospectionException {
//
//        final String id = instance.getClass() + "#" + name;
//
//        if (!map.containsKey(id)) {
//            return locateMethod(instance, name);
//        } else {
//            throw new IllegalArgumentException(String.format("Cannot find instance with property '%s'", name));
//        }
//    }

    public static Method getReadMethod(final String name, final Object instance) {
        try {
            // First try the traditional JavaBean getter pattern
            String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String getterName = "get" + capitalizedName;

            try {
                return instance.getClass().getMethod(getterName);
            } catch (NoSuchMethodException e) {
                // If traditional getter isn't found, try Record pattern
                return instance.getClass().getMethod(name);
            }
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method locateMethod(final Object instance, final String name)
            throws IntrospectionException {
        // Get the BeanInfo, either from BeanInfo class or reflection
        final BeanInfo info = Introspector.getBeanInfo(instance.getClass(), Introspector.USE_ALL_BEANINFO);

        // Lookup all of this bean's properties
        final PropertyDescriptor pds[] = info.getPropertyDescriptors();

        // Search through the list for the one with 'name'
        for (final PropertyDescriptor pd : pds) {
            if (name.equals(pd.getName())) {
                final Method method = pd.getReadMethod();
                map.put(name, method);
                break;
            }
        }

        return map.get(name);
    }
}