package com.samenea.commons.tracking.model;

import junit.framework.Assert;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/14/13
 * Time: 10:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class SynonymTest {

    @Test
    public void should_make_two_distinct_words_synonym(){
        Synonym synonym = new Synonym("id1", "id2");

        Assert.assertEquals(2, synonym.getSynonymElements().size());
        Assert.assertTrue(synonym.getSynonymElements().contains("id1"));
        Assert.assertTrue(synonym.getSynonymElements().contains("id2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_synonym_with_empty_word(){
        new Synonym("", "id2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_create_synonym_with_null_word(){
        new Synonym("id1", null);
    }

    @Test
    public void should_add_new_word_to_synonym_elements(){
        Synonym synonym = new Synonym("id1", "id2");
        synonym.addSynonymElement("id3");


        Assert.assertEquals(3,synonym.getSynonymElements().size());
        Assert.assertTrue(synonym.getSynonymElements().contains("id1"));
        Assert.assertTrue(synonym.getSynonymElements().contains("id2"));
        Assert.assertTrue(synonym.getSynonymElements().contains("id3"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_add_duplicate_word_to_synonym_elements(){
        Synonym synonym = new Synonym("id1", "id2");
        synonym.addSynonymElement("id1");

    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_add_empty_word_to_synonym_elements(){
        Synonym synonym = new Synonym("id1","id2");
        synonym.addSynonymElement("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_not_add_null_word_to_synonym_elements(){
        Synonym synonym = new Synonym("id1","id2");
        synonym.addSynonymElement(null);
    }

//    @Test
//    public void just_test(){
//        Set<String> ids = new HashSet<String>();
//        ids.add("id1");
//        ids.add("id2");
//        Iterator it = ids.iterator();
//      //  String s1 = new String(it.next().toString());
//       // String s2 = new String(it.next().toString());
//        System.out.println(it.next().toString());
//        System.out.println(it.next().toString());
//
//      //  String tos = ids.toString();
//        StringUtils.join(ids,",");
//    }


}
