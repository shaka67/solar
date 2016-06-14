/**
 * Copyright (C) 2015 O'er, Inc. All Rights Reserved.
 */
package com.ouer.solar.bean.introspector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

import com.ouer.solar.bean.introspector.ClassDescriptor;
import com.ouer.solar.bean.introspector.ClassIntrospector;
import com.ouer.solar.bean.introspector.ConstructorDescriptor;
import com.ouer.solar.bean.introspector.FieldDescriptor;
import com.ouer.solar.bean.introspector.MethodDescriptor;
import com.ouer.solar.bean.introspector.PropertyDescriptor;
import com.ouer.solar.bean.sample.BeanSampleA;
import com.ouer.solar.bean.sample.BeanSampleB;
import com.ouer.solar.bean.sample.BeanSampleC;
import com.ouer.solar.bean.sample.Child;
import com.ouer.solar.bean.sample.Overload;
import com.ouer.solar.bean.sample.Parent;

/**
 * 
 * @author <a href="indra@ixiaopu.com">chenxi</a>
 * @version create on 2014年9月19日 上午2:10:33
 */
public class IntrospectorTest {

    @Test
    public void testBasic() {
        ClassDescriptor cd = ClassIntrospector.lookup(BeanSampleA.class);
        assertNotNull(cd);
        PropertyDescriptor[] properties = cd.getAllPropertyDescriptors();
        int c = 0;
        for (PropertyDescriptor property : properties) {
            if (property.isFieldOnlyDescriptor())
                continue;
            if (property.isPublic())
                c++;
        }
        assertEquals(2, c);

        Arrays.sort(properties, new Comparator<PropertyDescriptor>() {
            @Override
			public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        PropertyDescriptor pd = properties[0];
        assertEquals("fooProp", pd.getName());
        assertNotNull(pd.getReadMethodDescriptor());
        assertNotNull(pd.getWriteMethodDescriptor());
        assertNotNull(pd.getFieldDescriptor());

        pd = properties[1];
        assertEquals("shared", pd.getName());
        assertNull(pd.getReadMethodDescriptor());
        assertNull(pd.getWriteMethodDescriptor());
        assertNotNull(pd.getFieldDescriptor());

        pd = properties[2];
        assertEquals("something", pd.getName());
        assertNotNull(pd.getReadMethodDescriptor());
        assertNull(pd.getWriteMethodDescriptor());
        assertNull(pd.getFieldDescriptor());

        assertNotNull(cd.getPropertyDescriptor("fooProp", false));
        assertNotNull(cd.getPropertyDescriptor("something", false));
        assertNull(cd.getPropertyDescriptor("FooProp", false));
        assertNull(cd.getPropertyDescriptor("Something", false));
        assertNull(cd.getPropertyDescriptor("notExisting", false));
    }

    @Test
    public void testExtends() {
        ClassDescriptor cd = ClassIntrospector.lookup(BeanSampleB.class);
        assertNotNull(cd);

        PropertyDescriptor[] properties = cd.getAllPropertyDescriptors();
        int c = 0;
        for (PropertyDescriptor property : properties) {
            if (property.isFieldOnlyDescriptor())
                continue;
            if (property.isPublic())
                c++;
        }
        assertEquals(2, c);

        c = 0;
        for (PropertyDescriptor property : properties) {
            if (property.isFieldOnlyDescriptor())
                continue;
            c++;
        }
        assertEquals(3, c);
        assertEquals(4, properties.length);

        Arrays.sort(properties, new Comparator<PropertyDescriptor>() {
            @Override
			public int compare(PropertyDescriptor o1, PropertyDescriptor o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        PropertyDescriptor pd = properties[0];
        assertEquals("boo", pd.getName());
        assertNotNull(pd.getReadMethodDescriptor());
        assertNotNull(pd.getWriteMethodDescriptor());
        assertNotNull(pd.getFieldDescriptor());
        assertFalse(pd.isFieldOnlyDescriptor());

        pd = properties[1];
        assertEquals("fooProp", pd.getName());
        assertNotNull(pd.getReadMethodDescriptor());
        assertNotNull(pd.getWriteMethodDescriptor());
        assertNull(pd.getFieldDescriptor()); // null since field is not visible
        assertFalse(pd.isFieldOnlyDescriptor());

        pd = properties[2];
        assertEquals("shared", pd.getName());
        assertNull(pd.getReadMethodDescriptor());
        assertNull(pd.getWriteMethodDescriptor());
        assertNotNull(pd.getFieldDescriptor());
        assertTrue(pd.isFieldOnlyDescriptor());

        pd = properties[3];
        assertEquals("something", pd.getName());
        assertNotNull(pd.getReadMethodDescriptor());
        assertNull(pd.getWriteMethodDescriptor());
        assertNull(pd.getFieldDescriptor());
        assertFalse(pd.isFieldOnlyDescriptor());

        assertNotNull(cd.getPropertyDescriptor("fooProp", false));
        assertNotNull(cd.getPropertyDescriptor("something", false));
        assertNull(cd.getPropertyDescriptor("FooProp", false));
        assertNull(cd.getPropertyDescriptor("Something", false));
        assertNull(cd.getPropertyDescriptor("notExisting", false));

        assertNotNull(cd.getPropertyDescriptor("boo", true));
        assertNull(cd.getPropertyDescriptor("boo", false));
    }

    @Test
    public void testCtors() {
        ClassDescriptor cd = ClassIntrospector.lookup(Parent.class);
        ConstructorDescriptor[] ctors = cd.getAllConstructorDescriptors();
        int c = 0;
        for (ConstructorDescriptor ctor : ctors) {
            if (ctor.isPublic())
                c++;
        }
        assertEquals(1, c);
        ctors = cd.getAllConstructorDescriptors();
        assertEquals(2, ctors.length);
        assertNotNull(cd.getDefaultCtorDescriptor(true));
        assertNull(cd.getDefaultCtorDescriptor(false));

        Constructor<?> ctor = cd.getConstructorDescriptor(new Class[] { Integer.class }, true).getConstructor();
        assertNotNull(ctor);

        cd = ClassIntrospector.lookup(Child.class);
        ctors = cd.getAllConstructorDescriptors();
        c = 0;
        for (ConstructorDescriptor ccc : ctors) {
            if (ccc.isPublic())
                c++;
        }
        assertEquals(1, c);

        ctors = cd.getAllConstructorDescriptors();
        assertEquals(1, ctors.length);
        assertNull(cd.getDefaultCtorDescriptor(false));
        assertNull(cd.getDefaultCtorDescriptor(true));

        ConstructorDescriptor ctorDescriptor = cd.getConstructorDescriptor(new Class[] { Integer.class }, true);
        assertNull(ctorDescriptor);
        ctor = cd.getConstructorDescriptor(new Class[] { String.class }, true).getConstructor();
        assertNotNull(ctor);
    }

    @Test
    public void testSameFieldDifferentClass() {
        ClassDescriptor cd = ClassIntrospector.lookup(BeanSampleA.class);

        FieldDescriptor fd = cd.getFieldDescriptor("shared", false);
        assertNull(fd);

        fd = cd.getFieldDescriptor("shared", true);
        assertNotNull(fd);

        ClassDescriptor cd2 = ClassIntrospector.lookup(BeanSampleB.class);
        FieldDescriptor fd2 = cd2.getFieldDescriptor("shared", true);

        assertNotEquals(fd, fd2);
        assertEquals(fd.getField(), fd2.getField());
    }

    @Test
    public void testPropertyMatches() {
        ClassDescriptor cd = ClassIntrospector.lookup(BeanSampleC.class);

        PropertyDescriptor pd;

        pd = cd.getPropertyDescriptor("s1", false);
        assertNull(pd);

        pd = cd.getPropertyDescriptor("s1", true);
        assertFalse(pd.isPublic());
        assertTrue(pd.getReadMethodDescriptor().isPublic());
        assertFalse(pd.getWriteMethodDescriptor().isPublic());

        assertNotNull(getPropertyGetterDescriptor(cd, "s1", false));
        assertNull(getPropertySetterDescriptor(cd, "s1", false));

        pd = cd.getPropertyDescriptor("s2", false);
        assertNull(pd);

        pd = cd.getPropertyDescriptor("s2", true);
        assertFalse(pd.isPublic());
        assertFalse(pd.getReadMethodDescriptor().isPublic());
        assertTrue(pd.getWriteMethodDescriptor().isPublic());

        assertNull(getPropertyGetterDescriptor(cd, "s2", false));
        assertNotNull(getPropertySetterDescriptor(cd, "s2", false));

        pd = cd.getPropertyDescriptor("s3", false);
        assertNotNull(pd);

        pd = cd.getPropertyDescriptor("s3", true);
        assertTrue(pd.isPublic());
        assertTrue(pd.getReadMethodDescriptor().isPublic());
        assertTrue(pd.getWriteMethodDescriptor().isPublic());

        assertNotNull(getPropertyGetterDescriptor(cd, "s3", false));
        assertNotNull(getPropertySetterDescriptor(cd, "s3", false));
    }

    @Test
    public void testOverload() {
        ClassDescriptor cd = ClassIntrospector.lookup(Overload.class);

        PropertyDescriptor[] pds = cd.getAllPropertyDescriptors();

        assertEquals(1, pds.length);

        PropertyDescriptor pd = pds[0];

        assertNotNull(pd.getFieldDescriptor());
        assertNotNull(pd.getReadMethodDescriptor());
        assertNull(pd.getWriteMethodDescriptor());
    }

    @Test
    public void testSerialUid() {
        ClassDescriptor cd = ClassIntrospector.lookup(BeanSampleB.class);

        assertNull(cd.getFieldDescriptor("serialVersionUID", true));
    }

    MethodDescriptor getPropertySetterDescriptor(ClassDescriptor cd, String name, boolean declared) {
        PropertyDescriptor propertyDescriptor = cd.getPropertyDescriptor(name, true);

        if (propertyDescriptor != null) {
            MethodDescriptor setter = propertyDescriptor.getWriteMethodDescriptor();

            if ((setter != null) && setter.matchDeclared(declared)) {
                return setter;
            }
        }
        return null;
    }

    MethodDescriptor getPropertyGetterDescriptor(ClassDescriptor cd, String name, boolean declared) {
        PropertyDescriptor propertyDescriptor = cd.getPropertyDescriptor(name, true);

        if (propertyDescriptor != null) {
            MethodDescriptor getter = propertyDescriptor.getReadMethodDescriptor();

            if ((getter != null) && getter.matchDeclared(declared)) {
                return getter;
            }
        }
        return null;
    }

}
