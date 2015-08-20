package org.ateam.common.utils;

import junit.framework.Assert;
import org.ateam.common.model.Client;
import org.ateam.common.model.Resource;
import org.junit.Before;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by OPSKMC on 8/20/15.
 */

public class ClientSpringOAuthUtilsTest {
    @Before
    public void initData(){

    }
    @org.junit.Test
    public void testParseResources() throws Exception {
        String resources = "openmrs,demo_resource_server";
        Resource resource1 = new Resource("openmrs");
        Resource resource2 = new Resource("demo_resource_server");
        Collection<Resource> resourceCollection = new HashSet<Resource>();
        resourceCollection.add(resource1);
        resourceCollection.add(resource2);
        Set<String> parsedResource = ClientSpringOAuthUtils.parseResources(resourceCollection);
        Set<String> resourceSet = new HashSet<String>();
        resourceSet.add("openmrs");
        resourceSet.add("demo_resource_server");
        for(String val:resourceSet)
        {
            Assert.assertTrue(parsedResource.contains(val));
        }
    }

    @org.junit.Test
    public void testParseAuthorities() throws Exception {

    }

    @org.junit.Test
    public void testParseRedirectURIs() throws Exception {

    }

    @org.junit.Test
    public void testParseAuthorizedGrantTypes() throws Exception {

    }

    @org.junit.Test
    public void testParseScope() throws Exception {

    }

    @org.junit.Test
    public void testCommaDelimitedStringToCollection() throws Exception {
        int count=0;
        String resources = "openmrs,demo_resource_server";
        Resource resource1 = new Resource("openmrs");
        Resource resource2 = new Resource("demo_resource_server");
        Collection<Resource> resourceCollection = new HashSet<Resource>();
        resourceCollection.add(resource1);
        resourceCollection.add(resource2);
        Collection<Resource> parsedResource = ClientSpringOAuthUtils.commaDelimitedStringToCollection(resources,getSampleClient(),Resource.class);
        for(Resource res:resourceCollection){
            String resString = res.getResource();
            for(Resource res2:parsedResource){
                if(res2.getParameter().equals(resString)) {
                    count++;
                }
            }
        }
        Assert.assertEquals(count, resourceCollection.size());

    }
    private Client getSampleClient(){
        Client client = new Client("my-trusted-client-with-secret","somesecret","openmrs","read,write","password,authorization_code,implicit,client_credentials,refresh_token","ROLE_CLIENT","http://anywhere?key=value");
        client.setId(1);
        client.setName("Demo Client");
        return client;
    }
}
