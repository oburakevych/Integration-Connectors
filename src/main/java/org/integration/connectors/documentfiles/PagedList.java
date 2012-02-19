package org.integration.connectors.documentfiles;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class PagedList<T> {

    @XmlAttribute(name="numPages")
    private Integer pages;
    
    @XmlAttribute(name="pageId")
    private Integer page;
    
    @XmlAttribute(name="itemsPerPage")
    private Integer itemsPerPage;
    
    public PagedList(List<T> items, Integer pages, Integer page, Integer itemsPerPage, int itemCount) {
        setItems(items);
        setPages(pages);
        setPage(page);
        setItemsPerPage(itemsPerPage);
        setItemCount(itemCount);
    }

    @XmlAttribute(name="itemCount")
    private int itemCount;
    
    public PagedList() {}

    public abstract List<T> getItems();
    
    public abstract void setItems(List<T> items);

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

    public void addItem(T item) {
        getItems().add(item);
    }

    public int getItemCount() {
        return itemCount;
    }
    
    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
