package org.jtester.tutorial.scanner.ibatis;

public class NamespaceID {
	public NamespaceID() {

	}

	public NamespaceID(String namespace, String type, String id) {
		super();
		this.namespace = namespace;
		this.type = type;
		this.id = id;
	}

	private String namespace;
	private String type; // select insert
	private String id; //

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		NamespaceID ni = (NamespaceID) obj;
		if (this.namespace.equals(ni.getNamespace()) && this.type.equals(ni.getType()) && this.id.equals(ni.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return namespace.hashCode() + type.hashCode() + id.hashCode();
	}

	@Override
	public String toString() {
		return "[namespace=" + namespace + ", type=" + type + ", id=" + id + "]";
	}
}
