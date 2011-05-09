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
public class BeanPropertyUtil
{
    /**
     * Implements a static utility class; cannot be instantiated
     */
    private BeanPropertyUtil()
    {
    }


    /**
     * Stores the list of class/property names with associated read methods
     */
    private static Map map = new WeakHashMap();


    /**
     * Return the value of the JavaBean <CODE>property</CODE> from
     * <CODE>instance</CODE>.
     * <p/>
     * Uses {@link java.beans.Introspector} to obtain the appropriate {@link
     * java.beans.PropertyDescriptor}, and then the accessor method is invoked
     * dynamically using {@link java.lang.reflect.Method}.
     *
     * @param name     the property name to look up
     * @param instance the bean instance to use
     *
     * @return Object value of the property. Primitive types are wrapped
     *         automatically by the reflection package.
     *
     * @throws IllegalArgumentException if the <I>name</I> does not exist in
     *                                  <I>instance</I> or there is an exception
     *                                  dynamically invoking the accessor method
     *                                  .
     * @see java.beans.Introspector
     * @see java.beans.PropertyDescriptor
     * @see java.beans.PropertyDescriptor#getReadMethod
     * @see java.lang.reflect.Method#invoke
     */
    public static Object getProperty(final String name,
                                     final Object instance)
            throws IllegalArgumentException
    {

        try
        {
            Method read = getReadMethod(name, instance);
            if (read == null)
            {
                throw new IllegalArgumentException(
                        "Cannot find instance with property '" + name + "'");
            }
            return read.invoke(instance, null);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Problem accessing property '" +
                                               name +
                                               "': " +
                                               e.getMessage());
        }
    }


    /** Obtain the read method for property <em>name</em> associated with <em>instance</em>.
     * Once the method is located, it is stored in {@link #map} using a
     * format <code>fully.qualified.Class#name</code> so that it is
     * obtained quicker on subsequent requests. As the map is a
     * {@link java.util.WeakHashMap} then memory requests may cause parts
     * of this cache to be lost.
     * @param name the property name
     * @param instance the instance to query
     */
    private static Method getReadMethod(final String name,
                                        final Object instance)
            throws IllegalArgumentException, IntrospectionException
    {

        final String id = instance.getClass() + "#" + name;
        Method read = (Method)map.get(id);
        if (read == null)
        {
            // Get the BeanInfo, either from BeanInfo class or reflection
            BeanInfo info = Introspector.getBeanInfo(
                    instance.getClass(), Introspector.USE_ALL_BEANINFO);
            // Lookup all of this bean's properties
            PropertyDescriptor pds[] = info.getPropertyDescriptors();
            // Search through the list for the one with 'name'
            for (int i = 0; i < pds.length; i++)
            {
                PropertyDescriptor pd = pds[i];
                if (name.equals(pd.getName()))
                {
                    // and now that we've found it, invoke it with no arguments (null)
                    read = pd.getReadMethod();
                    map.put(id, read);
                    break;
                }
            }
        }
        if (read == null)
        {
            throw new IllegalArgumentException(
                    "Cannot find instance with property '" + name + "'");
        }
        return read;
    }
}
