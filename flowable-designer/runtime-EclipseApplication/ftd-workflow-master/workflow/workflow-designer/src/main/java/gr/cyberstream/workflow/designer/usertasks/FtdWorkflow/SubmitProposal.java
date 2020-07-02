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

public class SubmitProposal extends AbstractCustomUserTask {
	
	@Property(type = PropertyType.RADIO_CHOICE, displayName = "Assign", required = true, defaultValue = "ROLE")
	@Help(displayHelpShort = "Assigner", displayHelpLong = "Assign Actor or Role.")
	@PropertyItems({ "ACTOR", "ACTOR", "ROLE", "ROLE" })
	private String actorRole;

	@Property(type = PropertyType.COMBOBOX_CHOICE, displayName = "Role assignment", required = true, defaultValue = "Manager")
	@Help(displayHelpShort = "Role assignment", displayHelpLong = "Choose a meeting room")
	@PropertyItems({ "Accountant", "Accountant", "Manager", "Manager", "Executive", "Executive", "Director", "Director", "Developer", "Developer" })
	private String roleAssignment;
	
	@Property(type = PropertyType.DATE_PICKER, displayName = "Expiry date", required = true)
	@Help(displayHelpShort = "Due date", displayHelpLong = "Choose the due date when the account will expire if no extended before the date.")
	@DatePickerProperty()
	private String expiryDate;
	
	
	@Override
	public String getName() {
		return "Submit Proposal";
	}

	@Override
	public String getSmallIconPath() {
		return "icons/coins.png";
	}

	@Override
	public String contributeToPaletteDrawer() {
		return "FTD Tasks";
	}

	@Override
	public String getLargeIconPath() {
		return "icons/coins.png";
	}

}
