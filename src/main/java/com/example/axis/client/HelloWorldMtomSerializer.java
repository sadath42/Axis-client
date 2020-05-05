package com.example.axis.client;

import java.io.IOException;

import javax.xml.namespace.QName;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.JAFDataHandlerSerializer;
import org.apache.axis.wsdl.fromJava.Types;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

public class HelloWorldMtomSerializer implements Serializer {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String getMechanismType() {
	// TODO Auto-generated method stub
	return Constants.AXIS_SAX;
    }

    public void serialize(QName name, Attributes attributes, Object value, SerializationContext context)
	    throws IOException {
	if (value != null) {
	    HelloWorldMtom data = (HelloWorldMtom) value;
	    context.startElement(new QName("arg0"), null);
	    context.serialize(new QName("xctxt"), null, data.getXctxt(), null, Boolean.TRUE, Boolean.FALSE);
	    if (data.getXattach() != null) {
		Serializer jafs = new JAFDataHandlerSerializer();
		//should convert the data into string
		jafs.serialize(new QName("xattach"), null, data.getXattach(), context);
	    } else {
		context.serialize(new QName("xattach"), null, null, null, Boolean.TRUE, Boolean.FALSE);
	    }
	    context.endElement();
	}
    }

    public Element writeSchema(Class javaType, Types types) throws Exception {
	return null;
    }

}
