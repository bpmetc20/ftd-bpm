/**
 * @author nlyk
 */
package gr.cyberstream.workflow.designer.usertasks.FtdWorkflow;

import org.activiti.designer.integration.annotation.DatePickerProperty;
import org.activiti.designer.integration.annotation.Help;
import org.activiti.designer.integration.annotation.Property;
import org.activiti.designer.integration.annotation.PropertyItems;
import org.activiti.designer.integration.servicetask.AbstractCustomServiceTask;
import org.activiti.designer.integration.servicetask.PropertyType;
import org.activiti.designer.integration.usertask.AbstractCustomUserTask;

public class ContactExternalExpert  extends AbstractCustomUserTask {
	
	private static final String DEFAULT_ICON_PATH = "icons/defaultCustomUserTask.png";
	
	@Property(type = PropertyType.RADIO_CHOICE, displayName = "Submit for PST", required = true, defaultValue = "Submit for PST")
	@Help(displayHelpShort = "Assigner", displayHelpLong = "Assign Actor or Role.")
	@PropertyItems({ "Submit for PST", "Submit for PST", "Submit for PST Review", "Submit for PST Review" })
	private String actorRole;

	@Property(type = PropertyType.RADIO_CHOICE, displayName = "Contact External Expert", required = false, defaultValue = "")
	@Help(displayHelpShort = "Assigner", displayHelpLong = "Assign Actor or Role.")
	@PropertyItems({ "Contact External Expert", "Contact External Expertl"})
	private String contsactExpertRole;
	
		
	@Override
	public String getName() {
		return "Contact External Expert";
	}

	@Override
	public String getSmallIconPath() {
		return DEFAULT_ICON_PATH;
	}

	@Override
	public String contributeToPaletteDrawer() {
		return "FTD Tasks";
	}

	@Override
	public String getLargeIconPath() {
		return DEFAULT_ICON_PATH;
	}

}
