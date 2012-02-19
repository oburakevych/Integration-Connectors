package org.integration.connectors.tradeshift.document;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Dispatch", namespace = "http://tradeshift.com/api/public/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dispatch {
	@XmlElement(name = "DispatchId")
	private UUID dispatchID;
	
	@XmlElement(name = "ObjectId")
	private UUID objectId;
	
	@XmlElement(name = "Created")
	private String created;
	
	@XmlElement(name = "SenderCompanyAccountId")
	private UUID senderCompanyAccountId;
	
	@XmlElement(name = "SenderUserId")
	private UUID senderUserId;
	
	@XmlElement(name = "DispatchState")
	private DispatchStatus dispatchState;
	
	@XmlElement(name = "LastStateChange")
	private String lastStateChange;
	
	@XmlElement(name = "ReceiverConnectionId")
	private UUID receiverConnectionId;
	
	@XmlElement(name = "DispatchChannel")
	private String dispatchChannel;
	
	@XmlElement(name = "FailureMessage")
	private String failureMessage;
	
	public UUID getDispatchID() {
		return dispatchID;
	}

	public void setDispatchID(UUID dispatchID) {
		this.dispatchID = dispatchID;
	}

	public UUID getObjectId() {
		return objectId;
	}

	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public UUID getSenderCompanyAccountId() {
		return senderCompanyAccountId;
	}

	public void setSenderCompanyAccountId(UUID senderTenantId) {
		this.senderCompanyAccountId = senderTenantId;
	}

	public UUID getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(UUID senderUserId) {
		this.senderUserId = senderUserId;
	}

	public DispatchStatus getDispatchState() {
		return dispatchState;
	}

	public void setDispatchState(DispatchStatus dispatchState) {
		this.dispatchState = dispatchState;
	}

	public String getLastStateChange() {
		return lastStateChange;
	}

	public void setLastStateChange(String lastStateChange) {
		this.lastStateChange = lastStateChange;
	}

	public UUID getReceiverConnectionId() {
		return receiverConnectionId;
	}

	public void setReceiverConnectionId(UUID receiverConnectionId) {
		this.receiverConnectionId = receiverConnectionId;
	}

	public String getDispatchChannel() {
		return dispatchChannel;
	}

	public void setDispatchChannel(String dispatchChannel) {
		this.dispatchChannel = dispatchChannel;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}
}
