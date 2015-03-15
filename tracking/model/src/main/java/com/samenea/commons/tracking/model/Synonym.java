package com.samenea.commons.tracking.model;

import com.samenea.commons.component.model.Entity;
import org.springframework.util.Assert;

import javax.persistence.Access;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/14/13
 * Time: 10:02 AM
 * To change this template use File | Settings | File Templates.
 */

@javax.persistence.Entity(name="XSYNONYM")
public class Synonym extends Entity<Long> {


    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Synonym_elements")
    private final Set<String> synonymElements;

    private Synonym() {
        this.synonymElements = null;
    }

    public Set<String> getSynonymElements() {
        return synonymElements;
    }

    public Synonym(String element1, String element2){
        Assert.notNull(element1, "synonym elements can not be null!");
        Assert.notNull(element2, "synonym elements can not be null!");
        Assert.hasText(element1, "synonym elements can not be empty!");
        Assert.hasText(element2, "synonym elements can not be empty!");

        synonymElements = new HashSet<String>();
        synonymElements.add(element1);
        synonymElements.add(element2);
    }


    public void addSynonymElement(String newElement) {
        Assert.notNull(newElement, "synonym elements can not be null!");
        Assert.hasText(newElement, "synonym elements can not be empty!");

        Assert.isTrue(!synonymElements.contains(newElement), "this elements already exist in sysnonym!");

        synonymElements.add(newElement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Synonym synonym = (Synonym) o;

        if (synonymElements != null ? !synonymElements.equals(synonym.synonymElements) : synonym.synonymElements != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return synonymElements != null ? synonymElements.hashCode() : 0;
    }
}
