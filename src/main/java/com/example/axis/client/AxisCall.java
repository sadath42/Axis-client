package com.example.axis.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.security.Provider.Service;
import java.util.Base64;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.axis.Handler;
import org.apache.axis.client.Call;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.soap.SOAPConstants;

public class AxisCall {

    private static final String ADDRESS = "http://s11gs3s.doomdns.com:9091/WSDL/soap/IWSLim5AP";

    public static void main(String[] args) throws ServiceException, IOException {

	File file = new File("Capture.JPG");
	FileDataSource dataSource = new FileDataSource(file);
	byte[] readAllBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
	byte[] xattachbytes = Base64.getEncoder().encode(readAllBytes);
	ByteArrayDataSource arrayDataSource = new ByteArrayDataSource(xattachbytes, "multipart/related");
	DataHandler xattach = new DataHandler(arrayDataSource);
	
	HelloWorldMtom hwMtom = new HelloWorldMtom();
	hwMtom.setXctxt("test");
	hwMtom.setXattach(xattach);

	IWSLim5APserviceLocator iwsLim5APservice = new IWSLim5APserviceLocator();
	HandlerRegistry hr = iwsLim5APservice.getHandlerRegistry();
	List<HandlerInfo> handlerChain = hr.getHandlerChain((QName) iwsLim5APservice.getPorts().next());
	HandlerInfo hi = new HandlerInfo();
	hi.setHandlerClass(LogHandler.class);
	handlerChain.add(hi);

	Call call = (Call) iwsLim5APservice.createCall();
	Handler reqHandler=new RequestHandler();
	//call.setClientHandlers(reqHandler, reqHandler);
	call.setTargetEndpointAddress(ADDRESS);
	call.setOperationName(new QName("urn:WSLim5APIntf-IWSLim5AP", "IMGsd"));
	call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
	call.setProperty(Call.ATTACHMENT_ENCAPSULATION_FORMAT, Call.ATTACHMENT_ENCAPSULATION_FORMAT_MIME);
	call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
	call.setReturnType(org.apache.axis.Constants.XSD_STRING);
	call.setOperationStyle(Style.WRAPPED);
	call.setOperationUse(Use.LITERAL);
	QName qnameHelloMtom = new QName("urn:WSLim5APIntf-IWSLim5AP", "IMGsd");
	call.registerTypeMapping(hwMtom.getClass(), qnameHelloMtom, new HelloWorldMtomSerializerFactory(),
		new BeanDeserializerFactory(hwMtom.getClass(), qnameHelloMtom));
	call.addParameter("arg0", qnameHelloMtom, ParameterMode.IN);
	String ret = (String) call.invoke(new Object[] { hwMtom });
	System.out.println(ret);

    }
}
