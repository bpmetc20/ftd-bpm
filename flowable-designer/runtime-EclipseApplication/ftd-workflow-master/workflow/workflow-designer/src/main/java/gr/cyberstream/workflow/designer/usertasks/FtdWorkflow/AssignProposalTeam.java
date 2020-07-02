/**
 * @author nlyk
 */
package gr.cyberstream.workflow.designer.usertasks.FtdWorkflow;

//import org.activiti.designer.integration.annotation.DatePickerProperty;
import org.activiti.designer.integration.annotation.Help;
import org.activiti.designer.integration.annotation.Property;
import org.activiti.designer.integration.annotation.PropertyItems;
import org.activiti.designer.integration.annotation.Runtime;
import org.activiti.designer.integration.servicetask.AbstractCustomServiceTask;
import org.activiti.designer.integration.servicetask.PropertyType;
import org.activiti.designer.integration.usertask.AbstractCustomUserTask;

public class AssignProposalTeam extends AbstractCustomUserTask {
	
	@Property(type = PropertyType.RADIO_CHOICE, displayName = "Assign", required = true, defaultValue = "ROLE")
	@Help(displayHelpShort = "Assigner", displayHelpLong = "Assign Actor or Role.")
	@PropertyItems({ "ACTOR", "ACTOR", "ROLE", "ROLE" })
	private String actorRole;

	@Property(type = PropertyType.COMBOBOX_CHOICE, displayName = "Role assignment", required = true, defaultValue = "Manager")
	@Help(displayHelpShort = "Role assignment", displayHelpLong = "Choose a meeting room")
	@PropertyItems({ "Accountant", "Accountant", "Manager", "Manager", "Executive", "Executive", "Director", "Director", "Developer", "Developer" })
	private String roleAssignment;
	
	@Property(type = PropertyType.RADIO_CHOICE, displayName = "Draft Proposal", required = true, defaultValue = "Draft Proposal")
	@Help(displayHelpShort = "Assigner", displayHelpLong = "Assign Actor or Role.")
	@PropertyItems({ "Draft Proposal", "Draft Proposal", "Follow Proposal SOP", "Follow Proposal SOP" })
	private String draftProposl;

	
	@Property(type = PropertyType.RADIO_CHOICE, displayName = "Proposal Action", required = true, defaultValue = "None")
	@Help(displayHelpShort = "Assigner", displayHelpLong = "Assign Actor or Role.")
	@PropertyItems({ "Contact External Expert", "Contact External Expert", "None", "None" })
	private String contsactExpertRole;
	
			
	@Override
	public String getName() {
		return "Assign Proposal Team";
	}

	@Override
	public String getSmallIconPath() {
		return "../icons/coins.png";
	}

	@Override
	public String contributeToPaletteDrawer() {
		return "FTD Tasks";
	}

	@Override
	public String getLargeIconPath() {
		return "../icons/coins.png";
	}

}
