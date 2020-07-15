package org.activiti.designer.handlers;

import org.eclipse.swt.widgets.*;

import java.util.Map;
import java.util.Objects;

import org.activiti.designer.eclipse.common.ActivitiPlugin;
import org.activiti.designer.util.DiagramHandler;
import org.activiti.designer.util.RestClient;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.LabelProvider;

public class LoadProcessHandler extends AbstractHandler {
	
	private String modelId = "";
	
	public final class BreakLoopException  extends RuntimeException {}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		Map<String, String> loadedModels = DiagramHandler.loadModels();
		final String[] tasksArray = DiagramHandler.buildList(loadedModels);
		
		if (tasksArray != null && tasksArray.length > 0) {
			String modelName = selectProcess(window, tasksArray);		
		
			if (!modelName.isEmpty()) {
				if(!DiagramHandler.isDiagramExist(modelName)) { 
					modelId = "";
			
					try {
						loadedModels.forEach((key, value) -> {
							if (value.equals(modelName)) {
								modelId = key;
								throw new BreakLoopException();
							}
						});
					} catch (BreakLoopException e) {
						// here you know that your condition has been met at least once
					}			
					if (!modelId.isEmpty()) {				
						String diagram = RestClient.getModelSource(modelId);				
						if (diagram.isEmpty() || DiagramHandler.writeDiagramToFile(modelName, diagram)) {						
							ErrorDialog.openError(window.getShell(), DiagramHandler.errorMessage, modelName, 
									new Status(IStatus.ERROR, ActivitiPlugin.getID(), "Error while opening new editor.", new PartInitException("Can't write diagram")));
							return window;
						}
					}
				}
				IStatus status = DiagramHandler.openDiagramForBpmnFile(modelName);	
				if (!status.isOK()) {
					ErrorDialog.openError(window.getShell(), "Error Opening Activiti Diagram", modelName, status);
				}
			}
		}
		return window;		
	}
	
	public String selectProcess(IWorkbenchWindow window, String[] elements) {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(window.getShell(), new LabelProvider());

        dialog.setTitle("Select Process");

        dialog.setMessage("&Select a process from the list. Type word using filter");

        dialog.setElements(elements);

        String selected = "";
        
        dialog.setMultipleSelection(false);
        int returnCode = dialog.open();
        if (returnCode == 0) {
            selected = (String) dialog.getFirstResult();
        }
        return selected;
	}		
}
