package com.example.axis.client;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.utils.Messages;

public class RequestHandler extends BasicHandler {

    public void invoke(MessageContext msgContext) throws AxisFault {
	try {
	    logMessage(msgContext);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void logMessage(MessageContext msgContext) throws Exception {
	Message inMsg = msgContext.getRequestMessage();
	Message outMsg = msgContext.getResponseMessage();
	if (outMsg == null) {
	    System.out.println("============================= REQUEST ============================================");
	/*    System.out.println(
		    Messages.getMessage("inMsg00", (inMsg == null ? "" : inMsg.getSOAPEnvelope().getAsString())));*/
	} else {
	    System.out.println("===================================RESPONSE======================================");
	    System.out.println(
		    Messages.getMessage("outMsg00", (outMsg == null ? "" : outMsg.getSOAPEnvelope().getAsString())));
	}
    }

    @Override
    public void onFault(MessageContext msgContext) {
	super.onFault(msgContext);
	try {
	    logMessage(msgContext);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
