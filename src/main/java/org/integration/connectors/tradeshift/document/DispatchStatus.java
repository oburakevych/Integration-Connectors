package org.integration.connectors.tradeshift.document;


public enum DispatchStatus {
	ACCEPTED(0),
	PROCESSING(3),
	COMPLETED(4),
	FAILED_TRANSIENT(5),
	FAILED(8);
	
    public static final String DOC = "The current dispatch state.\n" +
        "ACCEPTED - The dispatch has been queued for processing.\n" +
        "PROCESSING - The dispatch is currently in progress.\n" +
        "COMPLETED - The dispatch has completed successfully.\n" +
        "FAILED_TRANSIENT - Dispatching failed, but will be retried by system later.\n" +
        "FAILED - Dispatching failed permanently.";
    
	private final int code;

	private DispatchStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static DispatchStatus getStatus(int code) {
		for (DispatchStatus s : values()) {
			if (s.code == code) return s;
		}
		return null;
	}
}
