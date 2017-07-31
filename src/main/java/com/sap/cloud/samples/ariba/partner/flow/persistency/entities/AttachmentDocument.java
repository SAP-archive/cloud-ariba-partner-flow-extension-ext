package com.sap.cloud.samples.ariba.partner.flow.persistency.entities;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Attachment document entity.
 *
 */
@Entity
@XmlRootElement
@Table(name = "IND_LOC_DOCUMENT")
public class AttachmentDocument implements Serializable {

	private static final long serialVersionUID = 1798962518916087507L;

	@Id
	@Column(name = "IND_LOC_ID")
	private String id;

	@Column(name = "IND_LOC_NAME")
	private String name;

	@Column(name = "IND_LOC_PATH")
	private String path;

	@Column(name = "IND_LOC_LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@Column(name = "IND_LOC_LAST_MODIFICATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private GregorianCalendar lastModificationDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public GregorianCalendar getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(GregorianCalendar lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	@Override
	public String toString() {
		return "DocumentEntity [id=" + id + ", name=" + name + ", path=" + path + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastModificationDate=" + lastModificationDate + "]";
	}
}
