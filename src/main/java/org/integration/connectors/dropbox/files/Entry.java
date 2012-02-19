package org.integration.connectors.dropbox.files;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

/**
 * A metadata entry that describes a file or folder.
 */
public class Entry {

    /** Size of the file. */
    private long bytes;

    /**
     * If a directory, the hash is its "current version". If the hash
     * changes between calls, then one of the directory's immediate
     * children has changed.
     */
    private String hash;

    /**
     * Name of the icon to display for this entry. Corresponds to filenames
     * (without an extension) in the icon library available at
     * https://www.dropbox.com/static/images/dropbox-api-icons.zip.
     */
    private String icon;

    /** True if this entry is a directory, or false if it's a file. */
    private boolean isDir;

    /**
     * Last modified date, in "EEE, dd MMM yyyy kk:mm:ss ZZZZZ" form (see
     * {@code RESTUtility#parseDate(String)} for parsing this value.
     */
    private String modified;

    /** Path to the file from the root. */
    private String path;

    /**
     * Name of the root, usually either "dropbox" or "app_folder".
     */
    private String root;

    /**
     * Human-readable (and localized, if possible) description of the
     * file size.
     */
    private String size;

    /** The file's MIME type. */
    private String mimeType;

    /**
     * Full unique ID for this file's revision. This is a string, and not
     * equivalent to the old revision integer.
     */
    private String rev;

    /** Whether a thumbnail for this is available. */
    private boolean thumbExists;

    /**
     * Whether this entry has been deleted but not removed from the
     * metadata yet. Most likely you'll only want to show entries with
     * isDeleted == false.
     */
    private boolean isDeleted;

    /** A list of immediate children if this is a directory. */
    private List<Entry> contents;
    
    public Entry() {}
    
    public Entry(JsonNode tree) {
        setBytes(tree.get("bytes").asLong());
        setDeleted(tree.has("is_deleted") ? tree.get("is_deleted").asBoolean() : false);
        setDir(tree.has("is_dir") ? tree.get("is_dir").asBoolean() : false);
        setHash(tree.has("hash") ? tree.get("hash").asText() : StringUtils.EMPTY);
        setIcon(tree.has("icon") ? tree.get("icon").asText() : null);
        setMimeType(tree.has("mime_type") ? tree.get("mime_type").asText() : null);
        setModified(tree.has("modified") ? tree.get("modified").asText() : null);
        setPath(tree.get("path").asText());
        setRev(tree.has("rev") ? tree.get("rev").asText() : null);
        setRoot(tree.get("root").asText());
        setSize(tree.get("size").asText());
        setThumbExists(tree.has("thumb_exists") ? tree.get("thumb_exists").asBoolean() : false);
        
        JsonNode contentsTree = tree.get("contents");
        
        if (contentsTree != null) {
            Iterator<JsonNode> it = contentsTree.getElements();
            List<Entry> contents = new ArrayList<Entry>();
            while(it.hasNext()) { 
                contents.add(new Entry(it.next()));
            }
            
            setContents(contents);
        }
    }

    /**
     * Returns the file name if this is a file (the part after the last
     * slash in the path).
     */
    public String getName() {
        int ind = path.lastIndexOf('/');
        return path.substring(ind + 1, path.length());
    }

    /**
     * Returns the path of the parent directory if this is a file.
     */
    public String getParentPath() {
        if (path.equals("/")) {
            return "";
        } else {
            int ind = path.lastIndexOf('/');
            return path.substring(0, ind + 1);
        }
    }

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean isDir) {
        this.isDir = isDir;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public boolean isThumbExists() {
        return thumbExists;
    }

    public void setThumbExists(boolean thumbExists) {
        this.thumbExists = thumbExists;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<Entry> getContents() {
        return contents;
    }

    public void setContents(List<Entry> contents) {
        this.contents = contents;
    }
}