/*
 * Copyright 2008,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.hamcrest.reflection;

import java.util.Map;

import org.jtester.hamcrest.reflection.difference.ClassDifference;
import org.jtester.hamcrest.reflection.difference.CollectionDifference;
import org.jtester.hamcrest.reflection.difference.Difference;
import org.jtester.hamcrest.reflection.difference.DifferenceVisitor;
import org.jtester.hamcrest.reflection.difference.MapDifference;
import org.jtester.hamcrest.reflection.difference.ObjectDifference;
import org.jtester.hamcrest.reflection.difference.UnorderedCollectionDifference;
import org.jtester.module.utils.ObjectFormatter;

/**
 * A utility class to get the difference at the given element/field/key.
 *
 * @author Tim Ducheyne
 * @author Filip Neven
 */
public class InnerDifferenceFinder {


    /**
     * Gets the difference at the given element/field/key (depending on the type of the given difference)
     *
     * @param fieldName  The name of the element/field/key
     * @param difference The difference, not null
     * @return The difference, null if there is no difference
     */
    public static Difference getInnerDifference(String fieldName, Difference difference) {
        if (difference == null) {
            return null;
        }
        return difference.accept(new InnerDifferenceVisitor(), fieldName);
    }


    /**
     * The visitor for visiting the difference tree.
     */
    protected static class InnerDifferenceVisitor implements DifferenceVisitor<Difference, String> {

        /**
         * Formatter for object values.
         */
        protected ObjectFormatter objectFormatter = new ObjectFormatter();


        /**
         * Returns null, there are no inner differences for a simple difference.
         *
         * @param difference The difference, not null
         * @param key        The key
         * @return null
         */
        public Difference visit(Difference difference, String key) {
            return null;
        }


        /**
         * Returns the difference at the field with the given name.
         *
         * @param objectDifference The difference, not null
         * @param fieldName        The field name, not null
         * @return The difference, null if there is no difference
         */
        public Difference visit(ObjectDifference objectDifference, String fieldName) {
            return objectDifference.getFieldDifferences().get(fieldName);
        }

        
        public Difference visit(ClassDifference classDifference, String argument) {
            return null;
        }


        /**
         * Returns the difference at the given key. The string represenation (using the object formatter) of the keys
         * in the map are compared with the given key string.
         *
         * @param mapDifference The difference, not null
         * @param keyString     The key as a string, not null
         * @return The difference, null if there is no difference
         */
        public Difference visit(MapDifference mapDifference, String keyString) {
            for (Map.Entry<Object, Difference> entry : mapDifference.getValueDifferences().entrySet()) {
                if (objectFormatter.format(entry.getKey()).equals(keyString)) {
                    return entry.getValue();
                }
            }
            return null;
        }


        /**
         * Returns the difference at the field with the given index.
         *
         * @param collectionDifference The difference, not null
         * @param indexString          The index number as a string, not null
         * @return The difference, null if there is no difference
         */
        public Difference visit(CollectionDifference collectionDifference, String indexString) {
            return collectionDifference.getElementDifferences().get(new Integer(indexString));
        }


        /**
         * Returns the best matching difference at the field with the given index.
         *
         * @param unorderedCollectionDifference The difference, not null
         * @param indexString                   The index number as a string, not null
         * @return The difference, null if there is no difference
         */
        public Difference visit(UnorderedCollectionDifference unorderedCollectionDifference, String indexString) {
            int leftIndex = new Integer(indexString);
            int rightIndex = unorderedCollectionDifference.getBestMatchingIndexes().get(leftIndex);
            return unorderedCollectionDifference.getElementDifference(leftIndex, rightIndex);
        }
    }


}