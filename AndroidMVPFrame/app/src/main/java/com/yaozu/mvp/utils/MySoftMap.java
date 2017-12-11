package com.yaozu.mvp.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * @author Wei 软引用的集合
 * @param <K>
 * @param <V>
 */
public class MySoftMap<K, V> extends HashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 创建一个存袋子的集合
	private HashMap<K, SoftValue<K, V>> temp;
	// 袋子，该袋子只能装手机
	private ReferenceQueue<V> queue;

	public MySoftMap() {
		temp = new HashMap<K, SoftValue<K, V>>();
		queue = new ReferenceQueue<V>();
	}

	@Override
	public V put(K key, V value) {
		SoftValue<K, V> sr = new SoftValue<K, V>(key, value, queue);
		temp.put(key, sr);
		return null;
	}

	@Override
	public V get(Object key) {
		clearSoftReference();
		SoftValue<K, V> sr = temp.get(key);
		if (sr != null) {
			return sr.get();
		}
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		clearSoftReference();
		SoftValue<K, V> sr = temp.get(key);
		if (sr != null) {
			return sr.get() != null;
		}
		return false;
	}

	/**
	 * 清理掉空袋子
	 */
	@SuppressWarnings("unchecked")
	public void clearSoftReference() {
		SoftValue<K, V> sr = (SoftValue<K, V>) queue.poll();
		while (sr != null) {
			temp.remove(sr.key);
			sr = (SoftValue<K, V>) queue.poll();
		}
	}

	/**
	 * 自定义的袋子（多了key，删除袋子的时候方便）
	 * 
	 * @param <K>
	 * @param <V>
	 */
	@SuppressWarnings("hiding")
	private class SoftValue<K, V> extends SoftReference<V> {
		Object key;

		public SoftValue(K key, V r, ReferenceQueue<? super V> q) {
			super(r, q);
			this.key = key;
		}
	}
}
