package com.ouer.solar.convert;
///**
// * 
// */
//
//import static com.qf.common.convert.ConverterTestHelper.arri;
//import static com.qf.common.convert.ConverterTestHelper.arrl;
//import static com.qf.common.convert.ConverterTestHelper.arro;
//import static com.qf.common.convert.ConverterTestHelper.iterableo;
//import static com.qf.common.convert.ConverterTestHelper.listo;
//import static com.qf.common.convert.ConverterTestHelper.seto;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Test;
//
///**
// * FIXME
// * 
// * @author <a href="indra@ixiaopu.com">chenxi</a>
// * @version create on 2014年9月22日 下午2:50:25
// */
//public class MixTest {
//
//	@Test
//	public void testStringArrayMix() {
//		String[] strings;
//
//		strings = ConverterManager.convertType(new Class[] { Long.class,
//				int.class }, String[].class);
//		assertEquals("java.lang.Long", strings[0]);
//		assertEquals("int", strings[1]);
//
//		strings = ConverterManager.convertType("one,two", String[].class);
//		assertEquals("one", strings[0]);
//		assertEquals("two", strings[1]);
//
//		strings = ConverterManager.convertType(arri(1, 23), String[].class);
//		assertEquals("1", strings[0]);
//		assertEquals("23", strings[1]);
//
//		strings = ConverterManager.convertType(
//				arro(Integer.valueOf(173), "Foo"), String[].class);
//		assertEquals("173", strings[0]);
//		assertEquals("Foo", strings[1]);
//
//		strings = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "234"), String[].class);
//		assertEquals("123", strings[0]);
//		assertEquals("234", strings[1]);
//
//		strings = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "234"), String[].class);
//		assertEquals("123", strings[0]);
//		assertEquals("234", strings[1]);
//
//		strings = ConverterManager
//				.convertType(iterableo((Object) Integer.valueOf(123), "234"),
//						String[].class);
//		assertEquals("123", strings[0]);
//		assertEquals("234", strings[1]);
//	}
//
//	@Test
//	public void testIntArrayMix() {
//		int[] ints;
//
//		ints = ConverterManager.convertType("123, 234", int[].class);
//		assertEquals(123, ints[0]);
//		assertEquals(234, ints[1]);
//
//		ints = ConverterManager.convertType(arri(1, 23), int[].class);
//		assertEquals(1, ints[0]);
//		assertEquals(23, ints[1]);
//
//		ints = ConverterManager.convertType(
//				arro(Integer.valueOf(173), Float.valueOf(2.5f)), int[].class);
//		assertEquals(173, ints[0]);
//		assertEquals(2, ints[1]);
//
//		ints = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "234"), int[].class);
//		assertEquals(123, ints[0]);
//		assertEquals(234, ints[1]);
//
//		ints = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "234"), int[].class);
//		assertEquals(123, ints[0]);
//		assertEquals(234, ints[1]);
//
//		ints = ConverterManager.convertType(
//				iterableo((Object) Integer.valueOf(123), "234"), int[].class);
//		assertEquals(123, ints[0]);
//		assertEquals(234, ints[1]);
//	}
//
//	@Test
//	public void testLongArrayMix() {
//		long[] longs;
//
//		longs = ConverterManager.convertType("123, 234", long[].class);
//		assertEquals(123, longs[0]);
//		assertEquals(234, longs[1]);
//
//		longs = ConverterManager.convertType(arri(1, 23), long[].class);
//		assertEquals(1, longs[0]);
//		assertEquals(23, longs[1]);
//
//		longs = ConverterManager.convertType(
//				arro(Integer.valueOf(173), Float.valueOf(2.5f)), long[].class);
//		assertEquals(173, longs[0]);
//		assertEquals(2, longs[1]);
//
//		longs = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "234"), long[].class);
//		assertEquals(123, longs[0]);
//		assertEquals(234, longs[1]);
//
//		longs = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "234"), long[].class);
//		assertEquals(123, longs[0]);
//		assertEquals(234, longs[1]);
//
//		longs = ConverterManager.convertType(
//				iterableo((Object) Integer.valueOf(123), "234"), long[].class);
//		assertEquals(123, longs[0]);
//		assertEquals(234, longs[1]);
//	}
//
//	@Test
//	public void testFloatArrayMix() {
//		float[] floats;
//
//		floats = ConverterManager.convertType("123.1, 234.2", float[].class);
//		assertEquals(123.1, floats[0], 0.1);
//		assertEquals(234.2, floats[1], 0.1);
//
//		floats = ConverterManager.convertType(arri(1, 23), float[].class);
//		assertEquals(1, floats[0], 0.1);
//		assertEquals(23, floats[1], 0.1);
//
//		floats = ConverterManager.convertType(
//				arro(Integer.valueOf(173), Float.valueOf(2.5f)), float[].class);
//		assertEquals(173, floats[0], 0.1);
//		assertEquals(2.5, floats[1], 0.1);
//
//		floats = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "234"), float[].class);
//		assertEquals(123, floats[0], 0.1);
//		assertEquals(234, floats[1], 0.1);
//
//		floats = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "234"), float[].class);
//		assertEquals(123, floats[0], 0.1);
//		assertEquals(234, floats[1], 0.1);
//
//		floats = ConverterManager.convertType(
//				iterableo((Object) Integer.valueOf(123), "234"), float[].class);
//		assertEquals(123, floats[0], 0.1);
//		assertEquals(234, floats[1], 0.1);
//	}
//
//	@Test
//	public void testDoubleArrayMix() {
//		double[] doubles;
//
//		doubles = ConverterManager.convertType("123.1, 234.2", double[].class);
//		assertEquals(123.1, doubles[0], 0.1);
//		assertEquals(234.2, doubles[1], 0.1);
//
//		doubles = ConverterManager.convertType(arri(1, 23), double[].class);
//		assertEquals(1, doubles[0], 0.1);
//		assertEquals(23, doubles[1], 0.1);
//
//		doubles = ConverterManager
//				.convertType(arro(Integer.valueOf(173), Float.valueOf(2.5f)),
//						double[].class);
//		assertEquals(173, doubles[0], 0.1);
//		assertEquals(2.5, doubles[1], 0.1);
//
//		doubles = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "234"), double[].class);
//		assertEquals(123, doubles[0], 0.1);
//		assertEquals(234, doubles[1], 0.1);
//
//		doubles = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "234"), double[].class);
//		assertEquals(123, doubles[0], 0.1);
//		assertEquals(234, doubles[1], 0.1);
//
//		doubles = ConverterManager
//				.convertType(iterableo((Object) Integer.valueOf(123), "234"),
//						double[].class);
//		assertEquals(123, doubles[0], 0.1);
//		assertEquals(234, doubles[1], 0.1);
//	}
//
//	@Test
//	public void testByteArrayMix() {
//		byte[] bytes;
//
//		bytes = ConverterManager.convertType("123, -12", byte[].class);
//		assertEquals(123, bytes[0]);
//		assertEquals(-12, bytes[1]);
//
//		bytes = ConverterManager.convertType(arri(1, 23), byte[].class);
//		assertEquals(1, bytes[0]);
//		assertEquals(23, bytes[1]);
//
//		bytes = ConverterManager.convertType(
//				arro(Integer.valueOf(127), Float.valueOf(2.5f)), byte[].class);
//		assertEquals(127, bytes[0]);
//		assertEquals(2, bytes[1]);
//
//		bytes = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "-12"), byte[].class);
//		assertEquals(123, bytes[0]);
//		assertEquals(-12, bytes[1]);
//
//		bytes = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "-12"), byte[].class);
//		assertEquals(123, bytes[0]);
//		assertEquals(-12, bytes[1]);
//
//		bytes = ConverterManager.convertType(
//				iterableo((Object) Integer.valueOf(123), "-12"), byte[].class);
//		assertEquals(123, bytes[0]);
//		assertEquals(-12, bytes[1]);
//	}
//
//	@Test
//	public void testShortArrayMix() {
//		short[] shorts;
//
//		shorts = ConverterManager.convertType("123, -12", short[].class);
//		assertEquals(123, shorts[0]);
//		assertEquals(-12, shorts[1]);
//
//		shorts = ConverterManager.convertType(arri(1, 23), short[].class);
//		assertEquals(1, shorts[0]);
//		assertEquals(23, shorts[1]);
//
//		shorts = ConverterManager.convertType(
//				arro(Integer.valueOf(127), Float.valueOf(2.5f)), short[].class);
//		assertEquals(127, shorts[0]);
//		assertEquals(2, shorts[1]);
//
//		shorts = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "-12"), short[].class);
//		assertEquals(123, shorts[0]);
//		assertEquals(-12, shorts[1]);
//
//		shorts = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "-12"), short[].class);
//		assertEquals(123, shorts[0]);
//		assertEquals(-12, shorts[1]);
//
//		shorts = ConverterManager.convertType(
//				iterableo((Object) Integer.valueOf(123), "-12"), short[].class);
//		assertEquals(123, shorts[0]);
//		assertEquals(-12, shorts[1]);
//	}
//
//	@Test
//	public void testCharArrayMix() {
//		char[] chars;
//
//		chars = ConverterManager.convertType("123, -12", char[].class);
//		assertEquals("123, -12", new String(chars));
//
//		chars = ConverterManager.convertType(arri(1, 23), char[].class);
//		assertEquals(1, chars[0]);
//		assertEquals(23, chars[1]);
//
//		chars = ConverterManager.convertType(
//				arro(Integer.valueOf(127), Float.valueOf(2.5f)), char[].class);
//		assertEquals(127, chars[0]);
//		assertEquals(2, chars[1]);
//
//		chars = ConverterManager.convertType(
//				listo((Object) Integer.valueOf(123), "-12"), char[].class);
//		assertEquals(123, chars[0]);
//		assertEquals(65524, chars[1]);
//
//		chars = ConverterManager.convertType(
//				seto((Object) Integer.valueOf(123), "-12"), char[].class);
//		assertEquals(123, chars[0]);
//		assertEquals(65524, chars[1]);
//
//		chars = ConverterManager.convertType(
//				iterableo((Object) Integer.valueOf(123), "-12"), char[].class);
//		assertEquals(123, chars[0]);
//		assertEquals(65524, chars[1]);
//	}
//
//	@Test
//	public void testCollections() {
//		List<?> list1 = ConverterManager.convertType(arri(1, 2, 3), List.class);
//		assertEquals(listo(1, 2, 3), list1);
//
//		list1 = ConverterManager.convertType("1,2,3", List.class);
//		assertEquals(listo("1", "2", "3"), list1);
//
//		Set<?> set1 = ConverterManager.convertType(arrl(1, 2, 3),
//				LinkedHashSet.class);
//		assertTrue(set1 instanceof LinkedHashSet);
//		Iterator<?> i = set1.iterator();
//		assertEquals(Long.valueOf(1), i.next());
//		assertEquals(Long.valueOf(2), i.next());
//		assertEquals(Long.valueOf(3), i.next());
//
//		list1 = ConverterManager.convertType("hello", List.class);
//		assertEquals(listo("hello"), list1);
//
//		list1 = ConverterManager.convertType(Long.valueOf(4), List.class);
//		assertEquals(listo(Long.valueOf(4)), list1);
//	}
//
//	@Test
//	public void testListToArray() {
//		List<Long> list = new ArrayList<Long>();
//		list.add(1L);
//		list.add(9L);
//		list.add(2L);
//
//		Long[] array = ConverterManager.convertType(list, Long[].class);
//
//		assertEquals(3, array.length);
//		assertEquals(1, array[0].longValue());
//		assertEquals(9, array[1].longValue());
//		assertEquals(2, array[2].longValue());
//	}
//
// }