package cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections;

import android.util.ArrayMap;
import android.util.SparseArray;

import org.apache.commons.collections4.list.CursorableLinkedList;
import org.apache.commons.collections4.list.NodeCachingLinkedList;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.StaticBucketMap;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections.CollectionList;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections.CollectionMap;
import cin.ufpe.br.energyprofiler.modes.selectors.implementations.collections.CollectionSet;
import jsr166e.ConcurrentHashMapV8;

/**
 * Created by welli on 09-Sep-17.
 */

public abstract class CollectionFactory {

    //
    //LISTS NOT THREAD-SAFE
    //
    public static CollectionList getArrayList(){
        return new CollectionList(new ArrayList<Integer>(Options.capacity));
    }
    public static CollectionList getLinkedList(){
        return new CollectionList(new LinkedList());
    }

    /**
     * @return The eclipse collections' FastList
     */
    public static CollectionList getFastList() {return new CollectionList(new FastList(Options.capacity));}

    /**
     * @return The apache commons collections' TreeList
     */
    public static CollectionList getTreeList() {return new CollectionList(new TreeList());}

    /**
     *
     * @return The apache commons collections' NodeCachingLinkedList
     */
    public static CollectionList getNodeCachingLinkedList() {return new CollectionList(new NodeCachingLinkedList(Options.capacity));}

    //
    //LISTS THREAD-SAFE
    //
    public static CollectionList getVector(){
        return new CollectionList(new Vector<>(Options.capacity));
    }
    public static CollectionList getSyncArrayList() {
        return new CollectionList(
                Collections.synchronizedList(
                        new ArrayList(Options.capacity)));
    }
    public static CollectionList getSyncList() {
        return new CollectionList(
                Collections.synchronizedList(
                        new LinkedList()));
    }
    public static CollectionList getCopyWriteArrayList(){
        return new CollectionList(new CopyOnWriteArrayList<>());
    }

    /**
     * @return The eclipse collections' FastList as synchronized
     */
    public static CollectionList getSyncFastList() {
        return new CollectionList(new FastList(Options.capacity).asSynchronized());
    }

    /**
     * @return The apache commons collections' CursorableLinkedList
     */
    public static CollectionList getCursorableLinkedList() {
        return new CollectionList(new CursorableLinkedList());
    }

    //
    //MAPS NOT THREAD-SAFE
    //
    public static CollectionMap getArrayMap() {
        return new CollectionMap(
                new ArrayMap(Options.capacity)
        );
    }
    public static CollectionMap getSparseArray() {
        return new CollectionMap(
                (Map) new SparseArray<String>()
        );
    }
    public static CollectionMap getHashMap() {
        return new CollectionMap(
                new HashMap<String,Integer>(
                        Options.capacity, Options.loadFactor));
    }
    public static CollectionMap getLinkedHashMap() {
        return new CollectionMap(
                new LinkedHashMap<String, Integer>(
                        Options.capacity, Options.loadFactor));
    }
    public static CollectionMap getTreeMap() {
        return new CollectionMap(new TreeMap<String, Integer>());
    }
    public static CollectionMap getWeakHashMap() {
        return new CollectionMap(
                new WeakHashMap<String, Integer>(
                        Options.capacity, Options.loadFactor));
    }

    /**
     * @return The eclipse collections' UnifiedMap
     */
    public static CollectionMap getUnifiedMap() {
        return new CollectionMap(new UnifiedMap(Options.capacity,Options.loadFactor));
    }

    /**
     * @return The apache commons collections' HashedMap
     */
    public static CollectionMap getHashedMap() {
        return new CollectionMap(new HashedMap(Options.capacity,Options.loadFactor));
    }

    //
    //MAPS THREAD-SAFE
    //
    public static CollectionMap getHashTable(){
        return new CollectionMap(
                new Hashtable<>(
                        Options.capacity, Options.loadFactor));
    }
    public static CollectionMap getConcHashMap(){
        return new CollectionMap(
                new ConcurrentHashMap(
                        Options.capacity, Options.loadFactor));
    }
    public static CollectionMap getConcSkipListMap(){
        return new CollectionMap(
                new ConcurrentSkipListMap<String, Integer>());
    }
    public static CollectionMap getSyncHashMap(){
        return new CollectionMap(
                Collections.synchronizedMap(
                        new HashMap<String,Integer>(
                                Options.capacity, Options.loadFactor)));
    }
    public static CollectionMap getSyncLinkedHashMap() {
        return new CollectionMap(
                Collections.synchronizedMap(
                        new LinkedHashMap<String, Integer>(
                                Options.capacity, Options.loadFactor)));

    }
    public static CollectionMap getSyncTreeMap() {
        return new CollectionMap(
                Collections.synchronizedSortedMap(
                        new TreeMap<String, Integer>()));
    }
    public static CollectionMap getSyncWeakHashMap() {
        return new CollectionMap(
                Collections.synchronizedMap(
                        new WeakHashMap<String, Integer>(Options.capacity, Options.loadFactor)));

    }

    /**
     * @return The jsr166e ConcurrentHashMap
     */
    public static CollectionMap getConcHashMapV8(){
        return new CollectionMap(new ConcurrentHashMapV8(Options.capacity,Options.loadFactor));
    }
    /**
     * @return The eclipse collections' ConcurrentHashMap
     */
    public static CollectionMap getConcHashMapEC(){
        return new CollectionMap(new org.eclipse.collections.impl.map.mutable.ConcurrentHashMap());
    }
    /**
     * @return The eclipse collections' UnifiedMap as synchronized
     */
    public static CollectionMap getSyncUnifiedMap(){
        return new CollectionMap(new UnifiedMap(Options.capacity,Options.loadFactor).asSynchronized());
    }
    /**
     * @return The apache commons collections' StaticBucketMap
     */
    public static CollectionMap getStaticBucketMap(){
        return new CollectionMap(new StaticBucketMap(Options.capacity));
    }

    //
    //SETS NOT THREAD-SAFE
    //
    public static CollectionSet getHashSet() {
        return new CollectionSet(
                new HashSet<String>(
                        Options.capacity,Options.loadFactor));

    }
    public static CollectionSet getLinkedHashSet() {
        return new CollectionSet(
                new LinkedHashSet<String>(
                        Options.capacity,Options.loadFactor));

    }
    public static CollectionSet getTreeSet() {
        return new CollectionSet(new TreeSet<String>());
    }

    /**
     * @return The eclipse collections' UnifiedSet
     */
    public static CollectionSet getUnifiedSet() {
        return new CollectionSet(new UnifiedSet<String>());
    }
    /**
     * @return The eclipse collections' TreeSortedSet
     */
    public static CollectionSet getTreeSortedSet() {
        return new CollectionSet(new TreeSortedSet<String>());
    }


    //
    //SETS THREAD-SAFE

    //
    public static CollectionSet getConcSkipListSet(){
        return new CollectionSet(
                new ConcurrentSkipListSet());
    }
    public static CollectionSet getCopyWriteArraySet(){
        return new CollectionSet(
                new CopyOnWriteArraySet<>());
    }
    public static CollectionSet getSyncHashSet(){
        return new CollectionSet(
                Collections.synchronizedSet(
                        new HashSet<String>(
                                Options.capacity, Options.loadFactor)));
    }
    public static CollectionSet getSyncLinkedHashSet(){
        return new CollectionSet(
                Collections.synchronizedSet(
                        new LinkedHashSet<>(
                                Options.capacity, Options.loadFactor)));
    }
    public static CollectionSet getSyncTreeSet() {
        return new CollectionSet(
                Collections.synchronizedSortedSet(
                        new TreeSet<String>()));

    }
    public static CollectionSet getSetConcHashMap() {
        return new CollectionSet(
                Collections.newSetFromMap(
                        new ConcurrentHashMap<String, Boolean>(
                                Options.capacity, Options.loadFactor)));
    }

    /**
     * @return A set where the underlying map is the jsr166e ConcurrentHashMapV8
     */
    public static CollectionSet getSetConcHashMapV8() {
        return new CollectionSet(
                Collections.newSetFromMap(
                        new ConcurrentHashMapV8<String, Boolean>(
                                Options.capacity, Options.loadFactor)));
    }

    /**
     * @return The eclipse collections' UnifiedSet as synchronized
     */
    public static CollectionSet getSyncUnifiedSet(){
        return new CollectionSet(new UnifiedSet<String>().asSynchronized());
    }
    /**
     * @return The eclipse collections' TreeSortedSet as synchronized
     */
    public static CollectionSet getSyncTreeSortedSet(){
        return new CollectionSet(new TreeSortedSet<String>().asSynchronized());
    }
}
