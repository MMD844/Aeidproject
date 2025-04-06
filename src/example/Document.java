package example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable {

    public String content;
    private Date creationDate;
    private Date lastModificationDate;
    public static final int Doc_Entity_Code = 13;

    public Document (String content) {
        this.content = content;
    }

    @Override
    public Document copy() {
        Document copyDoc = new Document(this.content);
        copyDoc.id = this.id;
        if (this.creationDate != null) {
            copyDoc.creationDate = new Date(this.creationDate.getTime());
        }
        if (this.lastModificationDate != null) {
            copyDoc.lastModificationDate = new Date(this.lastModificationDate.getTime());
        }
        return copyDoc;
    }

    @Override
    public int getEntityCode() {
        return Doc_Entity_Code;
    }


    @Override
    public void setCreationDate(Date date) {
        this.creationDate = new Date(date.getTime());
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = new Date(date.getTime());
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}
