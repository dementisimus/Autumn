package dev.dementisimus.autumn.common.api.map;

import java.util.Objects;

/*******************************************************************************
 * Copyright (c) 2017 Pablo Pavon Marino and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the 2-clause BSD License
 * which accompanies this distribution, and is available at
 * https://opensource.org/licenses/BSD-2-Clause
 *
 * Contributors:
 *     Pablo Pavon Marino and others - initial API and implementation
 *******************************************************************************/
public class QuadrupleMap<A, B, C, D> {

    private final boolean isModifiable;
    private A a;
    private B b;
    private C c;
    private D d;

    /**
     * Default constructor.
     *
     * @param a The first element, may be {@code null}
     * @param b The second element, may be {@code null}
     * @param c The third element, may be {@code null}
     * @param d The fourth element, may be {@code null}
     * @param isModifiable Indicates whether or not the elements can be changed after initialization
     */
    public QuadrupleMap(A a, B b, C c, D d, boolean isModifiable) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.isModifiable = isModifiable;
    }

    /**
     * Returns a hash code value for the object. This method is supported for the benefit of hash tables such as those provided by {@code HashMap}.
     *
     * @return Hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.getFirst());
        hash = 97 * hash + Objects.hashCode(this.getSecond());
        hash = 97 * hash + Objects.hashCode(this.getThird());
        hash = 97 * hash + Objects.hashCode(this.getFourth());
        return hash;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o Reference object with which to compare
     *
     * @return {@code true} if this object is the same as the {@code o} argument; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(o == this) {
            return true;
        }
        if(!(o instanceof QuadrupleMap)) {
            return false;
        }

        QuadrupleMap p = (QuadrupleMap) o;
        if(this.a != null) {
            if(p.getFirst() == null) return false;
            if(!this.a.equals(p.getFirst())) return false;
        }else {if(p.getFirst() != null) return false;}
        if(this.b != null) {
            if(p.getSecond() == null) return false;
            if(!this.b.equals(p.getSecond())) return false;
        }else {if(p.getSecond() != null) return false;}
        if(this.c != null) {
            if(p.getThird() == null) return false;
            if(!this.c.equals(p.getThird())) return false;
        }else {if(p.getThird() != null) return false;}
        if(this.d != null) {
            if(p.getFourth() == null) return false;
            return this.d.equals(p.getFourth());
        }else {return p.getFourth() == null;}
    }

    /**
     * Returns a {@code String} representation of this QuadrupleMap using the format (first, second, third, fourth).
     *
     * @return A {@code String} representation of this QuadrupleMap
     */
    @Override
    public String toString() {
        return "(" + this.a + ", " + this.b + ", " + this.c + ", " + this.d + ")";
    }

    /**
     * Returns the first element from this QuadrupleMap.
     *
     * @return The first element from this QuadrupleMap
     */
    public A getFirst() {
        return this.a;
    }

    /**
     * Sets the first element from this QuadrupleMap.
     *
     * @param a The first element, may be {@code null}
     */
    public void setFirst(A a) {
        this.checkIsModifiable();
        this.a = a;
    }

    /**
     * Returns the fourth element from this QuadrupleMap.
     *
     * @return The fourth element from this QuadrupleMap
     */
    public D getFourth() {
        return this.d;
    }

    /**
     * Sets the fourth element from this QuadrupleMap.
     *
     * @param d The fourth element, may be {@code null}
     */
    public void setFourth(D d) {
        this.checkIsModifiable();
        this.d = d;
    }

    /**
     * Returns the second element from this QuadrupleMap.
     *
     * @return The second element from this QuadrupleMap
     */
    public B getSecond() {
        return this.b;
    }

    /**
     * Sets the second element from this QuadrupleMap.
     *
     * @param b The second element, may be {@code null}
     */
    public void setSecond(B b) {
        this.checkIsModifiable();
        this.b = b;
    }

    /**
     * Returns the third element from this QuadrupleMap.
     *
     * @return The third element from this QuadrupleMap
     */
    public C getThird() {
        return this.c;
    }

    /**
     * Sets the third element from this QuadrupleMap.
     *
     * @param c The third element, may be {@code null}
     */
    public void setThird(C c) {
        this.checkIsModifiable();
        this.c = c;
    }

    /**
     * Indicates whether or not elements from the pair can be changed after initialization.
     *
     * @return {@code true} if the element can be changed. Otherwise, {@code false}
     */
    public boolean isModifiable() {
        return this.isModifiable;
    }

    private void checkIsModifiable() {

    }

    /**
     * This factory allows a QuadrupleMap to be created using inference to obtain the generic types.
     *
     * @param <A> Class type for the first element
     * @param <B> Class type for the second element
     * @param <C> Class type for the third element
     * @param <D> Class type for the fourth element
     * @param a The first element, may be {@code null}
     * @param b The second element, may be {@code null}
     * @param c The third element, may be {@code null}
     * @param d The fourth element, may be {@code null}
     *
     * @return A QuadrupleMap formed from four parameters
     */
    public static <A, B, C, D> QuadrupleMap<A, B, C, D> of(A a, B b, C c, D d) {
        return new QuadrupleMap<A, B, C, D>(a, b, c, d, true);
    }

    /**
     * This factory allows an unmodifiable pair to be created using inference to obtain the generic types.
     *
     * @param <A> Class type for the first element
     * @param <B> Class type for the second element
     * @param <C> Class type for the third element
     * @param <D> Class type for the fourth element
     * @param a The first element, may be {@code null}
     * @param b The second element, may be {@code null}
     * @param c The third element, may be {@code null}
     * @param d The fourth element, may be {@code null}
     *
     * @return An unmodifiable QuadrupleMap formed from two parameters
     */
    public static <A, B, C, D> QuadrupleMap<A, B, C, D> unmodifiableOf(A a, B b, C c, D d) {
        return new QuadrupleMap<A, B, C, D>(a, b, c, d, false);
    }
}
