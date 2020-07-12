package org.activiti.designer.handlers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.activiti.designer.eclipse.common.ActivitiPlugin;
import org.activiti.designer.eclipse.editor.ActivitiDiagramEditorInput;
import org.activiti.designer.util.ActivitiConstants;
import org.activiti.designer.util.DiagramHandler;
import org.activiti.designer.util.RestClient;
import org.activiti.designer.util.editor.BpmnMemoryModel;
import org.activiti.designer.util.editor.ModelHandler;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;

public class LoadProcessHandler extends AbstractHandler {
	
	private Map<String, String> loadedModels = new HashMap<String, String>();
	private String modelId = "";
	
	public class BreakLoopException  extends RuntimeException {	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		loadedModels = RestClient.getModels();
		final String[] tasksArray = buildModelssList();
		
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
							//
						}
					}
				}
				DiagramHandler.openDiagramForBpmnFile(modelName);					
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
	
	private String[] buildModelssList() {
		List<String> values = new ArrayList<String>(loadedModels.values());
		Collections.sort(values);
		return values.toArray(new String[0]);
	}	
}
