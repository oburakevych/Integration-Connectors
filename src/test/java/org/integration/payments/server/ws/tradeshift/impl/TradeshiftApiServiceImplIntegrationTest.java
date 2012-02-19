package org.integration.payments.server.ws.tradeshift.impl;

import static org.junit.Assert.*;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

import java.io.IOException;
import java.util.UUID;

import org.integration.connectors.documentfiles.DocumentFileList;
import org.integration.connectors.tradeshift.appsettings.AppSettings;
import org.integration.connectors.tradeshift.document.Dispatch;
import org.integration.connectors.tradeshift.document.DocumentMetadata;
import org.integration.connectors.tradeshift.ws.TradeshiftApiService;
import org.integration.payments.server.document.DocumentServiceTest;
import org.integration.util.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.unitils.reflectionassert.ReflectionComparatorMode;


@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TradeshiftApiServiceImplIntegrationTest {
    private static final UUID DOCUMENT_ID = UUID.fromString("2bf1c6a9-fa08-4cd7-969a-4c6bae072a33"); 

	@Value("${tradeshift.api.tenantId}")
	private String tenantId;

	@Autowired
	private TradeshiftApiService tradeshiftApiService;

	@Test
	public void getAppSettings() {
		AppSettings expectedAppSettings = new AppSettings();

		AppSettings actualAppSettings = tradeshiftApiService.getAppSettings(tenantId);

		assertReflectionEquals(expectedAppSettings, actualAppSettings, new ReflectionComparatorMode[] {ReflectionComparatorMode.LENIENT_DATES});
	}
	
	@Test
	public void getDocument() throws IOException {
	    //TODO: pick up a DocumentID from a property file
	    UUID documentId = DOCUMENT_ID;

	    byte[] rawXml = tradeshiftApiService.getDocument(tenantId, documentId, null);

	    assertNotNull(rawXml);
	    
	    // Save the pulled file. It will be used by DocumentServiceTest tests
	    //TODO: take the resources path and test file names from a property file
	    IOUtils.writeToFile(DocumentServiceTest.RESOURCES_PATH + "/" + DocumentServiceTest.TEST_UBL_FILE_NAME, rawXml, false);
	}
	
	@Test
	public void getMetadata() {
	    DocumentMetadata metadata = tradeshiftApiService.getDocumentMetadata(tenantId, DOCUMENT_ID);
	    
	    assertNotNull(metadata);
	    assertNotNull(metadata.getDocumentId());
	    assertNotNull(metadata.getState());
	    assertNotNull(metadata.getDocumentType());
	}
	
   @Test
   @Ignore
    public void getDispatch() {
        Dispatch dispatch = tradeshiftApiService.getLatestDispatch(tenantId, DOCUMENT_ID);
        
        assertNotNull(dispatch);
        assertNotNull(dispatch.getDispatchID());
        assertNotNull(dispatch.getDispatchChannel());
        assertNotNull(dispatch.getDispatchState());
        assertNotNull(dispatch.getObjectId());
        assertNotNull(dispatch.getSenderCompanyAccountId());
    }
	
	@Test
	public void getDocumentFiles() {
	    DocumentFileList dfList = tradeshiftApiService.getDocumentFiles(tenantId, null, 100, 0, null, null, null);
	    
	    assertNotNull(dfList);
	    assertTrue(dfList.getItemCount() > 0);
	    assertTrue(dfList.getItems().size() > 0);
	    assertTrue(dfList.getItemCount() >= dfList.getItems().size());
	}
	
}
