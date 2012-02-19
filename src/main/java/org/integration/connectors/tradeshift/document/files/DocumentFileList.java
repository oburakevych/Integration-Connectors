package org.integration.connectors.tradeshift.document.files;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="DocumentFileList", namespace = "http://tradeshift.com/api/public/1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentFileList {
    @XmlElement(name="Items")
	private List<DocumentFile> items = new ArrayList<DocumentFile>();
	
    @XmlAttribute(name="numPages")
    private Integer pages;
    
    @XmlAttribute(name="pageId")
    private Integer page;
    
    @XmlAttribute(name="itemsPerPage")
    private Integer itemsPerPage;

    @XmlAttribute(name="itemCount")
    private int itemCount;
    
    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }
    
    public void setPage(Integer page) {
        this.page = page;
    }
    
    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    public int getPage() {
        return page;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void addItem(DocumentFile item) {
        getItems().add(item);
    }

    public int getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
	
	public List<DocumentFile> getItems() {
		return items;
	}

    public void setItems(List<DocumentFile> items) {
        this.items = items;
    }
}
