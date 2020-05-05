package com.example.axis.client;

import java.util.Iterator;
import java.util.Vector;

import javax.xml.rpc.encoding.Serializer;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializerFactory;

public class HelloWorldMtomSerializerFactory implements SerializerFactory {
    private Vector mechanisms;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Serializer getSerializerAs(String arg0) {
	return new HelloWorldMtomSerializer();
    }

    public Iterator getSupportedMechanismTypes() {
	if (mechanisms == null) {
	    mechanisms = new Vector();
	    mechanisms.add(Constants.AXIS_SAX);
	}
	return mechanisms.iterator();
    }

}
