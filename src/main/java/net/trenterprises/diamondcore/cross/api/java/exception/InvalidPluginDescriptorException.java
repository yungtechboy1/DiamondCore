package net.trenterprises.diamondcore.cross.api.java.exception;

public class InvalidPluginDescriptorException extends PluginException {
	
	private static final long serialVersionUID = 5535916519765665458L;

	public InvalidPluginDescriptorException(String descriptor) {
		super("Invalid plugin.yml, descriptor \"" + descriptor + "\" is not valid!");
	}
	
}
