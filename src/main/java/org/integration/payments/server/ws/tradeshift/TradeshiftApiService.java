package org.integration.payments.server.ws.tradeshift;

import java.util.UUID;

import org.integration.connectors.documentfiles.DocumentFileList;
import org.integration.connectors.documentfiles.DocumentFileState;
import org.integration.connectors.tradeshift.account.TradeshiftAccount;
import org.integration.payments.server.document.Dispatch;
import org.integration.payments.server.document.DocumentMetadata;
import org.integration.payments.server.ws.tradeshift.dto.AppSettings;


public interface TradeshiftApiService {
	public AppSettings getAppSettings(String companyAccountId);
	
	public TradeshiftAccount getAccount(String companyAccountId);

	public void resendOAuthAccessToken(String companyAccountId);
	
	public DocumentMetadata getDocumentMetadata(String companyAccountId, UUID documentId);

	public byte[] getDocument(String companyAccountId, UUID documentId, String locale);
	
	public void putDocumentFile(String companyAccountId, String directory, String filename, String mimeType, byte[] content);
	
	public String dispatchDocumentFile(String companyAccountId, String directory, String filename);
	
	public byte[] getDocumentFile(String companyAccountId, String directory, String filename);
	
	public DocumentFileList getDocumentFiles(String companyAccountId, String since, int limit, int page, DocumentFileState state, String directory, String filename);
	
	public Dispatch getLatestDispatch(String companyAccountId, UUID documentId);
}
