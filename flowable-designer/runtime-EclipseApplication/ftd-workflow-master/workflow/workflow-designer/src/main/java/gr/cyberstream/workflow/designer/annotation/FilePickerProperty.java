package gr.cyberstream.workflow.designer.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;

import org.activiti.designer.integration.servicetask.PropertyType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;

/**
 * Defines attributes for {@link PropertyType#DATE_PICKER} fields.
 * 
 * @author Tiese Barrell
 * @version 1
 * @since 0.7.0
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FilePickerProperty {

	FileDialog dialog=new FileDialog(null);
	  //dialog.setText("Select external archive");
	  //dialog.setFilterExtensions(new String[]{"*.jar;,"*.*"});
	  //Path currPath=new Path(_entryText.getText());
	  //dialog.setFilterPath(currPath.toOSString());
	  String res=dialog.open();
	  //if (res != null) {
	  //  _entryText.setText(Path.fromOSString(res).makeAbsolute().toOSString());
	  //}
	
  /**
   * The pattern for the date time to be stored in the output. This pattern is
   * used to instruct {@link SimpleDateFormat} and should therefore be supported
   * by it.
   * 
   * @see {@link SimpleDateFormat}
   */
  //abstract String dateTimePattern() default DEFAULT_DATE_TIME_PATTERN;

  /**
   * The style to be used for the {@link DateTime} control. Only use values
   * supported by the {@link DateTime} control, such as {@link SWT#CALENDAR},
   * {@link SWT#DATE}.
   * 
   * @see {@link DateTime}
   * @see {@literal http://help.eclipse.org/helios/index.jsp?topic=/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/widgets/DateTime.html}
   * 
   */
  //abstract int swtStyle() default DEFAULT_DATE_TIME_CONTROL_STYLE;

}

