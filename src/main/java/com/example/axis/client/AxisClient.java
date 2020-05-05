package com.example.axis.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.axis.attachments.AttachmentPart;
import org.apache.axis.client.Stub;

public class AxisClient {

    public static void main(String[] args) throws ServiceException, IOException {
	IWSLim5APserviceLocator iwsLim5APservice = new IWSLim5APserviceLocator();
	IWSLim5APbindingStub iwsLim5APPort = (IWSLim5APbindingStub) iwsLim5APservice.getIWSLim5APPort();
	/*
	 * EngineConfiguration clientConfig = new SimpleProvider(); LogHandler
	 * logHandler = new LogHandler(); SimpleChain reqHandler = new
	 * SimpleChain(); SimpleChain respHandler = new SimpleChain();
	 * reqHandler.addHandler(logHandler);
	 * respHandler.addHandler(logHandler); HTTPSender pivot = new
	 * HTTPSender(); SimpleTargetedChain transport = new
	 * SimpleTargetedChain(reqHandler, pivot, respHandler);
	 * ((SimpleProvider)
	 * clientConfig).deployTransport(HTTPTransport.DEFAULT_TRANSPORT_NAME,
	 * transport); iwsLim5APservice.setEngineConfiguration(clientConfig);
	 */

	HandlerRegistry hr = iwsLim5APservice.getHandlerRegistry();
	List<HandlerInfo> handlerChain = hr.getHandlerChain((QName) iwsLim5APservice.getPorts().next());
	HandlerInfo hi = new HandlerInfo();
	hi.setHandlerClass(LogHandler.class);
	handlerChain.add(hi);
	System.out.println(iwsLim5APPort.textrec("test"));

	String xctxt = "test";
	File file = new File("Capture.JPG");
	FileDataSource dataSource = new FileDataSource(file);
	byte[] readAllBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	byte[] xattachbytes = Base64.getEncoder().encode(readAllBytes);
	ByteArrayDataSource arrayDataSource = new ByteArrayDataSource(readAllBytes, "application/binary");
	DataHandler xattach = new DataHandler(arrayDataSource);
	FileOutputStream os = new FileOutputStream(file);

	Stub stub = (org.apache.axis.client.Stub) iwsLim5APPort;

	((org.apache.axis.client.Stub) stub)._setProperty(org.apache.axis.client.Call.ATTACHMENT_ENCAPSULATION_FORMAT,
		org.apache.axis.client.Call.ATTACHMENT_ENCAPSULATION_FORMAT_MIME);

	AttachmentPart attachmentPart = new AttachmentPart(xattach);
	stub.addAttachment(attachmentPart);

	String imGsd = iwsLim5APPort.IMGsd(xctxt, xattach);
	System.out.println(imGsd);
    }

}
