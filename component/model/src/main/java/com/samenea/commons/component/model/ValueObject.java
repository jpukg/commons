package com.samenea.commons.component.model;

import java.io.Serializable;

/**
 * @author: jalal <a href="mailto:jalal.ashrafi@gmail.com">Jalal Ashrafi</a>
 * Date: 6/25/12
 * Time: 5:33 PM
 */
public abstract class ValueObject implements Serializable{
    public abstract boolean equals(Object other);
    public abstract int hashCode();
    public abstract String toString();
}
