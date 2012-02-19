package org.integration.connectors;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.InvoiceType;

import org.apache.commons.lang.math.RandomUtils;
import org.integration.connectors.documentfiles.DocumentFile;
import org.integration.connectors.documentfiles.DocumentFileList;
import org.integration.connectors.documentfiles.DocumentFileState;
import org.integration.connectors.filesystem.FileSystemConnectorService;
import org.integration.payments.server.util.IOUtils;
import org.integration.payments.server.util.JAXBUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;


@ContextConfiguration(locations={"/test-root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FileSystemConnectorServiceTest {
    //TODO: take the resources path and test file names from a property file
    public static final String RESOURCES_PATH = "src/test/resources";
    public static final String TEST_INV_TO_RESEND_FILE_NAME = "sandbox_sashsd_ubl_inv.xml";
    
    @Value("${tradeshift.api.tenantId}")
    private String companyAccountId;
    
    @Autowired
    private FileSystemConnectorService connectorService;
    
    @Test
    public void transferAndDispatchDocumentFile() throws ParserConfigurationException, SAXException, IOException, InterruptedException {
        String documentId = String.valueOf(RandomUtils.nextLong());
        byte[] invoice = getInvoiceByteStream(RESOURCES_PATH + "/" + TEST_INV_TO_RESEND_FILE_NAME, documentId);
        
        final String directory = "FS";
        final String filename = documentId + ".xml";
        
        connectorService.transferDocumentFile(companyAccountId, directory, filename, null, invoice);
        
        byte[] transferedInvoice = connectorService.getDocumentFile(companyAccountId, directory, filename ); 
        
        assertNotNull(transferedInvoice);
        assertArrayEquals(invoice, transferedInvoice);
        
        
        DocumentFileList documentFileList = connectorService.getDocumentFiles(companyAccountId, null, 50, 0, DocumentFileState.NEW, directory, filename);
        
        assertNotNull(documentFileList);
        assertTrue(1 == documentFileList.getItemCount());
        
        List<DocumentFile> documentFiles = documentFileList.getItems();
        
        assertNotNull(documentFiles);
        assertTrue("Document files size is " + documentFiles.size(), documentFiles.size() > 0);
        
        DocumentFile documentFile = documentFiles.get(0);
        
        assertEquals(filename, documentFile.getFilename());
        assertEquals(directory, documentFile.getDirectory());
        
        connectorService.dispatchDocumentFile(companyAccountId, directory, filename);
        
        Thread.sleep(6000);
        
        documentFileList = connectorService.getDocumentFiles(companyAccountId, null, 50, 0, DocumentFileState.DISPATCHED, null, filename);
        
        assertNotNull(documentFileList);
        assertTrue(1 == documentFileList.getItemCount());
        
        documentFiles = documentFileList.getItems();
        assertNotNull(documentFiles);
        
        documentFile = documentFiles.get(0);
        
        assertEquals(filename, documentFile.getFilename());
        assertFalse(directory.equals(documentFile.getDirectory()));
        assertNotNull(documentFile.getDocumentId());
    }
    
    private byte[] getInvoiceByteStream(String path, String id) throws IOException {
        InvoiceType invoice = JAXBUtils.unmarshall(IOUtils.getResourceAsByteArray(path), InvoiceType.class);
        IDType idt = new IDType();
        idt.setValue(id);
        invoice.setID(idt);
        
        final byte[] ublData = JAXBUtils.marshall(invoice, false);
        
        return ublData;
    }

}
