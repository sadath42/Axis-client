package com.example.axis.client;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.client.Stub;

public class LogHandler extends GenericHandler {
    HandlerInfo hi;

    public void init(HandlerInfo info) {
	hi = info;
    }

    public QName[] getHeaders() {
	return hi.getHeaders();
    }

    @Override
    public boolean handleRequest(javax.xml.rpc.handler.MessageContext context) {

	System.out.println("request");

	try {

	    // get the soap header
	    SOAPMessageContext smc = (SOAPMessageContext) context;
	    SOAPMessage message = smc.getMessage();

	    File file = new File("Capture.JPG");

	    FileDataSource dataSource = new FileDataSource(file);
	    byte[] readAllBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	    byte[] xattachbytes = Base64.getEncoder().encode(readAllBytes);
	    ByteArrayDataSource arrayDataSource = new ByteArrayDataSource(xattachbytes, "multipart/related");
	    DataHandler xattach = new DataHandler(arrayDataSource);



	   
	    // Create transformer
	    TransformerFactory tff = TransformerFactory.newInstance();
	    Transformer tf = tff.newTransformer();

	    // Get reply content
	    Source sc = message.getSOAPPart().getContent();

	    // Set output transformation
	    StreamResult result = new StreamResult(System.out);
	    tf.transform(sc, result);
	    System.out.println();

	} catch (Exception e) {
	    throw new JAXRPCException(e);
	}
	return true;
    }

    @Override
    public boolean handleResponse(javax.xml.rpc.handler.MessageContext context) {

	System.out.println("response");

	try {

	    // get the soap header
	    SOAPMessageContext smc = (SOAPMessageContext) context;
	    SOAPMessage message = smc.getMessage();

	    // Create transformer
	    TransformerFactory tff = TransformerFactory.newInstance();
	    Transformer tf = tff.newTransformer();

	    // Get reply content
	    Source sc = message.getSOAPPart().getContent();

	    // Set output transformation
	    StreamResult result = new StreamResult(System.out);
	    tf.transform(sc, result);
	    System.out.println();

	} catch (Exception e) {
	    throw new JAXRPCException(e);
	}
	return true;
    }

    @Override
    public boolean handleFault(javax.xml.rpc.handler.MessageContext context) {

	System.out.println("falut");

	try {

	    // get the soap header
	    SOAPMessageContext smc = (SOAPMessageContext) context;
	    SOAPMessage message = smc.getMessage();

	    // Create transformer
	    TransformerFactory tff = TransformerFactory.newInstance();
	    Transformer tf = tff.newTransformer();

	    // Get reply content
	    Source sc = message.getSOAPPart().getContent();

	    // Set output transformation
	    StreamResult result = new StreamResult(System.out);
	    tf.transform(sc, result);
	    System.out.println();

	} catch (Exception e) {
	    throw new JAXRPCException(e);
	}
	return true;
    }
}
