package org.integration.payments.server.ws.dropbox.message.convertor;

import static org.integration.payments.server.ws.dropbox.DropboxApiConstants.DEFAULT_CHARSET;
import static org.integration.payments.server.ws.dropbox.DropboxApiConstants.METADATA_HEADER;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.integration.connectors.dropbox.files.DropboxFile;
import org.integration.connectors.dropbox.files.Entry;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.FileCopyUtils;

public class DropboxFileHttpConverter extends AbstractHttpMessageConverter<DropboxFile> {
	private ObjectMapper objectMapper;

	public DropboxFileHttpConverter() {
		this(new ObjectMapper());
	}

	public DropboxFileHttpConverter(ObjectMapper objectMapper) {
		super(new MediaType("*", "*", Charset.forName(DEFAULT_CHARSET)));

		this.objectMapper = objectMapper;
	}

	public DropboxFileHttpConverter(JsonFactory jsonFactory) {
		this(new ObjectMapper(jsonFactory));
	}

	protected DropboxFile readInternal(Class<? extends DropboxFile> arg0, HttpInputMessage inputMessage)
			    throws IOException, HttpMessageNotReadableException {
	    DropboxFile file = null;
	    HttpHeaders headers = inputMessage.getHeaders();
	    
	    if (headers.containsKey(METADATA_HEADER)) {
	        List<String> metadataJson = headers.get(METADATA_HEADER);

	        Entry metadata = objectMapper.readValue(metadataJson.get(0), Entry.class);
	    
    	    if (metadata != null) {
    	        file = new DropboxFile(metadata);
    	        
    	        long contentLength = inputMessage.getHeaders().getContentLength();
    	        if (contentLength >= 0) {
    	            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) contentLength);
    	            FileCopyUtils.copy(inputMessage.getBody(), bos);
    	            file.setContents(bos.toByteArray());
    	        } else {
    	            file.setContents(FileCopyUtils.copyToByteArray(inputMessage.getBody()));
    	        }
    	    }
	    }

		return file;
	}

	protected boolean supports(Class<?> clazz) {
		return DropboxFile.class.equals(clazz);
	}

	protected void writeInternal(DropboxFile arg0, HttpOutputMessage arg1)
			throws IOException, HttpMessageNotWritableException {
		throw new UnsupportedOperationException();
	}
	
}
